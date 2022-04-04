package me.tapumandal.ovirupa.domain.sms;


import com.sun.istack.Nullable;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Data
public class SMSDto {
    int id;

    String title;

    @Nullable
    String followLink;

    @Nullable
    String imageUrl;


    @Nullable
    MultipartFile highlightImage;
}
