package me.tapumandal.ovirupa.domain.slider;

import me.tapumandal.ovirupa.util.CommonResponseArray;
import me.tapumandal.ovirupa.util.CommonResponseSingle;
import me.tapumandal.ovirupa.util.ControllerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/slider")
public class SliderController extends ControllerHelper<Slider> {

    @Autowired
    SliderService sliderService;

    @PostMapping(path = "/create")
    public CommonResponseSingle createSlider(@ModelAttribute SliderDto sliderDto, HttpServletRequest request) {

        storeUserDetails(request);

        Slider slider = sliderService.create(sliderDto);

        if (slider != null) {
            return response(true, HttpStatus.CREATED, "New slider inserted successfully", slider);
        } else if (slider == null) {
            return response(false, HttpStatus.BAD_REQUEST, "Something is wrong please contact", (Slider) null);
        }
        return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (Slider) null);
    }

    @GetMapping(path = "/get")
    public CommonResponseArray getSlider(HttpServletRequest request) {

        storeUserDetails(request);

        List<Slider> menuLists = sliderService.getSlider();


        if (menuLists != null) {
            return responseCustom(true, HttpStatus.CREATED, "New slider inserted successfully", menuLists);
        } else if (menuLists == null) {
            return responseCustom(false, HttpStatus.BAD_REQUEST, "Something is wrong please contact", (List<Slider>) null);
        }
        return responseCustom(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (List<Slider>) null);
    }

    @PostMapping(path = "/update")
    public CommonResponseSingle updateSlider(@ModelAttribute SliderDto sliderDto, HttpServletRequest request) {


        storeUserDetails(request);

        Slider slider = sliderService.update(sliderDto);

        if (slider != null) {
            return response(true, HttpStatus.OK, "New slider inserted successfully", slider);
        } else if (slider == null) {
            return response(false, HttpStatus.BAD_REQUEST, "Something is wrong with data", (Slider) null);
        }
        return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (Slider) null);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteSlider(@PathVariable("id") int id,  HttpServletRequest request) {


        storeUserDetails(request);

        sliderService.deleteById(id);
    }


    @Autowired
    private   CommonResponseArray commonResponseArray;

    protected  CommonResponseArray<List<Slider>> responseCustom(boolean action, HttpStatus status, String message, List<Slider> data){

        commonResponseArray.setAction(action);
        commonResponseArray.setStatus(status);
        commonResponseArray.setMessage(message);
        commonResponseArray.setData(data);

        return commonResponseArray;
    }

}


