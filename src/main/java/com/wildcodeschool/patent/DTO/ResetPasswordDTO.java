package com.wildcodeschool.patent.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class ResetPasswordDTO {

    @NotBlank
    @Email
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
