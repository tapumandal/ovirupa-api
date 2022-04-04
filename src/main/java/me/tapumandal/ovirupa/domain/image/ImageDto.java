package me.tapumandal.ovirupa.domain.image;

import com.sun.istack.Nullable;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Data
public class ImageDto {
    @Nullable
    private int id;

    @Nullable
    private String name;

    @Nullable
    private MultipartFile[] images;

    @Nullable
    private String blogImageIdentification = "";

    private boolean isActive = true;
    private boolean isDeleted = false;

}
