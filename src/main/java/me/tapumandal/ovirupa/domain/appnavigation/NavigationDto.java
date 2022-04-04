package me.tapumandal.ovirupa.domain.appnavigation;


import com.sun.istack.Nullable;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Data
public class NavigationDto {
    int navigationId;

    String navigationTitle;

    @Nullable
    String navigationImageUrl;

    @Nullable
    MultipartFile navigationImage;

    @Nullable
    private String navigationChildren;;

    private String metaTagTitle;

    private String metaTagDescription;
}
