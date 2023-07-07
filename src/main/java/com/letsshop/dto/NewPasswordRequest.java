package com.letsshop.dto;

public class NewPasswordRequest {
    private  String newPassword;
    private String email;

    public NewPasswordRequest(String newPassword, String email) {
        this.newPassword = newPassword;
        this.email = email;
    }

    public NewPasswordRequest() {
    }

    @Override
    public String toString() {
        return "NewPasswordRequest{" +
                "newPassword='" + newPassword + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
