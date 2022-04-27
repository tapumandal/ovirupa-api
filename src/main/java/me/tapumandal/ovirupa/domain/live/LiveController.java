package me.tapumandal.ovirupa.domain.live;

import com.google.gson.Gson;
import me.tapumandal.ovirupa.util.CommonResponseArray;
import me.tapumandal.ovirupa.util.CommonResponseSingle;
import me.tapumandal.ovirupa.util.ControllerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/live")
public class LiveController extends ControllerHelper<Live> {

    @Autowired
    LiveService liveService;

    @PostMapping(path = "/create")
    public CommonResponseSingle createlive(@ModelAttribute LiveDto liveDto, HttpServletRequest request) {
        System.out.println("HIGHLIGHT CONTROLLER: createlive");

        storeUserDetails(request);

        Live live = liveService.create(liveDto);

        if (live != null) {
            return response(true, HttpStatus.CREATED, "New live inserted successfully", live);
        } else if (live == null) {
            return response(false, HttpStatus.BAD_REQUEST, "Something is wrong please contact", (Live) null);
        }
        return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (Live) null);
    }

    @GetMapping(path = "/list")
    public CommonResponseArray getliveList(HttpServletRequest request, Pageable pageable) {

        storeUserDetails(request);

        List<Live> menuLists = liveService.getAll(pageable, null);


        if (menuLists != null) {
            return responseCustom(true, HttpStatus.CREATED, "New live inserted successfully", menuLists);
        } else if (menuLists == null) {
            return responseCustom(false, HttpStatus.BAD_REQUEST, "Something is wrong please contact", (List<Live>) null);
        }
        return responseCustom(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (List<Live>) null);
    }

    @PostMapping(path = "/update")
    public CommonResponseSingle updatelive(@ModelAttribute LiveDto liveDto, HttpServletRequest request) {
        System.out.println("HIGHLIGHT CONTROLLER: updatelive");

        System.out.println("updatelive");
        System.out.println(new Gson().toJson(liveDto));

        storeUserDetails(request);

        Live live = liveService.update(liveDto);

        if (live != null) {
            return response(true, HttpStatus.OK, "New live inserted successfully", live);
        } else if (live == null) {
            return response(false, HttpStatus.BAD_REQUEST, "Something is wrong with data", (Live) null);
        }
        return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (Live) null);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deletelive(@PathVariable("id") int id,  HttpServletRequest request) {
        System.out.println("HIGHLIGHT CONTROLLER: deletelive");

        System.out.println("updatelive: "+id);

        storeUserDetails(request);

        liveService.deleteById(id);
    }


    @Autowired
    private   CommonResponseArray commonResponseArray;

    protected  CommonResponseArray<List<Live>> responseCustom(boolean action, HttpStatus status, String message, List<Live> data){

        commonResponseArray.setAction(action);
        commonResponseArray.setStatus(status);
        commonResponseArray.setMessage(message);
        commonResponseArray.setData(data);

        return commonResponseArray;
    }

}