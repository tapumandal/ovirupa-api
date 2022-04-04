package me.tapumandal.ovirupa.domain.blog;

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
public class BlogRepositoryImpl implements BlogRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    @Override
    public int create(Blog blog) {

        //System.out.println("Repository Before Save: ");
        //System.out.println(new Gson().toJson(blog));

        getSession().saveOrUpdate(blog);
        getSession().flush();
        getSession().clear();

        //System.out.println("Repository After Save: ");
        //System.out.println(new Gson().toJson(blog));
        return blog.getId();
    }

    @Override
    public int update(Blog blog) {

        Optional<Blog> tmpEntity = Optional.ofNullable(getById(blog.getId()));
        getSession().clear();

        if(tmpEntity.isPresent()) {
            getSession().update(blog);
            getSession().flush();
            getSession().clear();
        }
        return blog.getId();
    }

    @Override
    public List<Blog> getAll(Pageable pageable, ListFilter listFilter) {


        String query = "FROM Blog P WHERE P.isDeleted = 0  ORDER BY P."+listFilter.getSortBy()+" "+listFilter.getSortType();
        Query resQuery =  getSession().createQuery(query);

        return getFromDB(pageable, resQuery).getResultList();
    }

    @Override
    public List<Blog> getAllBusiness(Pageable pageable, String flag, String selectedParentMenu) {
        flag = flag.trim();
        String query = "FROM Blog P WHERE P.isDeleted = 0 AND P.isActive = 1  ORDER BY P.createdAt ASC";

        Query resQuery =  getSession().createQuery(query);

        return getFromDB(pageable, resQuery).getResultList();
    }

    @Override
    public int getAllBusinessEntityCount(Pageable pageable, String flag) {
        flag = flag.trim();
        String query = "FROM Blog P WHERE P.quantity > 0 AND P.isDeleted = 0 AND P.isActive = 1";
        Query resQuery =  getSession().createQuery(query);

        return resQuery.getResultList().size();
    }

    @Override
    public List<Blog> searchBlog(Pageable pageable, String searchString) {
        searchString = searchString.trim();
        String query = "FROM Blog P\n" +
                "WHERE title LIKE '%"+searchString+"%' AND P.isDeleted = 0 AND P.isActive = 1 \n"+
                "ORDER BY\n" +
                "  CASE\n" +
                "    WHEN title LIKE '"+searchString+"%' THEN 1\n" +
                "    WHEN title LIKE '%"+searchString+"' THEN 3\n" +
                "    ELSE 2\n" +
                "  END";
        Query resQuery =  getSession().createQuery(query);

        return getFromDB(pageable, resQuery).getResultList();
    }

    @Override
    public int searchBlogEntityCount(Pageable pageable, String searchString) {
        searchString = searchString.trim();
        String query = "FROM Blog P\n" +
                "WHERE title LIKE '%"+searchString+"%'\n" +
                "ORDER BY\n" +
                "  CASE\n" +
                "    WHEN title LIKE '"+searchString+"%' THEN 1\n" +
                "    WHEN title LIKE '%"+searchString+"' THEN 3\n" +
                "    ELSE 2\n" +
                "  END";
        Query resQuery =  getSession().createQuery(query);

        return resQuery.getResultList().size();
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

    private Query getQuery(){
        String query = "FROM Blog P WHERE P.isDeleted = 0";
        Query resQuery =  getSession().createQuery(query);

        return resQuery;
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

        String query = "FROM Blog P WHERE P.isDeleted = 0 ORDER BY P."+listFilter.getSortBy()+" "+listFilter.getSortType();
        Query resQuery =  getSession().createQuery(query);

        return resQuery.getResultList().size();
    }

    @Override
    public Blog getById(int id) {

        String query = "FROM Blog P WHERE P.id = "+id+" AND P.isDeleted = 0";
        return (Blog) getSession().createQuery(query).uniqueResult();
    }

    @Override
    public Blog getBlogByProductId(int id) {

        String query = "FROM Blog P WHERE P.productId = '"+id+"' AND P.isDeleted = 0";
        System.out.println("QUERY:"+query);
        return (Blog) getSession().createQuery(query).uniqueResult();
    }


    @Override
    public Blog getByIdAny(int id) {

        String query = "FROM Blog P WHERE P.id = "+id;
        return (Blog) getSession().createQuery(query).uniqueResult();
    }


    @Override
    public Blog getByName(String name) {

        String query = "FROM Blog P WHERE LOWER(P.title) = LOWER('"+name+"') AND P.isDeleted = 0 AND P.isActive = 1";
        return (Blog) getSession().createQuery(query).setMaxResults(1).uniqueResult();
    }

    @Override
    public Blog getByNameWithSpecialChar(String name) {

        String query = "FROM Blog P WHERE LOWER(P.title) LIKE LOWER('"+name+"') AND P.isDeleted = 0";
        return (Blog) getSession().createQuery(query).setMaxResults(1).uniqueResult();
    }

    @Override
    public List<Blog> getByKeyAndValue(String key, String value) {
        return (List<Blog>) getSession().createQuery(
                "from Blog where "+key+" = :value"
        ).setParameter("value", value)
                .getResultList();
    }

    @Override
    public boolean delete(int id) {

        Optional<Blog> tmpEntity = Optional.ofNullable(getById(id));
        if(tmpEntity.isPresent()){
            Blog blog = tmpEntity.get();
            blog.setActive(false);
            blog.setDeleted(true);
            update(blog);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean deactivateById(int id) {

        Optional<Blog> tmpEntity = Optional.ofNullable(getById(id));
        if(tmpEntity.isPresent()){
            Blog blog = tmpEntity.get();
            blog.setActive(false);
            blog.setDeleted(false);
            update(blog);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean activateById(int id) {

        Optional<Blog> tmpEntity = Optional.ofNullable(getById(id));
        if(tmpEntity.isPresent()){
            Blog blog = tmpEntity.get();
            blog.setActive(true);
            blog.setDeleted(false);
            update(blog);
            return true;
        }else{
            return false;
        }
    }

}