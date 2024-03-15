package dev.insaneduck.alphanovespringboot.dto;

import dev.insaneduck.alphanovespringboot.entities.User;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
@Value
public class UserDetails implements Serializable {
    Integer id;
    String username;
    String email;
    Boolean enabled;
    String firstName;
    String lastName;

    public static UserDetails userToUserDetails(User user) {
        return new UserDetails(user.getId(), user.getUsername(), user.getEmail(), user.getEnabled(), user.getFirstName(), user.getLastName());
    }
}