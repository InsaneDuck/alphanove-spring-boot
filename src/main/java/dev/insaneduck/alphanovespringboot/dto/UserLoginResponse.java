package dev.insaneduck.alphanovespringboot.dto;

import lombok.Value;

@Value
public class UserLoginResponse {
    boolean success;
    String message;
    String token;
    UserDetails userDetails;
}
