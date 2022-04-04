package me.tapumandal.ovirupa.domain.slider;


import com.sun.istack.Nullable;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Data
public class SliderDto {
    int id;

    String title;

    @Nullable
    String followLink;

    @Nullable
    String imageUrl;


    @Nullable
    MultipartFile sliderImage;
}
