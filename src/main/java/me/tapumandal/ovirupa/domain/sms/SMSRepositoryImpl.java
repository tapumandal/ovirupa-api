package me.tapumandal.ovirupa.domain.sms;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public class SMSRepositoryImpl implements SMSRepository {

    @Autowired
    EntityManager entityManager;

    private final String modelClassName = "Highlight";


    @Override
    public Session getSession() {
        return entityManager.unwrap(Session.class);
    }


    @Override
    public SMS create(SMS SMS) {

        //System.out.println("SERVICE: ");
        //System.out.println(new Gson().toJson(highlight));
        deletehighlight();
        getSession().saveOrUpdate(SMS);
        getSession().flush();
        getSession().clear();
        return gethighlight();
    }

    @Override
    public SMS update(SMS SMS) {

        Optional<SMS> tmpEntity = Optional.ofNullable(gethighlight());
        getSession().clear();

        if(tmpEntity.isPresent()) {
            getSession().update(SMS);
            getSession().flush();
            getSession().clear();
        }
        return gethighlight();
    }

    @Override
    public SMS gethighlight() {
        String query = "FROM "+modelClassName+" M WHERE M.highlight IS NOT NULL";
        return (SMS) getSession().createQuery(query).uniqueResult();
    }
    public void deletehighlight() {
        String stringQuery = "DELETE FROM "+modelClassName;
        Query query = getSession().createQuery(stringQuery);
        query.executeUpdate();

//        String query = "delete FROM "+modelClassName+" M WHERE M.highlight IS NOT NULL OR M.highlight IS NULL";
//        return (Highlight) getSession().createQuery(query).uniqueResult();
    }


}