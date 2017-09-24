package org.terasology.persistence.repository;


import org.springframework.data.repository.CrudRepository;
import org.terasology.persistence.model.User;

import java.util.Optional;

/**
 *
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByUsernameOrEmail(String username, String email);
    User findUserByUsername(String username);
    User findUserByEmail(String email);

    User findUserById(long id);
}
