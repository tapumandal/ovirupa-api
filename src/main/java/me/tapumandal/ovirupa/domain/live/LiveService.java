package me.tapumandal.ovirupa.domain.live;

import me.tapumandal.ovirupa.service.Service;

import java.awt.print.Pageable;
import java.util.List;

public interface LiveService extends Service<LiveDto, Live> {
    public List<Live> getlive();
}
