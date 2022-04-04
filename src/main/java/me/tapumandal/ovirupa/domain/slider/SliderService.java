package me.tapumandal.ovirupa.domain.slider;

import me.tapumandal.ovirupa.service.Service;

import java.util.List;

public interface SliderService extends Service<SliderDto, Slider> {
    public List<Slider> getSlider();
}
