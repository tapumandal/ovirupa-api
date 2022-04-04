package me.tapumandal.ovirupa.domain.home_management;
import me.tapumandal.ovirupa.entity.ListFilter;
import me.tapumandal.ovirupa.util.MyPagenation;
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
public class HomeManagementRepositoryImpl implements HomeManagementRepository {

    @Autowired
    EntityManager entityManager;

    private final String modelClassName = "HomeManagement";


    @Override
    public Session getSession() {
        return entityManager.unwrap(Session.class);
    }


    @Override
    public int create(HomeManagement homeManagement) {
//        delete();

        getSession().saveOrUpdate(homeManagement);
        getSession().flush();
        getSession().clear();
        return homeManagement.getId();
    }

    @Override
    public HomeManagement getById(int id) {

        String query = "FROM HomeManagement";
        return (HomeManagement) getSession().createQuery(query).uniqueResult();
    }

    private boolean delete(){

//        String query = "DELETE FROM HomeManagement";
//        System.out.println(query);
//        Query resQuery =  getSession().createQuery(query);
//        getSession().createSQLQuery(query).executeUpdate();

        Query qq = getSession().createQuery("DELETE DiscountTypeCondition WHERE 1 = 1");
        qq.executeUpdate();

        Query q = getSession().createQuery("DELETE HomeManagement WHERE 1 = 1");
        q.executeUpdate();

        getSession().flush();
        getSession().clear();

//        List<HomeManagement> tmpEntity = getAll(null);
//
//        if (tmpEntity.size()>0) {
//            getSession().delete(tmpEntity);
//            return true;
//        }else{
//            return false;
//        }
        return true;
    }


    @Override
    public int update(HomeManagement homeManagement) {

        Optional<HomeManagement> tmpEntity = Optional.ofNullable(getById(homeManagement.getId()));
        getSession().clear();

        if(tmpEntity.isPresent()) {
            getSession().update(homeManagement);
            getSession().flush();
            getSession().clear();
        }else{
            return create(homeManagement);
        }
        return homeManagement.getId();
    }

    @Override
    public List<HomeManagement> getAll(Pageable pageable, ListFilter listFilter) {


        Query resQuery = getQuery();

        int pageNum = pageable.getPageNumber();
        if(pageNum<1){
            pageNum = 1;
        }

        resQuery.setFirstResult((pageNum-1)*pageable.getPageSize());
        resQuery.setMaxResults(pageable.getPageSize());
        return resQuery.getResultList();
    }

    @Override
    public MyPagenation getPageable(Pageable pageable) {
        Query resQuery = getQuery();

        MyPagenation myPagenation = new MyPagenation();

        myPagenation.setTotalElement(resQuery.getResultList().size());
        return myPagenation;
    }

    @Override
    public int getAllEntityCount(Pageable pageable, ListFilter listFilter) {
        return 0;
    }

    private Query getQuery(){
        String query = "FROM HomeManagement ";
        Query resQuery =  getSession().createQuery(query);

        return resQuery;
    }




    @Override
    public List<HomeManagement> getByKeyAndValue(String key, String value) {
        return (List<HomeManagement>) getSession().createQuery(
                "from HomeManagement where "+key+" = :value"
        ).setParameter("value", value)
                .getResultList();
    }

    @Override
    public boolean delete(int id) {

//        List<HomeManagement> tmpEntity = getAll(null);
//
//        if (tmpEntity.size()>0) {
//            getSession().delete(tmpEntity);
//            return true;
//        }else{
//            return false;
//        }
        return false;
    }


}