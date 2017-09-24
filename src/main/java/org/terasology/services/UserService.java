package org.terasology.services;

import org.terasology.persistence.model.PasswordResetToken;
import org.terasology.persistence.model.User;
import org.terasology.persistence.model.VerificationToken;
import org.terasology.web.dto.UserDto;

public interface UserService {
    User registerNewUserAccount(UserDto userDto);

    VerificationToken getVerificationToken(String verificationToken);

    void saveUser(User user);

    void deleteUser(User user);

    boolean emailExist(String email);

    VerificationToken createVerificationToken(User user);

    VerificationToken generateNewVerificationToken(User user);

    PasswordResetToken createPasswordResetToken(User user);

    User findUserByEmail(String email);

    User findUserByUsername(String username);

    User getUserByPasswordResetToken(String token);

    User getUserById(long id);

    void changeUserPassword(User user, String password);

    boolean isPasswordValid(User user, String password);

    TokenStatus validateVerificationToken(String token);
}
