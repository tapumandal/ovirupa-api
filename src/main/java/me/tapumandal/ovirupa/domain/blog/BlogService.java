package me.tapumandal.ovirupa.domain.blog;

import me.tapumandal.ovirupa.service.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService extends Service<BlogDto, Blog> {

    public List<Blog> getAllBusiness(Pageable pageable, String category, String selectedParentMenu);
    public int getAllBusinessEntityCount(Pageable pageable, String flag);

    List<Blog> searchBlog(Pageable pageable, String query);
    public int searchBlogEntityCount(Pageable pageable, String query);

    public Blog getByName(String name);

    boolean deactivateById(int id);

    boolean activateById(int id);

    Blog getBlogByProductId(int id);
}
