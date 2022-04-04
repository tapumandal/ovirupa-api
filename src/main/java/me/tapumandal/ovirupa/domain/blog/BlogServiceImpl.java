package me.tapumandal.ovirupa.domain.blog;

import me.tapumandal.ovirupa.domain.image.Image;
import me.tapumandal.ovirupa.domain.image.ImageService;
import me.tapumandal.ovirupa.entity.ImageModel;
import me.tapumandal.ovirupa.entity.ListFilter;
import me.tapumandal.ovirupa.util.ImageServiceUtil;
import me.tapumandal.ovirupa.util.MyPagenation;
import me.tapumandal.ovirupa.util.ResourceVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    ResourceVerifier resourceVerifier;

    @Autowired
    ImageServiceUtil imageServiceUtil;



    @Autowired
    ImageService imageService;

    String PRODUCT_FILE_PATH = "public/images/product/";

    private Blog blog;

    public BlogServiceImpl(){}

    public BlogServiceImpl(Blog blog){
        this.blog = blog;
    }

    @Override
    public Blog create(BlogDto blogDto) {

        PRODUCT_FILE_PATH = System.getProperty("user.dir")+PRODUCT_FILE_PATH;

        Blog b = new Blog(blogDto);

        Optional<Blog> blog;

//        try{
            int blogId = blogRepository.create(b);
            blog = Optional.ofNullable(blogRepository.getById(blogId));
//        }catch (Exception e){
//            return null;
//        }

        if(blog.isPresent()){
            return blog.get();
        }else{
            return null;
        }
    }


    @Override
    public Blog update(BlogDto blogDto) {


        Blog b = new Blog(blogDto);

        int proId = blogRepository.update(b);
        blog = getBlogByID(b.getId());

        return blog;
    }

    private Blog getBlogByID(int id) {
        Optional<Blog> blog;
        try{
            blog = Optional.ofNullable(blogRepository.getById(id));
        }catch (Exception e){
            return null;
        }
        if(blog.isPresent()){
            return blog.get();
        }else{
            return null;
        }
    }

    @Override
    public List<Blog> getAll(Pageable pageable, ListFilter listFilter) {
        Optional<List<Blog>> blogs = Optional.ofNullable(blogRepository.getAll(pageable, listFilter));

        if(blogs.isPresent()){
            return blogs.get();
        }else{
            return null;
        }
    }

    @Override
    public Blog getById(int id) {

        Optional<Blog> blog = Optional.ofNullable(blogRepository.getById(id));

        if(blog.isPresent()){
            return blog.get();
        }else{
            return null;
        }
    }


    @Override
    public Blog getBlogByProductId(int id) {

        Optional<Blog> blog = Optional.ofNullable(blogRepository.getBlogByProductId(id));

        if(blog.isPresent()){
            return blog.get();
        }else{
            return null;
        }
    }

    @Override
    public Blog getByName(String name) {


        name = name.replace("-", " ");

        Optional<Blog> blog;
        if(name.contains("'")){
            name = name.replace("'", "%");
            blog = Optional.ofNullable(blogRepository.getByNameWithSpecialChar(name));
        }else{
            blog = Optional.ofNullable(blogRepository.getByName(name));
        }

        if(blog.isPresent()){
            return blog.get();
        }else{
            return null;
        }
    }

    @Override
    public boolean deleteById(int id) {
        try {
            return blogRepository.delete(id);
        }catch (Exception ex){
            return false;
        }
    }

    @Override
    public boolean deactivateById(int id) {
        try {
            return blogRepository.deactivateById(id);
        }catch (Exception ex){
            return false;
        }
    }

    @Override
    public boolean activateById(int id) {
        try {
            return blogRepository.activateById(id);
        }catch (Exception ex){
            return false;
        }
    }


    @Override
    public Blog getByValue(String kye, String value) {
        return null;
    }

    @Override
    public List<Blog> getAllByValue(String kye, String value) {
        return null;
    }

    @Override
    public boolean isActive(int id) {
        Optional<Blog> blog = Optional.ofNullable(blogRepository.getById(id));
        if(blog.isPresent()){
            if(blog.get().isActive()){
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean isDeleted(int id) {
        return blog.isDeleted();
    }

    @Override
    public MyPagenation getPageable(Pageable pageable) {
        return blogRepository.getPageable(pageable);
    }

    @Override
    public int getAllEntityCount(Pageable pageable, ListFilter listFilter) {
        return blogRepository.getAllEntityCount(pageable, listFilter);
    }


    @Override
    public List<Blog> getAllBusiness(Pageable pageable, String flag, String selectedParentMenu) {

        //System.out.println("FLAG: "+flag);
        //System.out.println("PARENT: "+selectedParentMenu);
        flag = flag.replace("-", " ");
        flag = flag.replace(",", "%");
        flag = flag.replace("&", "%");
        flag = flag.replace("and", "%");
        flag = flag.replace("And", "%");
        flag = flag.replace("AND", "%");

        selectedParentMenu = selectedParentMenu.replace("-", " ");
        selectedParentMenu = selectedParentMenu.replace(",", "%");
        selectedParentMenu = selectedParentMenu.replace("&", "%");
        selectedParentMenu = selectedParentMenu.replace("and", "%");
        selectedParentMenu = selectedParentMenu.replace("And", "%");
        selectedParentMenu = selectedParentMenu.replace("AND", "%");

        selectedParentMenu = selectedParentMenu.trim();

        Optional<List<Blog>> blogs = Optional.ofNullable(blogRepository.getAllBusiness(pageable, flag, selectedParentMenu));

        if(blogs.isPresent()){
            return blogs.get();
        }else{
            return null;
        }

    }

    @Override
    public int getAllBusinessEntityCount(Pageable pageable, String flag) {
        return blogRepository.getAllBusinessEntityCount(pageable, flag);
    }

    @Override
    public List<Blog> searchBlog(Pageable pageable, String query) {
//        System.out.println("===========================================");
//        System.out.println("ORIGINAL: "+query);
//        String[] arrayQuery = query.split(" ");
//        List<String> queries = Arrays.asList(arrayQuery);
//        Collections.reverse(queries);
//        query = "";
//        for(int i =0 ; i<arrayQuery.length; i++){
//            query = query+arrayQuery[i]+" ";
//        }
//        for(int i =0 ; i<queries.size(); i++){
//            query = query+queries.get(i)+" ";
//        }
//        query.trim();
//
//        System.out.println("REVERSED: "+query);

        query = query.replace(",", "%");
        query = query.replace("&", "%");
        query = query.replace("and", "%");
        query = query.replace("And", "%");
        query = query.replace("AND", "%");
        query = query.replace(" ", "%");


        Optional<List<Blog>> blogs = Optional.ofNullable(blogRepository.searchBlog(pageable, query));

        if(blogs.isPresent()){
            return blogs.get();
        }else{
            return null;
        }
    }

    @Override
    public int searchBlogEntityCount(Pageable pageable, String query) {
        return blogRepository.searchBlogEntityCount(pageable, query);
    }


//    private String storeThumbnail(MultipartFile thumbnail) {
//        ImageModel imageModel = imageServiceUtil.createThumbnail(thumbnail);
//        return imageModel.getUrl();
//    }

    public List<Image> storeBlogImages(MultipartFile[] images) {

        List<ImageModel> imageModels = imageServiceUtil.store(images);

        List<Image> blogImages = new ArrayList<>();

        for (ImageModel tmp: imageModels) {
            Image image = new Image();
            image.setName(tmp.getName());
            image.setUrl(tmp.getUrl());
            image.setSize(tmp.getSize());

            blogImages.add(image);
        }

        return blogImages;
    }
}
