package me.tapumandal.ovirupa.repository;

import me.tapumandal.ovirupa.util.MyPagenation;
import me.tapumandal.ovirupa.entity.ListFilter;
import org.hibernate.Session;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface Repository<Entity> {

    public Session getSession();

    public int create(Entity o);

    public int update(Entity o);

    public List<Entity> getAll(Pageable pageable, ListFilter listFilter);

    public Entity getById(int id);

    public List<Entity> getByKeyAndValue(String kye, String value);

    public boolean delete(int id);

    MyPagenation getPageable(Pageable pageable);

    public int getAllEntityCount(Pageable pageable, ListFilter listFilter);

}
