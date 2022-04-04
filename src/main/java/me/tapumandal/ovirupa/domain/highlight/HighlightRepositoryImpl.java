package me.tapumandal.ovirupa.domain.highlight;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public class HighlightRepositoryImpl implements HighlightRepository {

    @Autowired
    EntityManager entityManager;

    private final String modelClassName = "Highlight";


    @Override
    public Session getSession() {
        return entityManager.unwrap(Session.class);
    }


    @Override
    public Highlight create(Highlight highlight) {

        //System.out.println("SERVICE: ");
        //System.out.println(new Gson().toJson(highlight));
        deletehighlight();
        getSession().saveOrUpdate(highlight);
        getSession().flush();
        getSession().clear();
        return gethighlight();
    }

    @Override
    public Highlight update(Highlight highlight) {

        Optional<Highlight> tmpEntity = Optional.ofNullable(gethighlight());
        getSession().clear();

        if(tmpEntity.isPresent()) {
            getSession().update(highlight);
            getSession().flush();
            getSession().clear();
        }
        return gethighlight();
    }

    @Override
    public Highlight gethighlight() {
        String query = "FROM "+modelClassName+" M WHERE M.highlight IS NOT NULL";
        return (Highlight) getSession().createQuery(query).uniqueResult();
    }
    public void deletehighlight() {
        String stringQuery = "DELETE FROM "+modelClassName;
        Query query = getSession().createQuery(stringQuery);
        query.executeUpdate();

//        String query = "delete FROM "+modelClassName+" M WHERE M.highlight IS NOT NULL OR M.highlight IS NULL";
//        return (Highlight) getSession().createQuery(query).uniqueResult();
    }


}