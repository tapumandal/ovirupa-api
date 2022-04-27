package me.tapumandal.ovirupa.domain.live;

import me.tapumandal.ovirupa.domain.product.Product;
import me.tapumandal.ovirupa.entity.ImageModel;
import me.tapumandal.ovirupa.entity.ListFilter;
import me.tapumandal.ovirupa.util.ImageServiceUtil;
import me.tapumandal.ovirupa.util.MyPagenation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LiveServiceImpl implements LiveService {

    @Autowired
    LiveRepository liveRepository;

    @Autowired
    LiveDao liveDao;


    @Autowired
    ImageServiceUtil imageServiceUtil;



    public LiveServiceImpl(){}

    @Override
    public Live create(LiveDto liveDto) {

        Live entity = new Live(liveDto);


        ImageModel liveImgModel = imageServiceUtil.uploadImage(liveDto.getLiveImage(), "live_image");
        entity.setImage(liveImgModel.getUrl());

        //System.out.println("SERVICE: ");
        //System.out.println(new Gson().toJson(entity));
//        try{
            return liveDao.save(entity);
//        }catch (Exception e){
//            return null;
//        }
    }

    @Override
    public Live update(LiveDto liveDto) {


        Live entity = new Live(liveDto);

        if(liveDto.getLiveImage() != null) {
            System.out.println("CATEGORY BANNER IS NOT NULL");
            ImageModel liveImgModel = imageServiceUtil.uploadImage(liveDto.getLiveImage(), "live_image");
            entity.setImage(liveImgModel.getUrl());
        }

//        ImageModel liveImgModel = imageServiceUtil.createThumbnail(liveDto.getLiveImage());
//        entity.setImageUrl(liveImgModel.getUrl());
//        entity.setImageName(liveImgModel.getName());

//        try{
            return liveDao.save(entity);
//        }catch (Exception e){
//            return null;
//        }
    }

    @Override
    public List<Live> getlive() {


        List<Live> live =  liveDao.findAll();


        return live;
    }

    @Override
    public List<Live> getAll(Pageable pageable, ListFilter listFilter) {
        Optional<List<Live>> products = Optional.ofNullable(liveRepository.getAll(pageable, listFilter));

        if(products.isPresent()){
            return products.get();
        }else{
            return null;
        }
    }

    @Override
    public Live getById(int id) {
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        liveDao.deleteById(id);
        return true;
    }

    @Override
    public Live getByValue(String kye, String value) {
        return null;
    }

    @Override
    public List<Live> getAllByValue(String kye, String value) {
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
