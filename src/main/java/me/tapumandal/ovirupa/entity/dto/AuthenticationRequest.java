package me.tapumandal.ovirupa.entity.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class AuthenticationRequest {

    @NotNull(message = "Username can't be empty")
    @Email(message = "Email is not valid")
    private String username;

    private String password;

    private String otp;


    public AuthenticationRequest(){}

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public AuthenticationRequest(String username, String password, String otp) {
        this.username = username;
        this.password = password;
        this.otp = otp;
    }
}
