package me.tapumandal.ovirupa.domain.blog;

import com.google.gson.Gson;
import me.tapumandal.ovirupa.entity.ListFilter;
import me.tapumandal.ovirupa.entity.dto.ListFilterDto;
import me.tapumandal.ovirupa.util.CommonResponseArray;
import me.tapumandal.ovirupa.util.CommonResponseSingle;
import me.tapumandal.ovirupa.util.ControllerHelper;
import me.tapumandal.ovirupa.util.MyPagenation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/blog")
public class BlogController extends ControllerHelper<Blog> {

    @Autowired
    BlogService blogService;


    @PostMapping(path = "/create")
    public ResponseEntity<CommonResponseSingle> createBlog(@ModelAttribute BlogDto blogDto, HttpServletRequest request) {

        //System.out.println("Controller Create");
        //System.out.println(new Gson().toJson(blogDto));
        storeUserDetails(request);
        Blog blog = blogService.create(blogDto);

        if (blog != null) {
            return ResponseEntity.ok(response(true, HttpStatus.CREATED, "New blog inserted successfully", blog));
        } else if (blog == null) {
            return ResponseEntity.ok(response(false, HttpStatus.BAD_REQUEST, "Something is wrong please contact", (Blog) null));
        }
        return ResponseEntity.ok(response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (Blog) null));
    }

    @GetMapping(path = "/list")
    public ResponseEntity<CommonResponseArray<Blog>> getAll(@ModelAttribute ListFilterDto listFilterDto, HttpServletRequest request, Pageable pageable) {

        //System.out.println(new Gson().toJson(listFilterDto));

        ListFilter listFilter = new ListFilter(listFilterDto);

        storeUserDetails(request);
        List<Blog> blogs = blogService.getAll(pageable, listFilter);

        MyPagenation myPagenation = managePagenation(request, blogService.getAllEntityCount(pageable, listFilter), pageable);

        if (!blogs.isEmpty()) {
            return ResponseEntity.ok(response(true, HttpStatus.FOUND, "All blog list", blogs, myPagenation));
        } else if (blogs.isEmpty()) {
            return ResponseEntity.ok(response(false, HttpStatus.NO_CONTENT, "No blog found", new ArrayList<Blog>(), myPagenation));
        } else {
            return ResponseEntity.ok(response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong", new ArrayList<Blog>(), myPagenation));
        }

    }


    @PostMapping(path = "/update")
    public CommonResponseSingle updateBlog(@ModelAttribute BlogDto blogDto, HttpServletRequest request) {

        System.out.println(new Gson().toJson(blogDto));
        //System.out.println("Controller Update");
        //System.out.println(new Gson().toJson(blogDto));
        storeUserDetails(request);

        Blog blog;

        if(blogDto.getId() == 0) {
            blog = blogService.create(blogDto);
        }else{
            blog = blogService.update(blogDto);
        }
        //System.out.println("PRODUCT UPDATE RESPONSE:");
        //System.out.println(new Gson().toJson(blog));

        if (blog != null) {
            return response(true, HttpStatus.OK, "New blog inserted successfully", blog);
        } else if (blog == null) {
            return response(false, HttpStatus.BAD_REQUEST, "Something is wrong with data", (Blog) null);
        }
        return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (Blog) null);
    }

    @DeleteMapping(path = "/{id}")
    public CommonResponseSingle<Blog> deleteBlog(@PathVariable("id") int id, HttpServletRequest request) {

        storeUserDetails(request);

        if (blogService.deleteById(id)) {
            return response(true, HttpStatus.OK, "Blog by id " + id + " is deleted", (Blog) null);
        } else{
            return response(false, HttpStatus.NOT_FOUND, "Blog not found or deleted", (Blog) null);
        }
    }

    @GetMapping(path = "/activate/{id}")
    public CommonResponseSingle<Blog> activeBlog(@PathVariable("id") int id, HttpServletRequest request) {

        storeUserDetails(request);

        if (blogService.activateById(id)) {
            return response(true, HttpStatus.OK, "Blog by id " + id + " is activated", (Blog) null);
        } else{
            return response(false, HttpStatus.NOT_FOUND, "Blog not found or deleted", (Blog) null);
        }
    }

    @GetMapping(path = "/deactivate/{id}")
    public CommonResponseSingle<Blog> deactiveBlog(@PathVariable("id") int id, HttpServletRequest request) {

        storeUserDetails(request);

        if (blogService.deactivateById(id)) {
            return response(true, HttpStatus.OK, "Blog by id " + id + " is deactivated", (Blog) null);
        } else{
            return response(false, HttpStatus.NOT_FOUND, "Blog not found or deleted", (Blog) null);
        }
    }

    @GetMapping(path = "/delete/{id}")
    public CommonResponseSingle<Blog> deleteBlogGet(@PathVariable("id") int id, HttpServletRequest request) {

        storeUserDetails(request);

        if (blogService.deleteById(id)) {
            return response(true, HttpStatus.OK, "Blog by id " + id + " is deleted", (Blog) null);
        } else{
            return response(false, HttpStatus.NOT_FOUND, "Blog not found or deleted", (Blog) null);
        }
    }

    @GetMapping(path = "/consumer/{id}")
    public CommonResponseSingle<Blog> getBlog(@PathVariable("id") int id, HttpServletRequest request) {

        storeUserDetails(request);

        Blog blog = blogService.getById(id);

        if (blog != null) {
            return response(true, HttpStatus.FOUND, "Blog by id: " + id, blog);
        } else if (blog == null) {
            return response(false, HttpStatus.NO_CONTENT, "Blog not found or deleted", (Blog) null);
        } else {
            return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong", (Blog) null);
        }
    }


    @GetMapping(path = "/by-product/consumer/{id}")
    public CommonResponseSingle<Blog> getBlogByProductId(@PathVariable("id") int id, HttpServletRequest request) {

        storeUserDetails(request);

        Blog blog = blogService.getBlogByProductId(id);

        if (blog != null) {
            return response(true, HttpStatus.FOUND, "Blog by id: " + id, blog);
        } else if (blog == null) {
            return response(false, HttpStatus.NO_CONTENT, "Blog not found or deleted", (Blog) null);
        } else {
            return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong", (Blog) null);
        }
    }

    @GetMapping(path = "/consumer/name/{name}")
    public CommonResponseSingle<Blog> getBlogByName(@PathVariable("name") String name, HttpServletRequest request) {

        storeUserDetails(request);

        Blog blog = blogService.getByName(name);

        if (blog != null) {
            return response(true, HttpStatus.FOUND, "Blog by name: " + name, blog);
        } else if (blog == null) {
            return response(false, HttpStatus.NO_CONTENT, "Blog not found or deleted", (Blog) null);
        } else {
            return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong", (Blog) null);
        }
    }

    @GetMapping(path = "/consumer/list/{flag}")
    public ResponseEntity<CommonResponseArray<Blog>> getAllConsumer(@PathVariable("flag") String flag, HttpServletRequest request, Pageable pageable) {

        storeUserDetails(request);

        String selectedParentMenu = flag;
        List<Blog> blogs = blogService.getAllBusiness(pageable, flag, selectedParentMenu);
        MyPagenation myPagenation = managePagenation(request, blogService.getAllBusinessEntityCount(pageable, flag), pageable);

        if (!blogs.isEmpty()) {
            return ResponseEntity.ok(responseBusiness(true, HttpStatus.FOUND, "All blog list", blogs, myPagenation));
        } else if (blogs.isEmpty()) {
            return ResponseEntity.ok(responseBusiness(true, HttpStatus.OK, "No blog found", new ArrayList<Blog>(), myPagenation));
        } else {
            return ResponseEntity.ok(responseBusiness(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong", new ArrayList<Blog>(), myPagenation));
        }

    }

    @GetMapping(path = "/consumer/search/{query}")
    public ResponseEntity<CommonResponseArray<Blog>> searchBlog(@PathVariable("query") String query, HttpServletRequest request, Pageable pageable) {

        storeUserDetails(request);

        List<Blog> blogs = blogService.searchBlog(pageable, query);
        MyPagenation myPagenation = managePagenation(request, blogService.getAllBusinessEntityCount(pageable, query), pageable);

        if (!blogs.isEmpty()) {
            return ResponseEntity.ok(responseBusiness(true, HttpStatus.FOUND, "All blog list", blogs, myPagenation));
        } else if (blogs.isEmpty()) {
            return ResponseEntity.ok(responseBusiness(true, HttpStatus.OK, "No blog found", new ArrayList<Blog>(), myPagenation));
        } else {
            return ResponseEntity.ok(responseBusiness(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong", new ArrayList<Blog>(), myPagenation));
        }

    }

    @Autowired
    private   CommonResponseArray commonResponseArray;
    protected  CommonResponseArray responseBusiness(boolean action, HttpStatus status, String message, List<Blog> data, MyPagenation pagenation){

        commonResponseArray.setAction(action);
        commonResponseArray.setStatus(status);
        commonResponseArray.setMessage(message);
        commonResponseArray.setData(data);
        commonResponseArray.setMyPagenation(pagenation);

        return commonResponseArray;
    }
}
