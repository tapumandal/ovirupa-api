package me.tapumandal.ovirupa.domain.highlight;


import com.sun.istack.Nullable;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Data
public class HighlightDto {
    int id;

    String title;

    @Nullable
    String followLink;

    @Nullable
    String imageUrl;


    @Nullable
    MultipartFile highlightImage;
}
