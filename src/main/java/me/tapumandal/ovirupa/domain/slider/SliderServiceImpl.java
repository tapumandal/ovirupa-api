package me.tapumandal.ovirupa.domain.slider;

import me.tapumandal.ovirupa.entity.ImageModel;
import me.tapumandal.ovirupa.entity.ListFilter;
import me.tapumandal.ovirupa.util.ImageServiceUtil;
import me.tapumandal.ovirupa.util.MyPagenation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SliderServiceImpl implements SliderService {

    @Autowired
    SliderRepository sliderRepository;

    @Autowired
    SliderDao sliderDao;


    @Autowired
    ImageServiceUtil imageServiceUtil;



    public SliderServiceImpl(){}

    @Override
    public Slider create(SliderDto sliderDto) {

        Slider entity = new Slider(sliderDto);


        ImageModel sliderImgModel = imageServiceUtil.uploadImage(sliderDto.getSliderImage(), "slider_image");
        entity.setImageUrl(sliderImgModel.getUrl());

        //System.out.println("SERVICE: ");
        //System.out.println(new Gson().toJson(entity));
//        try{
            return sliderDao.save(entity);
//        }catch (Exception e){
//            return null;
//        }
    }

    @Override
    public Slider update(SliderDto sliderDto) {


        Slider entity = new Slider(sliderDto);

        if(sliderDto.getSliderImage() != null) {
            System.out.println("CATEGORY BANNER IS NOT NULL");
            ImageModel sliderImgModel = imageServiceUtil.uploadImage(sliderDto.getSliderImage(), "slider_image");
            entity.setImageUrl(sliderImgModel.getUrl());
        }

//        ImageModel sliderImgModel = imageServiceUtil.createThumbnail(sliderDto.getSliderImage());
//        entity.setImageUrl(sliderImgModel.getUrl());
//        entity.setImageName(sliderImgModel.getName());

//        try{
            return sliderDao.save(entity);
//        }catch (Exception e){
//            return null;
//        }
    }

    @Override
    public List<Slider> getSlider() {


        List<Slider> slider =  sliderDao.findAll();


        return slider;
    }

    @Override
    public List<Slider> getAll(Pageable pageable, ListFilter listFilter) {
        return null;
    }

    @Override
    public Slider getById(int id) {
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        sliderDao.deleteById(id);
        return true;
    }

    @Override
    public Slider getByValue(String kye, String value) {
        return null;
    }

    @Override
    public List<Slider> getAllByValue(String kye, String value) {
        return null;
    }

    @Override
    public boolean isActive(int id) {
        return false;
    }

    @Override
    public boolean isDeleted(int id) {
        return false;
    }

    @Override
    public MyPagenation getPageable(Pageable pageable) {
        return null;
    }

    @Override
    public int getAllEntityCount(Pageable pageable, ListFilter listFilter) {
        return 0;
    }

}
