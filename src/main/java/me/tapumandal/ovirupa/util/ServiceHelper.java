package me.tapumandal.ovirupa.util;

import me.tapumandal.ovirupa.entity.ImageModel;
import me.tapumandal.ovirupa.domain.image.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceHelper {


    @Autowired
    ImageServiceUtil imageServiceUtil;

    public List<Image> storeProductImages(MultipartFile[] images) {

        List<ImageModel> imageModels = imageServiceUtil.store(images);

        List<Image> productImages = new ArrayList<>();

        for (ImageModel tmp: imageModels) {
            Image image = new Image();
            image.setName(tmp.getName());
            image.setUrl(tmp.getUrl());
            image.setSize(tmp.getSize());

            productImages.add(image);
        }

        return productImages;
    }
}
