package com.intishaka.eeda.helper.model;

public class PermissionsResult {
    private final String isGranted;
    private final String permission;

    public PermissionsResult(String isGranted, String permission) {
        this.isGranted = isGranted;
        this.permission = permission;
    }

    public String isGranted() {
        return isGranted;
    }

    public String getPermission() {
        return permission;
    }

    @Override
    public String toString() {
        return "PermissionsResult{" +
                "isGranted='" + isGranted + '\'' +
                ", permission='" + permission + '\'' +
                '}';
    }
}