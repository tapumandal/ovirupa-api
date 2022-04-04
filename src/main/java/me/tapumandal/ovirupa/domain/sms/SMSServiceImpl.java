package me.tapumandal.ovirupa.domain.sms;

import me.tapumandal.ovirupa.entity.ImageModel;
import me.tapumandal.ovirupa.entity.ListFilter;
import me.tapumandal.ovirupa.util.ApplicationPreferences;
import me.tapumandal.ovirupa.util.ImageServiceUtil;
import me.tapumandal.ovirupa.util.MyPagenation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class SMSServiceImpl implements SMSService {

    @Autowired
    SMSRepository SMSRepository;

    @Autowired
    SMSDao SMSDao;


    @Autowired
    ImageServiceUtil imageServiceUtil;

    @Autowired
    ApplicationPreferences applicationPreferences;

    @Autowired
    RestService restService;

    public SMSServiceImpl(){}

    @Override
    public String sendOTP(String phoneNumber) {

        Random random = new Random();
        String otp = String.format("%04d", random.nextInt(10000));



        String message = otp+" is your OTP. Welcome to ovirupa.";
        String result = restService.getPostsPlainJSON(phoneNumber, message);

        applicationPreferences.storeOTP(otp);
        System.out.println("OTP:"+otp);

        System.out.println("RESULT:"+result);

        return "OTP has been successfully send. Please check your phone.";
    }

    @Override
    public SMS create(SMSDto SMSDto) {

        SMS entity = new SMS(SMSDto);


        ImageModel highlightImgModel = imageServiceUtil.uploadImage(SMSDto.getHighlightImage(), "highlight_image");
        entity.setImageUrl(highlightImgModel.getUrl());

        //System.out.println("SERVICE: ");
        //System.out.println(new Gson().toJson(entity));
//        try{
            return SMSDao.save(entity);
//        }catch (Exception e){
//            return null;
//        }
    }

    @Override
    public SMS update(SMSDto SMSDto) {


        SMS entity = new SMS(SMSDto);

        if(SMSDto.getHighlightImage() != null) {
            System.out.println("CATEGORY BANNER IS NOT NULL");
            ImageModel highlightImgModel = imageServiceUtil.uploadImage(SMSDto.getHighlightImage(), "highlight_image");
            entity.setImageUrl(highlightImgModel.getUrl());
        }

//        ImageModel highlightImgModel = imageServiceUtil.createThumbnail(highlightDto.getHighlightImage());
//        entity.setImageUrl(highlightImgModel.getUrl());
//        entity.setImageName(highlightImgModel.getName());

//        try{
            return SMSDao.save(entity);
//        }catch (Exception e){
//            return null;
//        }
    }

    @Override
    public List<SMS> gethighlight() {

        List<SMS> SMS =  SMSDao.findAll();


        return SMS;
    }

    @Override
    public List<SMS> getAll(Pageable pageable, ListFilter listFilter) {
        return null;
    }

    @Override
    public SMS getById(int id) {
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        SMSDao.deleteById(id);
        return true;
    }

    @Override
    public SMS getByValue(String kye, String value) {
        return null;
    }

    @Override
    public List<SMS> getAllByValue(String kye, String value) {
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
