package me.tapumandal.ovirupa.domain.highlight;

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
@CrossOrigin
@RequestMapping("/api/v1/highlight")
public class HighlightController extends ControllerHelper<Highlight> {

    @Autowired
    HighlightService highlightService;

    @PostMapping(path = "/create")
    public CommonResponseSingle createhighlight(@ModelAttribute HighlightDto highlightDto, HttpServletRequest request) {
        System.out.println("HIGHLIGHT CONTROLLER: createhighlight");

        storeUserDetails(request);

        Highlight highlight = highlightService.create(highlightDto);

        if (highlight != null) {
            return response(true, HttpStatus.CREATED, "New highlight inserted successfully", highlight);
        } else if (highlight == null) {
            return response(false, HttpStatus.BAD_REQUEST, "Something is wrong please contact", (Highlight) null);
        }
        return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (Highlight) null);
    }

    @GetMapping(path = "/get")
    public CommonResponseArray gethighlight(HttpServletRequest request) {

        storeUserDetails(request);

        List<Highlight> menuLists = highlightService.gethighlight();


        if (menuLists != null) {
            return responseCustom(true, HttpStatus.CREATED, "New highlight inserted successfully", menuLists);
        } else if (menuLists == null) {
            return responseCustom(false, HttpStatus.BAD_REQUEST, "Something is wrong please contact", (List<Highlight>) null);
        }
        return responseCustom(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (List<Highlight>) null);
    }

    @PostMapping(path = "/update")
    public CommonResponseSingle updatehighlight(@ModelAttribute HighlightDto highlightDto, HttpServletRequest request) {
        System.out.println("HIGHLIGHT CONTROLLER: updatehighlight");

        System.out.println("updatehighlight");
        System.out.println(new Gson().toJson(highlightDto));

        storeUserDetails(request);

        Highlight highlight = highlightService.update(highlightDto);

        if (highlight != null) {
            return response(true, HttpStatus.OK, "New highlight inserted successfully", highlight);
        } else if (highlight == null) {
            return response(false, HttpStatus.BAD_REQUEST, "Something is wrong with data", (Highlight) null);
        }
        return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (Highlight) null);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deletehighlight(@PathVariable("id") int id,  HttpServletRequest request) {
        System.out.println("HIGHLIGHT CONTROLLER: deletehighlight");

        System.out.println("updatehighlight: "+id);

        storeUserDetails(request);

        highlightService.deleteById(id);
    }


    @Autowired
    private   CommonResponseArray commonResponseArray;

    protected  CommonResponseArray<List<Highlight>> responseCustom(boolean action, HttpStatus status, String message, List<Highlight> data){

        commonResponseArray.setAction(action);
        commonResponseArray.setStatus(status);
        commonResponseArray.setMessage(message);
        commonResponseArray.setData(data);

        return commonResponseArray;
    }

}