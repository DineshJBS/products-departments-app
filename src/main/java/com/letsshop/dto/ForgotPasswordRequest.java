package com.letsshop.dto;

public class ForgotPasswordRequest {

    private String email;
    private String otp;

    public ForgotPasswordRequest(String email, String otp) {
        this.email = email;
        this.otp = otp;
    }

    public ForgotPasswordRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Override
    public String toString() {
        return "ForgotPasswordRequest{" +
                "email='" + email + '\'' +
                ", otp='" + otp + '\'' +
                '}';
    }
}
