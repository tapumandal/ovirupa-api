package me.tapumandal.ovirupa.domain.blog;

import me.tapumandal.ovirupa.repository.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogRepository extends Repository<Blog> {

    public List<Blog> getAllBusiness(Pageable pageable, String flag, String selectedParentMenu);
    public int getAllBusinessEntityCount(Pageable pageable, String flag);

    List<Blog> searchBlog(Pageable pageable, String query);
    public int searchBlogEntityCount(Pageable pageable, String query);

    public Blog getByName(String name);
    public Blog getByNameWithSpecialChar(String name);

    boolean deactivateById(int id);

    boolean activateById(int id);

    Blog getByIdAny(int blogId);

    Blog getBlogByProductId(int id);
}
