package com.vavv.web.model;

public enum UserRole {
    SUPER_ADMIN, // will be able to create and edit all orders.
    GROUP_ADMIN, // will be able to edit orders of group of contractors.
    CONTRACTOR // will be able to edit his orders.
}
