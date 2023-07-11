package com.letsshop.dto;

public class EmailRequestForOtp {
    private String email;

    public EmailRequestForOtp(String email) {
        this.email = email;
    }

    public EmailRequestForOtp() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "EmailRequestForOtp{" +
                "email='" + email + '\'' +
                '}';
    }
}
