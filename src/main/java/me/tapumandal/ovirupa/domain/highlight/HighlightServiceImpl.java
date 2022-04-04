package me.tapumandal.ovirupa.domain.highlight;

import me.tapumandal.ovirupa.entity.ImageModel;
import me.tapumandal.ovirupa.entity.ListFilter;
import me.tapumandal.ovirupa.util.ImageServiceUtil;
import me.tapumandal.ovirupa.util.MyPagenation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HighlightServiceImpl implements HighlightService {

    @Autowired
    HighlightRepository highlightRepository;

    @Autowired
    HighlightDao highlightDao;


    @Autowired
    ImageServiceUtil imageServiceUtil;



    public HighlightServiceImpl(){}

    @Override
    public Highlight create(HighlightDto highlightDto) {

        Highlight entity = new Highlight(highlightDto);


        ImageModel highlightImgModel = imageServiceUtil.uploadImage(highlightDto.getHighlightImage(), "highlight_image");
        entity.setImageUrl(highlightImgModel.getUrl());

        //System.out.println("SERVICE: ");
        //System.out.println(new Gson().toJson(entity));
//        try{
            return highlightDao.save(entity);
//        }catch (Exception e){
//            return null;
//        }
    }

    @Override
    public Highlight update(HighlightDto highlightDto) {


        Highlight entity = new Highlight(highlightDto);

        if(highlightDto.getHighlightImage() != null) {
            System.out.println("CATEGORY BANNER IS NOT NULL");
            ImageModel highlightImgModel = imageServiceUtil.uploadImage(highlightDto.getHighlightImage(), "highlight_image");
            entity.setImageUrl(highlightImgModel.getUrl());
        }

//        ImageModel highlightImgModel = imageServiceUtil.createThumbnail(highlightDto.getHighlightImage());
//        entity.setImageUrl(highlightImgModel.getUrl());
//        entity.setImageName(highlightImgModel.getName());

//        try{
            return highlightDao.save(entity);
//        }catch (Exception e){
//            return null;
//        }
    }

    @Override
    public List<Highlight> gethighlight() {


        List<Highlight> highlight =  highlightDao.findAll();


        return highlight;
    }

    @Override
    public List<Highlight> getAll(Pageable pageable, ListFilter listFilter) {
        return null;
    }

    @Override
    public Highlight getById(int id) {
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        highlightDao.deleteById(id);
        return true;
    }

    @Override
    public Highlight getByValue(String kye, String value) {
        return null;
    }

    @Override
    public List<Highlight> getAllByValue(String kye, String value) {
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
