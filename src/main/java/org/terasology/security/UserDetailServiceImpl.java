package org.terasology.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.terasology.persistence.model.User;
import org.terasology.persistence.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Arrays;

@Service
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    public UserDetailServiceImpl() {
        super();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findUserByUsernameOrEmail(username,username);
        if(user == null)
            throw  new UsernameNotFoundException("User not found for:" + username);

        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), Arrays.asList(new SimpleGrantedAuthority(user.getRole()) ));
    }
}
