package me.tapumandal.ovirupa.domain.bootstrap;

import me.tapumandal.ovirupa.domain.appnavigation.Navigation;
import me.tapumandal.ovirupa.domain.appnavigation.NavigationService;
import me.tapumandal.ovirupa.domain.highlight.Highlight;
import me.tapumandal.ovirupa.domain.highlight.HighlightService;
import me.tapumandal.ovirupa.domain.slider.Slider;
import me.tapumandal.ovirupa.domain.slider.SliderService;
import me.tapumandal.ovirupa.util.CommonResponseSingle;
import me.tapumandal.ovirupa.util.ControllerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/bootstrap")
public class BootstrapController extends ControllerHelper<BootstrapBasic> {

    @Autowired
    NavigationService navigationService;

    @Autowired
    SliderService sliderService;

    @Autowired
    HighlightService highlightService;

    @GetMapping(path = "/basic")
    public CommonResponseSingle getNavigation(HttpServletRequest request) {
        storeUserDetails(request);

        List<Navigation> menuLists = navigationService.getNavigation();
        List<Slider> slider =  sliderService.getSlider();
        List<Highlight> highlights =  highlightService.gethighlight();


        BootstrapBasic bootstrapBasic = new BootstrapBasic();

        bootstrapBasic.setMenuLists(menuLists);
        bootstrapBasic.setSlider(slider);
        bootstrapBasic.setHighlights(highlights);

        if (bootstrapBasic != null) {
            return response(true, HttpStatus.OK, "Bootstrap basic data fetch successfully", bootstrapBasic);
        } else if (bootstrapBasic == null) {
            return response(false, HttpStatus.BAD_REQUEST, "Something is wrong with data", (BootstrapBasic) null);
        }
        return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (BootstrapBasic) null);
    }
}