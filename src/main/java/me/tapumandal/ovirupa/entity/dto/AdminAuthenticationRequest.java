package me.tapumandal.ovirupa.entity.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AdminAuthenticationRequest {

    @NotNull(message = "Username can't be empty")
    @Email(message = "Email is not valid")
    private String username;

    @NotNull(message = "Password can't be empty")
    @Size(min=6, max = 32, message = "Password is not valid")
    private String password;

//    @NotNull(message = "Password can't be empty")
//    @Size(min=4, max = 32, message = "Password is not valid")
    private String otp;


    public AdminAuthenticationRequest(){}

    public AdminAuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public AdminAuthenticationRequest(String username, String password, String otp) {
        this.username = username;
        this.password = password;
        this.otp = otp;
    }
}
