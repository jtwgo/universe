package com.jtw.main.unified.entity;

public enum Role
{
    MANAGER(0,"manager"),

    SYSTEM(1,"system"),

    OPERATOR(2,"operator");

    private int roleId;

    private String roleName;

    Role(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }
}
