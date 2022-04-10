package me.tapumandal.ovirupa.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import me.tapumandal.ovirupa.domain.ref_code.RefCodeReward;
import me.tapumandal.ovirupa.entity.dto.ConsumerUserDto;
import me.tapumandal.ovirupa.entity.dto.UserDto;
import me.tapumandal.ovirupa.domain.address.Address;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id = 0;

    @Column(name = "name")
    @NotNull(message = "Name can't be empty")
    protected String name;

    @Column(name = "email", unique = true)
    protected String email;

    @Column(name = "phone_number_code")
    protected String phoneNumberCode = "880";

    @Column(name = "phone_number")
    protected String phoneNumber;

    @Column(name = "username", unique = true)
    protected String username;

    @Column(name = "username_status")
    protected boolean isUsernameVerified;

    @Column(name = "username_type")
    protected String usernameType;

    @Column(name = "gender")
    protected String gender;

    @Column(name = "address")
    protected String address;

    @Column(name = "city")
    protected String city;

    //    @Size(min=6, max = 32, message = "Password size must be between 6 and 32 character.")
    @Column(name = "password")
    @Nullable
    protected String password;

    @Column(name = "work_title")
    protected String workTitle;

    @Column(name = "role")
    protected String role;

    @Column(name = "otp")
    protected String otp;

    @Column(name="user_token_id", length = 1200)
    protected String userTokenId;

    @Column(name = "is_active", columnDefinition = "boolean default 1")
    private boolean isActive = false;

    @Column(name = "is_deleted", columnDefinition = "boolean default 0")
    private boolean isDeleted = false;

    @Column(name = "created_at", updatable = false, nullable = true)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<Address> addresses = new ArrayList<Address>();

    private String phone;

    public User(User user) {

    }

    public User() {

    }

    public User(UserDto userDto) {
        this.id = userDto.getId();
        this.name = userDto.getName();
        this.address = userDto.getAddress();
        this.city = userDto.getCity();
    }

    public User(ConsumerUserDto user) {
        this.name = user.getName();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.phoneNumberCode = user.getPhoneNumberCode();
        this.phoneNumber = user.getPhoneNumber();
        this.role = user.getRole();
        this.isActive = user.is_active();
        this.isDeleted = user.is_deleted();
        this.address = user.getAddress();
        this.city = user.getCity();
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ref_code_id", referencedColumnName = "id")
    private RefCodeReward refCodeReward;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_promo_id", referencedColumnName = "id")
    private UserPromo userPromo;


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }




}