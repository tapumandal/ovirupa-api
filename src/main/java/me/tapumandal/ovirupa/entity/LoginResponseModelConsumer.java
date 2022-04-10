package me.tapumandal.ovirupa.entity;

import me.tapumandal.ovirupa.entity.dto.ConsumerUserDto;

public class LoginResponseModelConsumer {

    private String jwt;

    private User user;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}