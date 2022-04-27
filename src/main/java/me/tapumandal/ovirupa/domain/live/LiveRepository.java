package me.tapumandal.ovirupa.domain.live;

import me.tapumandal.ovirupa.entity.ListFilter;
import org.hibernate.Session;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LiveRepository <Entity>{

    public Session getSession();
    public Live create(Live live);
    public Live update(Live live);
    public Live getlive();
    public List<Entity> getAll(Pageable pageable, ListFilter listFilter);
}
