package me.tapumandal.ovirupa.domain.business_settings;

import me.tapumandal.ovirupa.domain.home_management.HomeManagement;
import me.tapumandal.ovirupa.domain.home_management.HomeManagementService;
import me.tapumandal.ovirupa.entity.ListFilter;
import me.tapumandal.ovirupa.util.MyPagenation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessSettingsServiceImpl implements BusinessSettingsService {

    @Autowired
    BusinessSettingsRepository businessSettingsRepository;

    @Autowired
    HomeManagementService homeManagementService;

    private BusinessSettings businessSettings;

    public BusinessSettingsServiceImpl(){}

    public BusinessSettingsServiceImpl(BusinessSettings businessSettings){
        this.businessSettings = businessSettings;
    }

    @Override
    public BusinessSettings create(BusinessSettingsDto businessSettingsDto) {

        BusinessSettings entity = new BusinessSettings(businessSettingsDto);

        //System.out.println("SERVICE: ");
        //System.out.println(new Gson().toJson(entity));
//        try{
            int id = businessSettingsRepository.create(entity);
//        }catch (Exception e){
//            return null;
//        }
        BusinessSettings businessSettings = businessSettingsRepository.getById(id);

        return businessSettings;
    }

    @Override
    public BusinessSettings update(BusinessSettingsDto businessSettingsDto) {


        BusinessSettings entity = new BusinessSettings(businessSettingsDto);

//        try{
            int id = businessSettingsRepository.update(entity);
//        }catch (Exception e){
//            return null;
//        }
        return businessSettingsRepository.getById(id);
    }


    @Override
    public List<BusinessSettings> getAll(Pageable pageable, ListFilter listFilter) {
        return null;
    }

    @Override
    public BusinessSettings getById(int id) {


        BusinessSettings  businessSettings = businessSettingsRepository.getById(id);
        HomeManagement homeManagement = homeManagementService.getById(0);
        businessSettings.setHomeProductListType(homeManagement.getProductSection());

        return businessSettings;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }

    @Override
    public BusinessSettings getByValue(String kye, String value) {
        return null;
    }

    @Override
    public List<BusinessSettings> getAllByValue(String kye, String value) {
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
