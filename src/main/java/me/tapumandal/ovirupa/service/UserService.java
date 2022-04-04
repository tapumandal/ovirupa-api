package me.tapumandal.ovirupa.service;

import me.tapumandal.ovirupa.entity.LoginResponseModelConsumer;
import me.tapumandal.ovirupa.entity.User;
import me.tapumandal.ovirupa.entity.dto.AuthenticationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService extends Service<User, User>{

    public boolean isUserExist(String userName);
    public LoginResponseModelConsumer adminAuthenticate(AuthenticationRequest authenticationRequest);
    public LoginResponseModelConsumer consumerAuthenticate(AuthenticationRequest authenticationRequest);
    public LoginResponseModelConsumer createUser(User user);
    public LoginResponseModelConsumer createAdminAccount(User user);
    public User getUserByUserName(String username);
    public Page<User> getAllPageable(Pageable pageable);
    public List<User> getAllAdmin();
    public User updateAdmin(User u);

}
