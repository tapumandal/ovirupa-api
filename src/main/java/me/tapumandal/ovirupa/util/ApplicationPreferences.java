package me.tapumandal.ovirupa.util;

import me.tapumandal.ovirupa.entity.User;
import me.tapumandal.ovirupa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.prefs.Preferences;


@Component
public class ApplicationPreferences {

    @Autowired
    UserService userService;


    private static Preferences preferences;

    private static User user;

    public void saveUserByUsername(String username) {
        User u = userService.getUserByUserName(username);
        if(u != null) {
            if(u.getPhoneNumber() == null){
               u.setPhoneNumber(u.getUsername());
            }
            preferences = Preferences.userRoot().node(this.getClass().getName());
            preferences.put("name", u.getName());
            preferences.put("userId", String.valueOf(u.getId()));
            preferences.put("phoneNumberCode", u.getPhoneNumberCode());
            preferences.put("phoneNumber", u.getPhoneNumber());
            preferences.put("role", u.getRole());
        }
    }

    public static User getUser(){
        user = new User();
        preferences = Preferences.userRoot().node(ApplicationPreferences.class.getName());
        user.setName(preferences.get("name", ""));
        user.setId(Integer.parseInt(preferences.get("userId", "0")));
        user.setPhoneNumberCode(preferences.get("phoneNumberCode", null));
        user.setPhoneNumber(preferences.get("phoneNumber", null));
        user.setRole(preferences.get("role", null));
        return user;
    }

    public void saveCart(String cart) {
        preferences = Preferences.userRoot().node(this.getClass().getName());
        preferences.put("current_cart", cart);
    }

    public static String getCart(){
        preferences = Preferences.userRoot().node(ApplicationPreferences.class.getName());
        return preferences.get("current_cart", null);
    }

    public void storeOTP(String otp){
        preferences = Preferences.userRoot().node(this.getClass().getName());
        preferences.put("OTP", otp);
    }

    public static String getOTP(){
        preferences = Preferences.userRoot().node(ApplicationPreferences.class.getName());
        return preferences.get("OTP", "");
    }

}
