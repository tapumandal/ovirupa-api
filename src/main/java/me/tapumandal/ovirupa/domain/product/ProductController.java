package me.tapumandal.ovirupa.domain.product;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/product")
public class ProductController extends ControllerHelper<Product> {

    @Autowired
    ProductService productService;


    @PostMapping(path = "/create")
    public ResponseEntity<CommonResponseSingle> createProduct(@ModelAttribute ProductDto productDto, HttpServletRequest request) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z").format(new Date(System.currentTimeMillis())));

        System.out.println("PRODUCT CONTROLLER: createProduct");
        System.out.println("ProductDto: "+new Gson().toJson(productDto));
        //System.out.println("Controller Create");
        //System.out.println(new Gson().toJson(productDto));
        storeUserDetails(request);
        productDto.setImageRealPath(request.getServletContext().getRealPath(""));
        Product product = productService.create(productDto);

        if (product != null) {
            return ResponseEntity.ok(response(true, HttpStatus.CREATED, "New product inserted successfully", product));
        } else if (product == null) {
            return ResponseEntity.ok(response(false, HttpStatus.BAD_REQUEST, "Something is wrong please contact", (Product) null));
        }
        return ResponseEntity.ok(response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (Product) null));
    }

    @GetMapping(path = "/list")
    public ResponseEntity<CommonResponseArray<Product>> getAll(@ModelAttribute ListFilterDto listFilterDto, HttpServletRequest request, Pageable pageable) {
        System.out.println("PRODUCT CONTROLLER: getAll");

        //System.out.println(new Gson().toJson(listFilterDto));

        ListFilter listFilter = new ListFilter(listFilterDto);

        storeUserDetails(request);
        List<Product> products = productService.getAll(pageable, listFilter);

        MyPagenation myPagenation = managePagenation(request, productService.getAllEntityCount(pageable, listFilter), pageable);

        if (!products.isEmpty()) {
            return ResponseEntity.ok(response(true, HttpStatus.FOUND, "All product list", products, myPagenation));
        } else if (products.isEmpty()) {
            return ResponseEntity.ok(response(false, HttpStatus.NO_CONTENT, "No product found", new ArrayList<Product>(), myPagenation));
        } else {
            return ResponseEntity.ok(response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong", new ArrayList<Product>(), myPagenation));
        }

    }


    @PostMapping(path = "/update")
    public CommonResponseSingle updateProduct(@ModelAttribute ProductDto productDto, HttpServletRequest request) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z").format(new Date(System.currentTimeMillis())));
        System.out.println("PRODUCT CONTROLLER: updateProduct");
        System.out.println("ProductDto: "+new Gson().toJson(productDto));

        //System.out.println("Controller Update");
        //System.out.println(new Gson().toJson(productDto));
        storeUserDetails(request);

        Product product = productService.update(productDto);
        //System.out.println("PRODUCT UPDATE RESPONSE:");
        //System.out.println(new Gson().toJson(product));

        if (product != null) {
            return response(true, HttpStatus.OK, "New product inserted successfully", product);
        } else if (product == null) {
            return response(false, HttpStatus.BAD_REQUEST, "Something is wrong with data", (Product) null);
        }
        return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (Product) null);
    }

    @DeleteMapping(path = "/{id}")
    public CommonResponseSingle<Product> deleteProduct(@PathVariable("id") int id, HttpServletRequest request) {
        System.out.println("PRODUCT CONTROLLER: deleteProduct");

        storeUserDetails(request);

        if (productService.deleteById(id)) {
            return response(true, HttpStatus.OK, "Product by id " + id + " is deleted", (Product) null);
        } else{
            return response(false, HttpStatus.NOT_FOUND, "Product not found or deleted", (Product) null);
        }
    }

    @GetMapping(path = "/activate/{id}")
    public CommonResponseSingle<Product> activeProduct(@PathVariable("id") int id, HttpServletRequest request) {
        System.out.println("PRODUCT CONTROLLER: activeProduct");

        storeUserDetails(request);

        if (productService.activateById(id)) {
            return response(true, HttpStatus.OK, "Product by id " + id + " is activated", (Product) null);
        } else{
            return response(false, HttpStatus.NOT_FOUND, "Product not found or deleted", (Product) null);
        }
    }

    @GetMapping(path = "/deactivate/{id}")
    public CommonResponseSingle<Product> deactiveProduct(@PathVariable("id") int id, HttpServletRequest request) {
        System.out.println("PRODUCT CONTROLLER: deactiveProduct");

        storeUserDetails(request);

        if (productService.deactivateById(id)) {
            return response(true, HttpStatus.OK, "Product by id " + id + " is deactivated", (Product) null);
        } else{
            return response(false, HttpStatus.NOT_FOUND, "Product not found or deleted", (Product) null);
        }
    }

    @GetMapping(path = "/delete/{id}")
    public CommonResponseSingle<Product> deleteProductGet(@PathVariable("id") int id, HttpServletRequest request) {
        System.out.println("PRODUCT CONTROLLER: deleteProductGet");

        storeUserDetails(request);

        if (productService.deleteById(id)) {
            return response(true, HttpStatus.OK, "Product by id " + id + " is deleted", (Product) null);
        } else{
            return response(false, HttpStatus.NOT_FOUND, "Product not found or deleted", (Product) null);
        }
    }

    @GetMapping(path = "/consumer/{id}")
    public CommonResponseSingle<Product> getProduct(@PathVariable("id") int id, HttpServletRequest request) {
        System.out.println("PRODUCT CONTROLLER: getProduct");

        storeUserDetails(request);

        Product product = productService.getById(id);

        if (product != null) {
            return response(true, HttpStatus.FOUND, "Product by id: " + id, product);
        } else if (product == null) {
            return response(false, HttpStatus.NO_CONTENT, "Product not found or deleted", (Product) null);
        } else {
            return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong", (Product) null);
        }
    }

    @GetMapping(path = "/consumer/name/{name}")
    public CommonResponseSingle<Product> getProductByName(@PathVariable("name") String name, HttpServletRequest request) {
        System.out.println("PRODUCT CONTROLLER: getProductByName");

        storeUserDetails(request);

        Product product = productService.getByName(name);

        if (product != null) {
            return response(true, HttpStatus.FOUND, "Product by name: " + name, product);
        } else if (product == null) {
            return response(false, HttpStatus.NO_CONTENT, "Product not found or deleted", (Product) null);
        } else {
            return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong", (Product) null);
        }
    }

    @GetMapping(path = "/consumer/list/{flag}/{selectedParentMenu}")
    public ResponseEntity<CommonResponseArray<ProductBusiness>> getAllConsumer(@PathVariable("flag") String flag, @PathVariable("selectedParentMenu") String xselectedParentMenu, HttpServletRequest request, Pageable pageable) {
        System.out.println("PRODUCT CONTROLLER: getAllConsumer");

        storeUserDetails(request);

        String selectedParentMenu = flag;
        List<ProductBusiness> products = productService.getAllBusiness(pageable, flag, selectedParentMenu);
        MyPagenation myPagenation = managePagenation(request, productService.getAllBusinessEntityCount(pageable, flag), pageable);

        if (!products.isEmpty()) {
            return ResponseEntity.ok(responseBusiness(true, HttpStatus.FOUND, "All product list", products, myPagenation));
        } else if (products.isEmpty()) {
            return ResponseEntity.ok(responseBusiness(true, HttpStatus.OK, "No product found", new ArrayList<ProductBusiness>(), myPagenation));
        } else {
            return ResponseEntity.ok(responseBusiness(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong", new ArrayList<ProductBusiness>(), myPagenation));
        }

    }

    @GetMapping(path = "/consumer/search/{query}")
    public ResponseEntity<CommonResponseArray<ProductBusiness>> searchProduct(@PathVariable("query") String query, HttpServletRequest request, Pageable pageable) {
        System.out.println("PRODUCT CONTROLLER: searchProduct");

        storeUserDetails(request);

        List<ProductBusiness> products = productService.searchProduct(pageable, query);
        MyPagenation myPagenation = managePagenation(request, productService.getAllBusinessEntityCount(pageable, query), pageable);

        if (!products.isEmpty()) {
            return ResponseEntity.ok(responseBusiness(true, HttpStatus.FOUND, "All product list", products, myPagenation));
        } else if (products.isEmpty()) {
            return ResponseEntity.ok(responseBusiness(true, HttpStatus.OK, "No product found", new ArrayList<ProductBusiness>(), myPagenation));
        } else {
            return ResponseEntity.ok(responseBusiness(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong", new ArrayList<ProductBusiness>(), myPagenation));
        }

    }

    @Autowired
    private   CommonResponseArray commonResponseArray;
    protected  CommonResponseArray responseBusiness(boolean action, HttpStatus status, String message, List<ProductBusiness> data, MyPagenation pagenation){

        commonResponseArray.setAction(action);
        commonResponseArray.setStatus(status);
        commonResponseArray.setMessage(message);
        commonResponseArray.setData(data);
        commonResponseArray.setMyPagenation(pagenation);

        return commonResponseArray;
    }
}
