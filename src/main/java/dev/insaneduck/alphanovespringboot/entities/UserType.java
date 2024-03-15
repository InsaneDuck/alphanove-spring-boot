package dev.insaneduck.alphanovespringboot.entities;

public enum UserType {

    ROLE_ADMIN("ROLE_ADMIN"), ROLE_SUPPLIER("ROLE_SUPPLIER"), ROLE_CONSUMER("ROLE_CONSUMER");

    private final String type;


    UserType(String string) {
        this.type = string;
    }

    @Override
    public String toString() {
        return type;
    }
}
