package me.tapumandal.ovirupa.controller.v1;

import com.google.gson.Gson;
import me.tapumandal.ovirupa.entity.LoginResponseModelConsumer;
import me.tapumandal.ovirupa.entity.dto.ConsumerUserDto;
import me.tapumandal.ovirupa.entity.dto.UserDto;
import me.tapumandal.ovirupa.util.*;
import me.tapumandal.ovirupa.entity.ListFilter;
import me.tapumandal.ovirupa.entity.User;
import me.tapumandal.ovirupa.service.MyUserDetailsService;
import me.tapumandal.ovirupa.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import me.tapumandal.ovirupa.entity.dto.AuthenticationRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1")
public class UserController extends ControllerHelper {

    private static final String CONSUMER_USER_PASSWORD = "12345abcde!@#$%";

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    MyUserDetailsService myuserDetailsService;
    @Autowired
    UserDetails userDetails;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ApplicationPreferences applicationPreferences;

    @PostMapping("admin/authenticate")
    public ResponseEntity<LoginResponseModelConsumer> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        LoginResponseModelConsumer loginResponseModel = userService.adminAuthenticate(authenticationRequest);
        return ResponseEntity.ok(loginResponseModel);

    }

    @PostMapping(path = "admin/registration")
    public ResponseEntity<LoginResponseModelConsumer> adminRegistration(@RequestBody User u, HttpServletRequest request) throws Exception  {

        u.setRole("Admin");

        if (!userService.isUserExist(u.getUsername())) {
            LoginResponseModelConsumer loginResponseModel = userService.createAdminAccount(u);
            return ResponseEntity.ok(loginResponseModel);
        } else {
            return (ResponseEntity<LoginResponseModelConsumer>) ResponseEntity.badRequest();
        }
    }

    @PostMapping(path = "user/create")
    public CommonResponseSingle userCreate(@RequestBody @Valid User u, HttpServletRequest request) {

        storeUserDetails(request);

        if (!userService.isUserExist(u.getUsername())) {

//            u.setCompany(null);
//            User user = userService.createUser(u);

//            if (user != null) {
//                return response(true, HttpStatus.CREATED, "User & Company registration successful", user);
//            } else {
                return response(false, HttpStatus.BAD_REQUEST, "Something is wrong please contact.", (User) null);
//            }

        } else {
            return response(false, HttpStatus.NOT_ACCEPTABLE, "User already exist", (User) null);
        }
    }

    @GetMapping(path = "user/{id}")
    public CommonResponseSingle<User> getUser(@PathVariable("id") int id, HttpServletRequest request) {

        storeUserDetails(request);

        User user = userService.getById(id);


        if (user != null) {
            return response(true, HttpStatus.FOUND, "User by id: " + id, user);
        } else if (user == null) {
            return response(false, HttpStatus.NO_CONTENT, "User not found or deleted", (User) null);
        } else {
            return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong", (User) null);
        }
    }

    @GetMapping(path = "user/list")
    public CommonResponseArray getAll(@ModelAttribute ListFilter listFilter, HttpServletRequest request, Pageable pageable) {

        storeUserDetails(request);

        List<User> products = userService.getAll(pageable, listFilter);

        MyPagenation myPagenation = managePagenation(request, userService.getAllEntityCount(pageable, listFilter), pageable);

        if (!products.isEmpty()) {
            return response(true, HttpStatus.OK, "All user list", products, myPagenation);
        } else if (products.isEmpty()) {
            return response(true, HttpStatus.NO_CONTENT, "User List is empty", new ArrayList<User>(), myPagenation);
        } else {
            return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong", new ArrayList<User>(), myPagenation);
        }

    }

    @GetMapping(path = "admin/list")
    public CommonResponseArray getAllAdmin(HttpServletRequest request) {

        storeUserDetails(request);

        List<User> userList = userService.getAllAdmin();

        if (!userList.isEmpty()) {
            return response(true, HttpStatus.OK, "All user list", userList, null);
        } else if (userList.isEmpty()) {
            return response(true, HttpStatus.NO_CONTENT, "User List is empty", new ArrayList<User>(), null);
        } else {
            return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong", new ArrayList<User>(), null);
        }

    }

    @PostMapping(path = "user/update")
    public CommonResponseSingle updateProduct(@RequestBody User u, HttpServletRequest request) {

        storeUserDetails(request);
        User user = userService.update(u);

        if (user != null) {
            return response(true, HttpStatus.OK, "New user inserted successfully", user);
        } else if (user == null) {
            return response(false, HttpStatus.BAD_REQUEST, "Something is wrong with data", (User) null);
        }
        return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (User) null);
    }

    @PostMapping(path = "admin/update")
    public CommonResponseSingle updateAdminProduct(@RequestBody User u, HttpServletRequest request) {

        storeUserDetails(request);
        User user = userService.updateAdmin(u);


        if (user != null) {
            return response(true, HttpStatus.OK, "New user inserted successfully", user);
        } else if (user == null) {
            return response(false, HttpStatus.BAD_REQUEST, "Something is wrong with data", (User) null);
        }
        return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (User) null);
    }

    @DeleteMapping(path = "user/{id}")
    public CommonResponseSingle<User> deleteProduct(@PathVariable("id") int id, HttpServletRequest request) {

        storeUserDetails(request);

        if (userService.deleteById(id)) {
            return response(true, HttpStatus.OK, "User by id " + id + " is deleted", (User) null);
        } else {
            return response(false, HttpStatus.NOT_FOUND, "User not found or deleted", (User) null);
        }
    }

    @PostMapping(path = "consumer/registration")
    public ResponseEntity<LoginResponseModelConsumer> consumerRegistration(@RequestBody User u, HttpServletRequest request) throws Exception  {

        u.setRole("CONSUMER");

        if (!userService.isUserExist(u.getUsername())) {
            LoginResponseModelConsumer loginResponseModel = null;
            if(ApplicationPreferences.getOTP().equals(u.getOtp())) {
                u.setPassword(CONSUMER_USER_PASSWORD);
                loginResponseModel = userService.createUser(u);
            }
            return ResponseEntity.ok(loginResponseModel);
        } else {
            return (ResponseEntity<LoginResponseModelConsumer>) ResponseEntity.badRequest();
        }
    }

    @PostMapping("consumer/authenticate")
    public ResponseEntity<LoginResponseModelConsumer> consumerAuthenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        LoginResponseModelConsumer loginResponseModel = null;

        if(ApplicationPreferences.getOTP().equals(authenticationRequest.getOtp())) {
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), CONSUMER_USER_PASSWORD)
                );
            } catch (BadCredentialsException e) {
                throw new Exception("Incorrect username or tokenId", e);
            }
            userDetails = myuserDetailsService.loadUserByUsername(authenticationRequest.getUsername().toString());

            loginResponseModel = userService.consumerAuthenticate(authenticationRequest);
        }

        return ResponseEntity.ok(loginResponseModel);

    }


    @PostMapping(path = "consumer/address/update")
    public CommonResponseSingle<User> updateUserAddress(@RequestBody User u, HttpServletRequest request) {

        //System.out.println("CONTROLLER ADDRESS DTO: "+new Gson().toJson(u));
        storeUserDetails(request);

        User user = userService.update(u);
        //System.out.println("CONTROLLER ADDRESS RETURN: "+new Gson().toJson(u));
        if (user != null) {
            return response(true, HttpStatus.OK, "New user inserted successfully", user);
        } else if (user == null) {
            return response(false, HttpStatus.BAD_REQUEST, "Something is wrong with data", (User) null);
        }
        return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (User) null);
    }

    @GetMapping(path = "consumer/myprofile")
    public CommonResponseSingle<User> getMyProfileConsumer(HttpServletRequest request) {

        storeUserDetails(request);

        int userId = ApplicationPreferences.getUser().getId();
        User user = userService.getById(userId);

        if (user != null) {
            user.setPassword("");
            return response(true, HttpStatus.OK, "New user inserted successfully", user);
        } else if (user == null) {
            return response(false, HttpStatus.BAD_REQUEST, "Something is wrong with data", (User) null);
        }
        return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (User) null);
    }

    @PostMapping(path = "consumer/myprofile")
    public CommonResponseSingle<User> updateMyProfileConsumer(@ModelAttribute UserDto userDto, HttpServletRequest request) {


        storeUserDetails(request);

        int userId = ApplicationPreferences.getUser().getId();

        User user = userService.update(new User(userDto));
//        User user = userService.update(convertToEntity(userDto));
        if (user != null) {
            return response(true, HttpStatus.OK, "New user inserted successfully", user);
        } else if (user == null) {
            return response(false, HttpStatus.BAD_REQUEST, "Something is wrong with data", (User) null);
        }
        return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (User) null);
    }

    private ConsumerUserDto convertToDto(User user) {
        ConsumerUserDto consumerUserDto = modelMapper.map(user, ConsumerUserDto.class);
        return consumerUserDto;
    }

    private User convertToEntity(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        return user;
    }

    @PostMapping("consumer/access")
    public ResponseEntity<LoginResponseModelConsumer> consumerAccess(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        System.out.println("C 1:"+new Gson().toJson(authenticationRequest));
        LoginResponseModelConsumer loginResponseModel = null;
        if(ApplicationPreferences.getOTP().equals(authenticationRequest.getOtp())) {
            System.out.println("MATCHED");
            authenticationRequest.setPassword(CONSUMER_USER_PASSWORD);
            if (!userService.isUserExist(authenticationRequest.getUsername())) {
                System.out.println("NOT EXIST");
                User user = new User();
                user.setPassword(CONSUMER_USER_PASSWORD);
                user.setUsername(authenticationRequest.getUsername());
                user.setName(authenticationRequest.getUsername());
                loginResponseModel = userService.createUser(user);
                return ResponseEntity.ok(loginResponseModel);
            }else {
//                try {
//                    System.out.println("TRYING");
//                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), CONSUMER_USER_PASSWORD)
//                    );
//                } catch (BadCredentialsException e) {
//                    System.out.println("CATCH");
//                    throw new Exception("Incorrect username or tokenId", e);
//                }
                //                userDetails = myuserDetailsService.loadUserByUsername(authenticationRequest.getUsername().toString());
                System.out.println("EXIST");
                loginResponseModel = userService.consumerAuthenticate(authenticationRequest);
            }
        }
        System.out.println("C RETURN");
        return ResponseEntity.ok(loginResponseModel);

    }

}