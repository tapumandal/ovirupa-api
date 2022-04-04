package me.tapumandal.ovirupa.service.implementation;

import com.google.gson.Gson;
import me.tapumandal.ovirupa.entity.LoginResponseModelConsumer;
import me.tapumandal.ovirupa.entity.User;
import me.tapumandal.ovirupa.entity.dto.AuthenticationRequest;
import me.tapumandal.ovirupa.entity.dto.ConsumerUserDto;
import me.tapumandal.ovirupa.repository.RefCodeRepository;
import me.tapumandal.ovirupa.repository.UserRepository;
import me.tapumandal.ovirupa.service.MyUserDetailsService;
import me.tapumandal.ovirupa.util.ApplicationPreferences;
import me.tapumandal.ovirupa.util.JwtUtil;
import me.tapumandal.ovirupa.util.MyPagenation;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import me.tapumandal.ovirupa.domain.address.AddressRepository;
import me.tapumandal.ovirupa.entity.ListFilter;
import me.tapumandal.ovirupa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ApplicationPreferences applicationPreferences;

    @Autowired
    RefCodeRepository refCodeRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    MyUserDetailsService myuserDetailsService;

    @Autowired
    UserDetails userDetails;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    ModelMapper modelMapper;


    public UserServiceImpl(){}

