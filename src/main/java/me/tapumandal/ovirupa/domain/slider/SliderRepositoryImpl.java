package me.tapumandal.ovirupa.domain.slider;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public class SliderRepositoryImpl implements SliderRepository {

    @Autowired
    EntityManager entityManager;

    private final String modelClassName = "Slider";


    @Override
    public Session getSession() {
        return entityManager.unwrap(Session.class);
    }


    @Override
    public Slider create(Slider slider) {

        //System.out.println("SERVICE: ");
        //System.out.println(new Gson().toJson(slider));
        deleteSlider();
        getSession().saveOrUpdate(slider);
        getSession().flush();
        getSession().clear();
        return getSlider();
    }

    @Override
    public Slider update(Slider slider) {

        Optional<Slider> tmpEntity = Optional.ofNullable(getSlider());
        getSession().clear();

        if(tmpEntity.isPresent()) {
            getSession().update(slider);
            getSession().flush();
            getSession().clear();
        }
        return getSlider();
    }

    @Override
    public Slider getSlider() {
        String query = "FROM "+modelClassName+" M WHERE M.slider IS NOT NULL";
        return (Slider) getSession().createQuery(query).uniqueResult();
    }
    public void deleteSlider() {
        String stringQuery = "DELETE FROM "+modelClassName;
        Query query = getSession().createQuery(stringQuery);
        query.executeUpdate();

//        String query = "delete FROM "+modelClassName+" M WHERE M.slider IS NOT NULL OR M.slider IS NULL";
//        return (Slider) getSession().createQuery(query).uniqueResult();
    }


}