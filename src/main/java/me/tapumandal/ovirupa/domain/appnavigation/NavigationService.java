package me.tapumandal.ovirupa.domain.appnavigation;

import me.tapumandal.ovirupa.service.Service;

import java.util.List;

public interface NavigationService extends Service<NavigationDto, Navigation> {
    public List<Navigation> getNavigation();
}
