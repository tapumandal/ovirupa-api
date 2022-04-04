package me.tapumandal.ovirupa.domain.home_management;

import com.google.gson.Gson;
import me.tapumandal.ovirupa.util.CommonResponseSingle;
import me.tapumandal.ovirupa.util.ControllerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/home-management")
public class HomeManagementController extends ControllerHelper<HomeManagement> {

    @Autowired
    HomeManagementService homeManagementService;

    @PostMapping(path = "/create")
    public CommonResponseSingle createHomeManagement(@ModelAttribute HomeManagementDto homeManagementDto, HttpServletRequest request) {


        System.out.println("createHomeManagementDTO:");
        System.out.println(new Gson().toJson(homeManagementDto));

        storeUserDetails(request);
        HomeManagement homeManagement = homeManagementService.update(homeManagementDto);

        if (homeManagement != null) {
            return response(true, HttpStatus.CREATED, "New homeManagement inserted successfully", homeManagement);
        } else if (homeManagement == null) {
            return response(false, HttpStatus.BAD_REQUEST, "Something is wrong please contact", (HomeManagement) null);
        }
        return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (HomeManagement) null);
    }

    @GetMapping(path = "/get")
    public CommonResponseSingle getHomeManagement(HttpServletRequest request) {

        System.out.println("getHomeManagement");
        storeUserDetails(request);

        HomeManagement homeManagement = homeManagementService.getById(0);

        if (homeManagement != null) {
            return response(true, HttpStatus.CREATED, "HomeManagement inserted successfully", homeManagement);
        } else if (homeManagement == null) {
            return response(false, HttpStatus.NOT_FOUND, "HomeManagement not Found", (HomeManagement) null);
        }
        return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (HomeManagement) null);
    }


//    @PostMapping(path = "/update")
//    public CommonResponseSingle updateHomeManagement(@RequestBody HomeManagementDto homeManagementDto, HttpServletRequest request) {
//
//        storeUserDetails(request);
//
//        HomeManagement homeManagement = homeManagementService.update(homeManagementDto);
//
//        if (homeManagement != null) {
//            return response(true, HttpStatus.OK, "New homeManagement inserted successfully", homeManagement);
//        } else if (homeManagement == null) {
//            return response(false, HttpStatus.BAD_REQUEST, "Something is wrong with data", (HomeManagement) null);
//        }
//        return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (HomeManagement) null);
//    }


}