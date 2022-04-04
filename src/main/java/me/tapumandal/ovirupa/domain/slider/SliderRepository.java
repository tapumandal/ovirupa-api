package me.tapumandal.ovirupa.domain.slider;

import org.hibernate.Session;

public interface SliderRepository {

    public Session getSession();
    public Slider create(Slider slider);
    public Slider update(Slider slider);
    public Slider getSlider();
}
