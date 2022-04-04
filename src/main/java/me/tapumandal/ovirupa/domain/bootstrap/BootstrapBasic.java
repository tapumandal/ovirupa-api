package me.tapumandal.ovirupa.domain.bootstrap;


import lombok.Data;
import me.tapumandal.ovirupa.domain.appnavigation.Navigation;
import me.tapumandal.ovirupa.domain.highlight.Highlight;
import me.tapumandal.ovirupa.domain.slider.Slider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class BootstrapBasic {
    List<Navigation> menuLists;
    List<Slider> slider;
    List<Highlight> highlights;
}
