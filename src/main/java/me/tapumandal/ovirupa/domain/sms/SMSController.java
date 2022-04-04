package me.tapumandal.ovirupa.domain.sms;

import me.tapumandal.ovirupa.entity.dto.AuthenticationRequest;
import me.tapumandal.ovirupa.service.UserService;
import me.tapumandal.ovirupa.util.CommonResponseSingle;
import me.tapumandal.ovirupa.util.ControllerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/sms")
public class SMSController extends ControllerHelper<SMS> {

    @Autowired
    SMSService SMSService;

    @Autowired
    UserService userService;

    @PostMapping(path = "/otp")
    public ResponseEntity<CommonResponseSingle<String>> sendOTP(@RequestBody AuthenticationRequest authenticationRequest, HttpServletRequest request) {

        storeUserDetails(request);
        System.out.println("OTP USERNAME:"+authenticationRequest.getUsername());
        String status = SMSService.sendOTP(authenticationRequest.getUsername());
        return ResponseEntity.ok(responseCustom(true, HttpStatus.ACCEPTED, status, status));
    }


//    @PostMapping(path = "/create")
//    public CommonResponseSingle createhighlight(@ModelAttribute SMSDto SMSDto, HttpServletRequest request) {
//
//        storeUserDetails(request);
//
//        SMS SMS = SMSService.create(SMSDto);
//
//        if (SMS != null) {
//            return response(true, HttpStatus.CREATED, "New highlight inserted successfully", SMS);
//        } else if (SMS == null) {
//            return response(false, HttpStatus.BAD_REQUEST, "Something is wrong please contact", (SMS) null);
//        }
//        return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (SMS) null);
//    }
//
//    @GetMapping(path = "/get")
//    public CommonResponseArray gethighlight(HttpServletRequest request) {
//
//        storeUserDetails(request);
//
//        List<SMS> menuLists = SMSService.gethighlight();
//
//
//        if (menuLists != null) {
//            return responseCustom(true, HttpStatus.CREATED, "New highlight inserted successfully", menuLists);
//        } else if (menuLists == null) {
//            return responseCustom(false, HttpStatus.BAD_REQUEST, "Something is wrong please contact", (List<SMS>) null);
//        }
//        return responseCustom(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (List<SMS>) null);
//    }
//
//    @PostMapping(path = "/update")
//    public CommonResponseSingle updatehighlight(@ModelAttribute SMSDto SMSDto, HttpServletRequest request) {
//
//        System.out.println("updatehighlight");
//        System.out.println(new Gson().toJson(SMSDto));
//
//        storeUserDetails(request);
//
//        SMS SMS = SMSService.update(SMSDto);
//
//        if (SMS != null) {
//            return response(true, HttpStatus.OK, "New highlight inserted successfully", SMS);
//        } else if (SMS == null) {
//            return response(false, HttpStatus.BAD_REQUEST, "Something is wrong with data", (SMS) null);
//        }
//        return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (SMS) null);
//    }
//
//    @DeleteMapping(path = "/delete/{id}")
//    public void deletehighlight(@PathVariable("id") int id,  HttpServletRequest request) {
//
//        System.out.println("updatehighlight: "+id);
//
//        storeUserDetails(request);
//
//        SMSService.deleteById(id);
//    }


    @Autowired
    private CommonResponseSingle commonResponse;

    protected  CommonResponseSingle<String> responseCustom(boolean action, HttpStatus status, String message, String data){

        commonResponse.setAction(action);
        commonResponse.setStatus(status);
        commonResponse.setMessage(message);
        commonResponse.setData(data);

        return commonResponse;
    }

}