//    public UserServiceImpl(User user){
//        this.user = user;
//    }


    @Override
    public LoginResponseModelConsumer createUser(User user) {

        System.out.println("S 1:"+new Gson().toJson(user));

        String previousPass = user.getPassword();

        user.setRole("CONSUMER");
        user.setWorkTitle("Consumer");
        user.setPhoneNumber(user.getUsername());
        user.setPhoneNumberCode("+88");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user = this.checkUsernameType(user);

        System.out.println("S 2:"+new Gson().toJson(user));

        userRepository.save(user);
        applicationPreferences.saveUserByUsername(user.getUsername());

        boolean authenticationStatus = false;
        try {
            System.out.println("TRY 1");
           authenticationStatus = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), previousPass)).isAuthenticated();
            System.out.println("TRY 2");
        } catch (BadCredentialsException e) {
            System.out.println("CATCH");

        }
        LoginResponseModelConsumer loginResponseModel = null;
        if(authenticationStatus) {
            System.out.println("AUTH TRUE");
            userDetails = myuserDetailsService.loadUserByUsername(user.getUsername());
            loginResponseModel = new LoginResponseModelConsumer();
            loginResponseModel.setJwt(jwtUtil.generateToken(userDetails));
            loginResponseModel.setUser(convertToDto(user));
        }
        System.out.println("S RETURN");
        return loginResponseModel;
    }

    @Override
    public LoginResponseModelConsumer createAdminAccount(User user) {

        String userInputPass = user.getPassword();

        user.setRole("ADMIN");
        user.setWorkTitle("Admin");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setActive(false);
        user.setUsername(user.getEmail());
        user = this.checkUsernameType(user);

        List<User> userList = (List<User>) userRepository.findAllByRole("ADMIN");

        if(userList.isEmpty()){
            user.setActive(true);
        }


        userRepository.save(user);

        applicationPreferences.saveUserByUsername(user.getUsername());

        boolean authenticationStatus = false;

        try {
            authenticationStatus = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), userInputPass)).isAuthenticated();
        } catch (BadCredentialsException e) {

        }
        LoginResponseModelConsumer loginResponseModel = null;
        if(authenticationStatus) {
            userDetails = myuserDetailsService.loadUserByUsername(user.getUsername());
            loginResponseModel = new LoginResponseModelConsumer();
            loginResponseModel.setJwt(jwtUtil.generateToken(userDetails));
            loginResponseModel.setUser(convertToDto(user));

        }
        return loginResponseModel;
    }

    private User createUserAccount(User user) {
        return null;
    }

    private String generateRefCode(User u) {

//        String nameTmp  = u.getName();
//        nameTmp = nameTmp.toUpperCase();
//        nameTmp = nameTmp.replace(".", "" );
//        nameTmp = nameTmp.replace("_", "" );
//        nameTmp = nameTmp.replace("MD", "" );
//        nameTmp = nameTmp.trim();
//
//        String[] nameArray = nameTmp.split(" ");
//        String refCode = "";
//
//        if(nameArray.length > 2){
//            refCode = nameArray[1];
//        }else if(nameArray.length > 1){
//            refCode = nameArray[0];
//        }else if(nameArray.length > 0){
//            refCode = nameArray[0];
//        }
//
//        if(refCode.length()>8){
//            refCode = refCode.substring(0,8);
//        }
//        if(refCode.length()<3){
//            int left = 48;
//            int right = 57;
//            Random random = new Random();
//            refCode = refCode+random.ints(left, right+1)
//                    .limit(4-refCode.length())
//                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
//                    .toString();
//        }
//        refCode = generateExtraCode(refCode);
//
//
//        while (!refCodeRepository.getByCode(refCode).isEmpty()){
//            refCode = refCode.substring(0, (refCode.length()-3));
//            refCode = generateExtraCode(refCode);
//        }
//
//        return refCode;
        return "";
    }

    private String generateExtraCode(String refCode) {
//        Random random = new Random();
//
//        int left = 48;
//        int right = 57;
//
//        refCode = refCode+random.ints(left, right+1)
//                .limit(1)
//                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
//                .toString();
//
//        left = 65;
//        right = 90;
//        refCode = refCode+random.ints(left, right+1)
//                .limit(2)
//                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
//                .toString();
//
//        return refCode;
        return "";
    }

    @Override
    public User create(User u) {
        return null;
    }

    @Override
    public User update(User u) {

//        List<Address> children = new ArrayList<>();

        System.out.println("INPUT:"+new Gson().toJson(u));

        User user = getById(u.getId());
        System.out.println("DB:"+new Gson().toJson(user));
        user.setName(u.getName());
        user.setCity(u.getCity());
        user.setAddress(u.getAddress());
//
//        for (AddressDto dto : u.getAddresses()) {
//            Address child;
//            if (dto.getId() == 0) {
//                //CREATE MODE: create new child
//                child = new Address();
////                child.setUser(user); //associate parent
//            } else {
//                //UPDATE MODE : fetch by id
//                child = addressRepository.getById(dto.getId());
//            }
//
//            BeanUtils.copyProperties(dto, child);//copy properties from dto
//            children.add(child);
//        }
//        user.getAddresses().clear();
//        user.getAddresses().addAll(children);
//
        User userReturn;
        try{
//            int userId = userRepository.update(user);
            userReturn = userRepository.save(user);
        }catch (Exception e){
            return null;
        }
//
//        if(userReturn.isPresent()){
//            return userReturn.get();
//        }else{
//            return null;
//        }
        return userReturn;
    }


    @Override
    public User updateAdmin(User u) {

//        List<Address> children = new ArrayList<>();
//

        User user = getById(u.getId());
         user.setActive(u.isActive());
//
//        for (AddressDto dto : u.getAddresses()) {
//            Address child;
//            if (dto.getId() == 0) {
//                //CREATE MODE: create new child
//                child = new Address();
////                child.setUser(user); //associate parent
//            } else {
//                //UPDATE MODE : fetch by id
//                child = addressRepository.getById(dto.getId());
//            }
//
//            BeanUtils.copyProperties(dto, child);//copy properties from dto
//            children.add(child);
//        }
//        user.getAddresses().clear();
//        user.getAddresses().addAll(children);
//
        User userReturn;
        try{
//            int userId = userRepository.update(user);
            userReturn = userRepository.save(user);
        }catch (Exception e){
            return null;
        }
//
//        if(userReturn.isPresent()){
//            return userReturn.get();
//        }else{
//            return null;
//        }
        return userReturn;
    }

    @Override
    public List<User> getAll(Pageable pageable, ListFilter listFilter) {
        return null;
    }

    @Override
    public List<User> getAllAdmin() {
        return userRepository.findAllByRole("ADMIN");
    }

    @Override
    public Page<User> getAllPageable(Pageable pageable) {

        Pageable pageable1 = PageRequest.of(0, 3, Sort.by("id").descending());
        Page<User> users = userRepository.findAll(pageable1);

        return users;
    }

    @Override
    public User getById(int id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()){
            return user.get();
        }else{
            return null;
        }

    }

    @Override
    public User getUserByUserName(String username) {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username));
        if(user.isPresent()){
            return user.get();
        }else{
            return null;
        }
    }

    @Override
    public boolean deleteById(int id) {
        try {
            userRepository.deleteById(id);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    @Override
    public User getByValue(String key, String value) {
        return null;
    }

    @Override
    public List<User> getAllByValue(String kye, String value) {
        return null;
    }

    @Override
    public boolean isActive(int id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){

            if(user.get().isActive()){

                return true;
            }

            return false;
        }

        return false;
    }

    @Override
    public boolean isDeleted(int id) {
        return false;
    }

    @Override
    public MyPagenation getPageable(Pageable pageable) {
//        return userRepository.getPageable(pageable);
        return null;
    }

    @Override
    public int getAllEntityCount(Pageable pageable, ListFilter listFilter) {
        return 0;
    }

    public boolean isUserExist(String userName){

        User user  = userRepository.findByUsername(userName);
        if(user != null){
            return true;
        }
        return false;
    }

    @Override
    public LoginResponseModelConsumer adminAuthenticate(AuthenticationRequest authenticationRequest) {

        System.out.println("adminAuthenticate");
        User user = null;
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
            user = userRepository.findByUsername(authenticationRequest.getUsername());
        } catch (BadCredentialsException e) {

        }
        System.out.println(new Gson().toJson(user));
        if(user == null){
            return null;
        }

        applicationPreferences.saveUserByUsername(user.getUsername());

        userDetails = myuserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        LoginResponseModelConsumer loginResponseModel = new LoginResponseModelConsumer();
        loginResponseModel.setJwt(jwtUtil.generateToken(userDetails));
        loginResponseModel.setUser(convertToDto(user));

        return loginResponseModel;
    }

    @Override
    public LoginResponseModelConsumer consumerAuthenticate(AuthenticationRequest authenticationRequest) {
        User user = new User();
        LoginResponseModelConsumer loginResponseModel = new LoginResponseModelConsumer();
        boolean authenticationStatus = false;
        try {
            authenticationStatus = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())).isAuthenticated();
        } catch (BadCredentialsException e) {

        }

        if(authenticationStatus) {
            user = userRepository.findByUsername(authenticationRequest.getUsername());

            applicationPreferences.saveUserByUsername(user.getUsername());

            userDetails = myuserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            loginResponseModel.setJwt(jwtUtil.generateToken(userDetails));
            loginResponseModel.setUser(convertToDto(user));

        }else{
            System.out.println("NOT AUTHENTICATED");
            return null;
        }

        return loginResponseModel;
    }

    protected User checkUsernameType(User u){
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(u.getUsername());
        if(mat.matches()){
            u.setUsernameType("EMAIL");
        }else{
            u.setUsername(u.getUsername().replace("+88", ""));
            u.setUsernameType("MOBILE");
//            }
        }
        return u;
    }


    private ConsumerUserDto convertToDto(User user) {
        ConsumerUserDto consumerUserDto = modelMapper.map(user, ConsumerUserDto.class);
        return consumerUserDto;
    }
}
