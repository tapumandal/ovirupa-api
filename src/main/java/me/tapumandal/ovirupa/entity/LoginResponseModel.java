package me.tapumandal.ovirupa.entity;

public class LoginResponseModel {

    private String jwt;

    private User user;

    public LoginResponseModel(){}

    public LoginResponseModel(LoginResponseModelConsumer loginResponseModel) {
        this.jwt = loginResponseModel.getJwt();
        this.user = new User(loginResponseModel.getUser());
    }

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