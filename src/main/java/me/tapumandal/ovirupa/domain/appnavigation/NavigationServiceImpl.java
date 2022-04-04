package me.tapumandal.ovirupa.domain.appnavigation;

import me.tapumandal.ovirupa.entity.ImageModel;
import me.tapumandal.ovirupa.entity.ListFilter;
import me.tapumandal.ovirupa.util.ImageServiceUtil;
import me.tapumandal.ovirupa.util.MyPagenation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NavigationServiceImpl implements NavigationService {

    @Autowired
    NavigationRepository navigationRepository;

    @Autowired
    NavigationDao  navigationDao;


    @Autowired
    ImageServiceUtil imageServiceUtil;



    public NavigationServiceImpl(){}

    @Override
    public Navigation create(NavigationDto navigationDto) {

        Navigation entity = new Navigation(navigationDto);

        ImageModel navigationImgModel = new ImageModel();
        if(navigationDto.getNavigationImage() != null) {
            navigationImgModel = imageServiceUtil.uploadImage(navigationDto.getNavigationImage(), "navigation_image");
        }
        entity.setNavigationImageUrl(navigationImgModel.getUrl());

        //System.out.println("SERVICE: ");
        //System.out.println(new Gson().toJson(entity));
//        try{
            return navigationDao.save(entity);
//        }catch (Exception e){
//            return null;
//        }
    }

    @Override
    public Navigation update(NavigationDto navigationDto) {


        Navigation entity = new Navigation(navigationDto);

        if(navigationDto.getNavigationImage() != null) {
            ImageModel navigationImgModel = imageServiceUtil.uploadImage(navigationDto.getNavigationImage(), "navigation_image");
            entity.setNavigationImageUrl(navigationImgModel.getUrl());
        }

//        ImageModel navigationImgModel = imageServiceUtil.createThumbnail(navigationDto.getNavigationImage());
//        entity.setImageUrl(navigationImgModel.getUrl());
//        entity.setImageName(navigationImgModel.getName());

//        try{
            return navigationDao.save(entity);
//        }catch (Exception e){
//            return null;
//        }
    }

    @Override
    public List<Navigation> getNavigation() {


        List<Navigation> navigation =  navigationDao.findAll();
        return navigation;
    }

    @Override
    public List<Navigation> getAll(Pageable pageable, ListFilter listFilter) {
        return null;
    }

    @Override
    public Navigation getById(int id) {
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        navigationDao.deleteById(id);
        return true;
    }

    @Override
    public Navigation getByValue(String kye, String value) {
        return null;
    }

    @Override
    public List<Navigation> getAllByValue(String kye, String value) {
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
