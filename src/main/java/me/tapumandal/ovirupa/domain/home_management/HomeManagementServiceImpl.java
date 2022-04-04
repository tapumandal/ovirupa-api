package me.tapumandal.ovirupa.domain.home_management;

import me.tapumandal.ovirupa.entity.ListFilter;
import me.tapumandal.ovirupa.util.MyPagenation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeManagementServiceImpl implements HomeManagementService {

    @Autowired
    HomeManagementRepository homeManagementRepository;

    private HomeManagement homeManagement;

    public HomeManagementServiceImpl(){}

    public HomeManagementServiceImpl(HomeManagement homeManagement){
        this.homeManagement = homeManagement;
    }

    @Override
    public HomeManagement create(HomeManagementDto homeManagementDto) {

        HomeManagement entity = new HomeManagement(homeManagementDto);

        //System.out.println("SERVICE: ");
        //System.out.println(new Gson().toJson(entity));
//        try{
            int id = homeManagementRepository.create(entity);
//        }catch (Exception e){
//            return null;
//        }
        return homeManagementRepository.getById(id);
    }

    @Override
    public HomeManagement update(HomeManagementDto homeManagementDto) {


        HomeManagement entity = new HomeManagement(homeManagementDto);

//        try{
            int id = homeManagementRepository.update(entity);
//        }catch (Exception e){
//            return null;
//        }
        return homeManagementRepository.getById(id);
    }


    @Override
    public List<HomeManagement> getAll(Pageable pageable, ListFilter listFilter) {
        return null;
    }

    @Override
    public HomeManagement getById(int id) {
        return homeManagementRepository.getById(id);
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }

    @Override
    public HomeManagement getByValue(String kye, String value) {
        return null;
    }

    @Override
    public List<HomeManagement> getAllByValue(String kye, String value) {
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
