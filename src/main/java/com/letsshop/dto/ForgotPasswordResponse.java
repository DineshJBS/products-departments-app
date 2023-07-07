package com.letsshop.dto;

public class ForgotPasswordResponse {
    private String status;

    public ForgotPasswordResponse(String status) {
        this.status = status;
    }

    public ForgotPasswordResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ForgotPasswordResponse{" +
                "status='" + status + '\'' +
                '}';
    }
}
