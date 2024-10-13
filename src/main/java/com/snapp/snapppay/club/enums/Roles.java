package com.snapp.snapppay.club.enums;

public enum Roles {

    USER,
    ADMIN;

    public static Roles getDefaultRole() {
        return Roles.USER;
    }

}
