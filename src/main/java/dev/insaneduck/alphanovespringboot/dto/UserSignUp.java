package dev.insaneduck.alphanovespringboot.dto;

import lombok.Value;

@Value
public class UserSignUp {
    String userName;
    String password;
    String email;
    String firstName;
    String lastName;
}
