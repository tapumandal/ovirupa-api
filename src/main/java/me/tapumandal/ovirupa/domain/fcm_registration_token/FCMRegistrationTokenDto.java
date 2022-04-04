package me.tapumandal.ovirupa.domain.fcm_registration_token;


import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
public class FCMRegistrationTokenDto implements Serializable {

    @SerializedName("id")
    protected int id;

    protected int userId;

    protected String fcmRegistrationToken = "";
}
