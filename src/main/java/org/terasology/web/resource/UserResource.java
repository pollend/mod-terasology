package org.terasology.web.resource;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.terasology.persistence.model.User;

public class UserResource extends ResourceSupport {
    private final User user;

    public UserResource(User user) {
        this.user = user;
        add(new Link(user.getEmail(), "email"));
        add(new Link(user.getUsername(), "username"));
        add(new Link(user.getIdentifer(), "identifier"));
    }

    public User getUser() {
        return user;
    }
}
