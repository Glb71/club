package com.snapp.snapppay.club.enums;

public enum Roles {

    USER,
    ADMIN,
    PROVIDER;

    public static Roles getDefaultRole() {
        return Roles.USER;
    }

}
