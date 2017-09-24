package org.terasology.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.terasology.config.AppConfig;
import org.terasology.persistence.model.PasswordResetToken;
import org.terasology.persistence.model.User;
import org.terasology.persistence.model.VerificationToken;
import org.terasology.persistence.repository.PasswordResetTokenRepository;
import org.terasology.persistence.repository.UserRepository;
import org.terasology.persistence.repository.VerificationTokenRepository;
import org.terasology.web.dto.UserDto;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl  implements UserService {

    @Autowired
    private AppConfig properties;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SessionRegistry sessionRegistry;

    @Override
    public User registerNewUserAccount(final UserDto userDto) {
        if (emailExist(userDto.getEmail()))
            throw new UserAlreadyExistException("There is an account with that email address:" + userDto.getEmail());

        final User user = new User();

        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setRole(User.USER_ROLE);
        return userRepository.save(user);
    }

    @Override
    public VerificationToken getVerificationToken(final String verificationToken) {
        return verificationTokenRepository.findByToken(verificationToken);
    }

    @Override
    public void saveUser(final User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(final User user) {
        final VerificationToken verificationToken = verificationTokenRepository.findByUser(user);

        if (verificationToken != null) {
            verificationTokenRepository.delete(verificationToken);
        }

        final PasswordResetToken passwordResetToken = passwordTokenRepository.findByUser(user);

        if (passwordResetToken != null) {
            passwordTokenRepository.delete(passwordResetToken);
        }

        userRepository.delete(user);
    }

    @Override
    public boolean emailExist(String email) {
        return userRepository.findUserByEmail(email) != null;
    }

    @Override
    public VerificationToken createVerificationToken(final User user) {

        final VerificationToken token = new VerificationToken(UUID.randomUUID().toString(), user);
        verificationTokenRepository.save(token);
        return token;
    }

    @Override
    public VerificationToken generateNewVerificationToken(final User user) {
        VerificationToken token = verificationTokenRepository.findByUser(user);
        if (token != null) {
            token.setToken(UUID.randomUUID().toString());
            token = verificationTokenRepository.save(token);
            return token;
        }
        return null;
    }

    @Override
    public PasswordResetToken createPasswordResetToken(final User user) {
        final PasswordResetToken token = new PasswordResetToken(UUID.randomUUID().toString(), user);
        passwordTokenRepository.save(token);
        return token;
    }

    @Override
    public User findUserByEmail(final String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User findUserByUsername(final String username) {
        return userRepository.findUserByEmail(username);
    }

    @Override
    public User getUserByPasswordResetToken(final String token) {
        return passwordTokenRepository.findByToken(token).getUser();
    }

    @Override
    public User getUserById(final long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public void changeUserPassword(final User user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public boolean isPasswordValid(final User user, final String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public TokenStatus validateVerificationToken(String token) {
        final VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TokenStatus.INVALID;
        }

        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            verificationTokenRepository.delete(verificationToken);
            return TokenStatus.TOKEN_EXPIRED;
        }
        user.setConfirmed(true);

        userRepository.save(user);
        return TokenStatus.VALID;
    }

}
