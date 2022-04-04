package me.tapumandal.ovirupa.domain.image;

import me.tapumandal.ovirupa.entity.ImageModel;
import me.tapumandal.ovirupa.util.ImageServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ImageServiceUtil imageServiceUtil;

    private Image image;

    private String blogImageIdentification = "";

    public ImageServiceImpl(){}

    public ImageServiceImpl(Image image){
        this.image = image;
    }

    @Override
    public Image getImageByName(String name) {
        Optional<Image> image = Optional.ofNullable(imageRepository.getImageByName(name));

        if(image.isPresent()){
            return image.get();
        }else{
            return null;
        }
    }

    @Override
    public List<Image> getImageByProductId(int productId) {
        List<Image> image = imageRepository.getImageByProductId(productId);
        return image;
    }

    @Override
    public List<Image> getAllByImageBlogIdentifire(String value) {
        List<Image> images = imageRepository.getAllByImageBlogIdentifire(value);
        return images;
    }

    @Override
    public boolean deleteImageByName(String name) {
        try {
            return imageRepository.delete(name);
        }catch (Exception ex){
            return false;
        }
    }

    @Override
    public List<Image> create(ImageDto imageDto) {

        blogImageIdentification = imageDto.getBlogImageIdentification();
        if(imageDto.getImages().length>0) {
            List<Image> images = storeBlogImages(imageDto.getImages());
            for (Image img: images) {
                imageRepository.create(img);
            }
        }
        return imageRepository.getImageByBlogImageIdentification(blogImageIdentification);
    }
    public List<Image> storeBlogImages(MultipartFile[] images) {

        List<ImageModel> imageModels = imageServiceUtil.store(images);

        List<Image> productImages = new ArrayList<>();

        for (ImageModel tmp: imageModels) {
            Image image = new Image();
            image.setName(tmp.getName());
            image.setUrl(tmp.getUrl());
            image.setSize(tmp.getSize());
            image.setType("BLOG");
            image.setBlogImageIdentification(blogImageIdentification);
            productImages.add(image);
        }

        return productImages;
    }
}
