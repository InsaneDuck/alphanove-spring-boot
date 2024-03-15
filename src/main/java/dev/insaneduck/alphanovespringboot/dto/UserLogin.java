package dev.insaneduck.alphanovespringboot.dto;

import lombok.Value;

@Value
public class UserLogin {
    String username;
    String password;
}
