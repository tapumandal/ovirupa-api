package me.tapumandal.ovirupa.domain.live;


import com.sun.istack.Nullable;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Component
@Data
public class LiveDto {
    int id;

    String title;

    @Nullable
    String description;

    @Nullable
    String image;


    @Nullable
    MultipartFile liveImage;


    String facebookLink;

    Date createdAt;
}
