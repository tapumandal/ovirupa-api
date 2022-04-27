package me.tapumandal.ovirupa.domain.live;
import me.tapumandal.ovirupa.domain.product.Product;
import me.tapumandal.ovirupa.entity.ListFilter;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class LiveRepositoryImpl implements LiveRepository {

    @Autowired
    EntityManager entityManager;

    private final String modelClassName = "Live";


    @Override
    public Session getSession() {
        return entityManager.unwrap(Session.class);
    }


    @Override
    public Live create(Live live) {

        //System.out.println("SERVICE: ");
        //System.out.println(new Gson().toJson(live));
        deletelive();
        getSession().saveOrUpdate(live);
        getSession().flush();
        getSession().clear();
        return getlive();
    }

    @Override
    public Live update(Live live) {

        Optional<Live> tmpEntity = Optional.ofNullable(getlive());
        getSession().clear();

        if(tmpEntity.isPresent()) {
            getSession().update(live);
            getSession().flush();
            getSession().clear();
        }
        return getlive();
    }

    @Override
    public Live getlive() {
        String query = "FROM "+modelClassName+" M WHERE M.live IS NOT NULL";
        return (Live) getSession().createQuery(query).uniqueResult();
    }
    public void deletelive() {
        String stringQuery = "DELETE FROM "+modelClassName;
        Query query = getSession().createQuery(stringQuery);
        query.executeUpdate();

//        String query = "delete FROM "+modelClassName+" M WHERE M.live IS NOT NULL OR M.live IS NULL";
//        return (Live) getSession().createQuery(query).uniqueResult();
    }

    @Override
    public List<Product> getAll(Pageable pageable, ListFilter listFilter) {


        String query = "FROM Live P WHERE P.isDeleted = 0 Order By id DESC";
        Query resQuery =  getSession().createQuery(query);

        return getFromDB(pageable, resQuery).getResultList();
    }


    private Query getFromDB(Pageable pageable, Query resQuery){

        int pageNum = pageable.getPageNumber();
        if(pageNum<1){
            pageNum = 1;
        }

        resQuery.setFirstResult((pageNum-1)*pageable.getPageSize());
        resQuery.setMaxResults(pageable.getPageSize());
        return resQuery;
    }

}