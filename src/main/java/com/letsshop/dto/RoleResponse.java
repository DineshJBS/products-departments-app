package com.letsshop.dto;

public class RoleResponse {
    private String role;

    public RoleResponse(String role) {
        this.role = role;
    }

    public RoleResponse() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "RoleResponse{" +
                "role='" + role + '\'' +
                '}';
    }
}
