package me.tapumandal.ovirupa.service;

import me.tapumandal.ovirupa.repository.UserRepository;
import me.tapumandal.ovirupa.entity.MyUserDetails;
import me.tapumandal.ovirupa.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(!username.isEmpty() && username != null) {
            User user = userService.getUserByUserName(username);
            if(user != null) {
                if (userService.isActive(user.getId())) {
                    return new MyUserDetails(user);
                }
            }
        }

        return new MyUserDetails();

    }
    
}