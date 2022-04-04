package me.tapumandal.ovirupa.domain.cart;
import me.tapumandal.ovirupa.entity.ListFilter;
import me.tapumandal.ovirupa.util.MyPagenation;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class CartRepositoryImpl implements CartRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public Session getSession() {
        return entityManager.unwrap(Session.class);
    }


    @Override
    public int create(Cart cart) {

        getSession().saveOrUpdate(cart);
        getSession().flush();
        getSession().clear();
        return cart.getId();
    }

    @Override
    public int update(Cart cart) {

        Optional<Cart> tmpEntity = Optional.ofNullable(getById(cart.getId()));
        getSession().clear();

        if(tmpEntity.isPresent()) {
            getSession().update(cart);
            getSession().flush();
            getSession().clear();
        }
        return cart.getId();
    }

    @Override
    public List<Cart> getAll(Pageable pageable, ListFilter listFilter) {

        String query = "FROM Cart C WHERE C.isDeleted = 0 AND C.status != 'Delete' ORDER BY id DESC";
        Query resQuery =  getSession().createQuery(query);
        //System.out.println("ADMIN CART LIST"+new Gson().toJson(resQuery.getResultList()));
        getSession().flush();
        getSession().clear();
        return getPageableData(resQuery, pageable);
    }


    @Override
    public List<Cart> getAllByUserID(int phoneNumber, Pageable pageable) {
        String query = "FROM Cart C WHERE C.mobileNo = "+phoneNumber+" AND C.isDeleted = 0 AND C.status != 'Delete' ORDER BY id DESC";
        Query resQuery =  getSession().createQuery(query);

        return getPageableData(resQuery, pageable);
    }

    @Override
    public int getAllByIDEntityCount(Pageable pageable, int id) {
        String query = "FROM Cart C WHERE C.userId = "+id+" AND C.isDeleted = 0 AND C.status != 'Delete' ORDER BY id DESC";
        Query resQuery =  getSession().createQuery(query);
        return resQuery.getResultList().size();
    }

    private List<Cart> getPageableData(Query resQuery, Pageable pageable){
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
        String query = "FROM Cart C WHERE C.isDeleted = 0 AND C.status != 'Delete' ORDER BY id DESC";
        Query resQuery =  getSession().createQuery(query);
        MyPagenation myPagenation = new MyPagenation();

        myPagenation.setTotalElement(resQuery.getResultList().size());
        return myPagenation;
    }

    @Override
    public int getAllEntityCount(Pageable pageable, ListFilter listFilter) {
        String query = "FROM Cart C WHERE C.isDeleted = 0 AND C.status != 'Delete' ORDER BY id DESC";
        Query resQuery =  getSession().createQuery(query);

        return resQuery.getResultList().size();
    }


    @Override
    public Cart getById(int id) {

        String query = "FROM Cart C WHERE C.id = "+id+" AND C.isDeleted = 0 AND C.status != 'Delete'";
        return (Cart) getSession().createQuery(query).uniqueResult();
    }

    @Override
    public Cart getCartFirstTime(int id) {

        String query = "FROM Cart C WHERE C.id = "+id+" AND C.isDeleted = 0 AND C.status != 'Delete'";
        return (Cart) getSession().createQuery(query).uniqueResult();
    }


    @Override
    public List<Cart> getByKeyAndValue(String key, String value) {
        return (List<Cart>) getSession().createQuery(
                "from Cart where "+key+" = :value AND status != 'Delete'"
        ).setParameter("value", value)
                .getResultList();
    }

    @Override
    public boolean delete(int id) {

        Optional<Cart> tmpEntity = Optional.ofNullable(getById(id));
        if(tmpEntity.isPresent()){
            Cart cart = tmpEntity.get();
            cart.setActive(false);
            cart.setDeleted(true);
            update(cart);
            return true;
        }else{
            return false;
        }
    }


    @Override
    public List<Cart> getAllCartByDateRange(LocalDateTime start, LocalDateTime end, Pageable pageable) {

        String query = "FROM Cart C WHERE C.createdAt between "+start+" AND "+end+" ORDER BY id DESC";
        Query resQuery =  getSession().createQuery(query);

        return getPageableData(resQuery, pageable);
    }

}