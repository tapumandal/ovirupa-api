package me.tapumandal.ovirupa.domain.appnavigation;

import com.google.gson.Gson;
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
@RequestMapping("/api/v1/navigation")
public class NavigationController extends ControllerHelper<Navigation> {

    @Autowired
    NavigationService navigationService;

//    @PostMapping(path = "/create")
//    public CommonResponseSingle createNavigation(@ModelAttribute NavigationDto navigationDto, HttpServletRequest request) {
//
//
//        storeUserDetails(request);
//        Navigation navigation = navigationService.create(navigationDto);
//
//        if (navigation != null) {
//            return response(true, HttpStatus.CREATED, "New navigation inserted successfully", navigation);
//        } else if (navigation == null) {
//            return response(false, HttpStatus.BAD_REQUEST, "Something is wrong please contact", (Navigation) null);
//        }
//        return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (Navigation) null);
//    }
    @PostMapping(path = "/create")
    public CommonResponseSingle createNavigation(@ModelAttribute NavigationDto navigationDto, HttpServletRequest request) {

        storeUserDetails(request);

        Navigation navigation = navigationService.create(navigationDto);

        if (navigation != null) {
            return response(true, HttpStatus.CREATED, "New navigation inserted successfully", navigation);
        } else if (navigation == null) {
            return response(false, HttpStatus.BAD_REQUEST, "Something is wrong please contact", (Navigation) null);
        }
        return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (Navigation) null);
    }

    @GetMapping(path = "/get")
    public CommonResponseArray getNavigation(HttpServletRequest request) {

        storeUserDetails(request);

        List<Navigation> menuLists = navigationService.getNavigation();


        System.out.println("Navigation");
        System.out.println(new Gson().toJson(menuLists));

        if (menuLists != null) {
            return responseCustom(true, HttpStatus.CREATED, "New navigation inserted successfully", menuLists);
        } else if (menuLists == null) {
            return responseCustom(false, HttpStatus.BAD_REQUEST, "Something is wrong please contact", (List<Navigation>) null);
        }
        return responseCustom(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (List<Navigation>) null);
    }

    @PostMapping(path = "/update")
    public CommonResponseSingle updateNavigation(@ModelAttribute NavigationDto navigationDto, HttpServletRequest request) {


        storeUserDetails(request);

        Navigation navigation = navigationService.update(navigationDto);

        if (navigation != null) {
            return response(true, HttpStatus.OK, "New navigation inserted successfully", navigation);
        } else if (navigation == null) {
            return response(false, HttpStatus.BAD_REQUEST, "Something is wrong with data", (Navigation) null);
        }
        return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (Navigation) null);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteNavigation(@PathVariable("id") int id,  HttpServletRequest request) {


        storeUserDetails(request);

        navigationService.deleteById(id);
    }


    @Autowired
    private   CommonResponseArray commonResponseArray;

    protected  CommonResponseArray<List<Navigation>> responseCustom(boolean action, HttpStatus status, String message, List<Navigation> data){

        commonResponseArray.setAction(action);
        commonResponseArray.setStatus(status);
        commonResponseArray.setMessage(message);
        commonResponseArray.setData(data);

        return commonResponseArray;
    }

}