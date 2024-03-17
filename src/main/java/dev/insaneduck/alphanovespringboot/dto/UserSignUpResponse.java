package dev.insaneduck.alphanovespringboot.dto;

import lombok.Value;

@Value
public class UserSignUpResponse {
    boolean success;
    boolean userNameConflict;
    boolean emailConflict;
    String message;
}
