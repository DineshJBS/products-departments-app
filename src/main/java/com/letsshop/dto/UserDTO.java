package com.letsshop.dto;

public class UserDTO {

    private String name;
    private String role;

    public UserDTO(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public UserDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
