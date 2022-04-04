package me.tapumandal.ovirupa.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ConsumerUserDto {
    @NotNull
    private int id;

    @NotNull(message = "Name can't be empty")
    @Size(min=4, max = 32, message = "Write a proper name")
    protected String name;

    @NotNull(message = "UserName can't be empty")
    protected String username;

    protected String email;

    @NotNull(message = "Phone Code can't be empty")
    protected String phoneNumberCode;

    @NotNull(message = "Phone No can't be empty")
    protected String phoneNumber;

    protected String password;

    protected String work_title;

    protected String role;

    protected boolean is_active = true;

    protected boolean is_deleted = false;

    protected String address;

    protected String city;
}
