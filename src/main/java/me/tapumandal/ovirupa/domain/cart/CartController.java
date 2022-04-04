package me.tapumandal.ovirupa.domain.cart;

import com.google.gson.Gson;
import me.tapumandal.ovirupa.domain.payment_gateway.TransactionInitiator;
import me.tapumandal.ovirupa.domain.payment_gateway.TransactionResponseValidator;
import me.tapumandal.ovirupa.domain.payment_gateway.Utility.ParameterBuilder;
import me.tapumandal.ovirupa.domain.payment_gateway.parametermappings.SSLCommerzValidatorResponse;
import me.tapumandal.ovirupa.entity.ListFilter;
import me.tapumandal.ovirupa.entity.RandomJSON;
import me.tapumandal.ovirupa.repository.RandomJSONRepository;
import me.tapumandal.ovirupa.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.prefs.Preferences;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1")
public class CartController extends ControllerHelper<Cart> {

    @Autowired
    CartService cartService;

    @Autowired
    TransactionInitiator transactionInitiator;

    @Autowired
    RandomJSONRepository randomJSONRepository;

    @Value("${base.path.front}")
    String userBucketPath;

    private static Preferences preferences;

    @Autowired
    ApplicationPreferences applicationPreferences;


    @PostMapping(path = "/payment/request")
    public String onlinePaymentRequest(@RequestBody CartDto cartDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z").format(new Date(System.currentTimeMillis())));
        System.out.println("CART CONTROLLER: onlinePaymentRequest");
        System.out.println("CartDto: "+new Gson().toJson(cartDto));


        storeUserDetails(request);
        cartDto.setInvoiceNo(cartService.generateInvoice(cartDto));

        RandomJSON randomJSON = new RandomJSON();
        randomJSON.setJson(new Gson().toJson(cartDto));
        randomJSON = randomJSONRepository.save(randomJSON);

        System.out.println("RandomJSON RandomJSON");
        System.out.println(new Gson().toJson(randomJSON));

        applicationPreferences.saveCart(randomJSON.getId()+"");

        String transactionResponse = transactionInitiator.initTrnxnRequest(randomJSON.getJson());

        return new Gson().toJson(transactionResponse);
    }

    @PostMapping(path = "/cart/consumer/create")
    public CommonResponseSingle createCart(@RequestBody CartDto cartDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z").format(new Date(System.currentTimeMillis())));
        System.out.println("CART CONTROLLER: createCart");
        System.out.println("CartDto: "+new Gson().toJson(cartDto));

        storeUserDetails(request);

        cartDto.setInvoiceNo(cartService.generateInvoice(cartDto));

        System.out.println("CONTROLLER CARTDTO before create:"+new Gson().toJson(cartDto));

        Cart cart = cartService.create(cartDto);

        System.out.println("CONTROLLER CARTDTO after create:"+cart);

        if (cart != null) {
            return response(true, HttpStatus.CREATED, "New cart inserted successfully", cart);
        } else if (cart == null) {
            return response(false, HttpStatus.BAD_REQUEST, "Something is wrong please contact", (Cart) null);
        }
        return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (Cart) null);
    }

    @GetMapping(path = "/cart/{id}")
    public CommonResponseSingle<Cart> getCart(@PathVariable("id") int id, HttpServletRequest request) {
        System.out.println("CART CONTROLLER: getCart");

        storeUserDetails(request);

        Cart cart = cartService.getById(id);

        if (cart != null) {
            return response(true, HttpStatus.FOUND, "Cart by id: " + id, cart);
        } else if (cart == null) {
            return response(false, HttpStatus.NO_CONTENT, "Cart not found or deleted", (Cart) null);
        } else {
            return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong", (Cart) null);
        }
    }

    @GetMapping(path = "/cart/list")
    public CommonResponseArray<Cart> getAll(@ModelAttribute ListFilter listFilter, HttpServletRequest request, Pageable pageable) {
        System.out.println("CART CONTROLLER: getAll");

        storeUserDetails(request);
        //System.out.println("CART CONTROLLER:"+new Gson().toJson(listFilter));
        List<Cart> carts = cartService.getAll(pageable, listFilter);

        MyPagenation myPagenation = managePagenation(request, cartService.getAllEntityCount(pageable, listFilter), pageable);
        //System.out.println("CART:"+new Gson().toJson(carts));
        if (!carts.isEmpty()) {
            return response(true, HttpStatus.FOUND, "All cart list", carts, myPagenation);
        } else if (carts.isEmpty()) {
            return response(false, HttpStatus.NO_CONTENT, "No cart found", new ArrayList<Cart>(), myPagenation);
        } else {
            return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong", new ArrayList<Cart>(), myPagenation);
        }

    }

    @GetMapping(path = "/cart/consumer/list")
    public CommonResponseArray<Cart> getAllByUserID(HttpServletRequest request, Pageable pageable) {
        System.out.println("CART CONTROLLER: getAllByUserID");

        storeUserDetails(request);
        preferences = Preferences.userRoot().node(ApplicationPreferences.class.getName());
        int userID = Integer.parseInt(preferences.get("userId", "0"));

        List<Cart> carts = cartService.getAllByUserID(pageable);

        MyPagenation myPagenation = managePagenation(request, cartService.getAllByIDEntityCount(pageable, userID), pageable);
        //System.out.println("CART:"+new Gson().toJson(carts));
        if (!carts.isEmpty()) {
            return response(true, HttpStatus.FOUND, "All cart list", carts, myPagenation);
        } else if (carts.isEmpty()) {
            return response(false, HttpStatus.NO_CONTENT, "No cart found", new ArrayList<Cart>(), myPagenation);
        } else {
            return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong", new ArrayList<Cart>(), myPagenation);
        }
    }


    @PostMapping(path = "/cart/update")
    public CommonResponseSingle updateCart(@RequestBody CartDto cartDto, HttpServletRequest request) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z").format(new Date(System.currentTimeMillis())));
        System.out.println("CART CONTROLLER: updateCart");
        System.out.println("CartDto: "+new Gson().toJson(cartDto));

        storeUserDetails(request);

        Cart cart = cartService.update(cartDto);

        if (cart != null) {
            return response(true, HttpStatus.OK, "New cart inserted successfully", cart);
        } else if (cart == null) {
            return response(false, HttpStatus.BAD_REQUEST, "Something is wrong with data", (Cart) null);
        }
        return response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (Cart) null);
    }

    @DeleteMapping(path = "/cart/{id}")
    public CommonResponseSingle<Cart> deleteCart(@PathVariable("id") int id, HttpServletRequest request) {
        System.out.println("CART CONTROLLER: deleteCart");

        storeUserDetails(request);

        if (cartService.deleteById(id)) {
            return response(true, HttpStatus.OK, "Cart by id " + id + " is deleted", (Cart) null);
        } else{
            return response(false, HttpStatus.NOT_FOUND, "Cart not found or deleted", (Cart) null);
        }
    }

    @PostMapping(path = "/payment/confirmation")
    public void getAll(@ModelAttribute SSLCommerzValidatorResponse sslCommerzValidatorResponse, HttpServletResponse response, HttpServletRequest request, Pageable pageable) throws IOException {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z").format(new Date(System.currentTimeMillis())));
        System.out.println("CART CONTROLLER: getAll");
        System.out.println(new Gson().toJson(sslCommerzValidatorResponse));

        System.out.println("GET RANTOM JSON ID");
//        System.out.println(ApplicationPreferences.getCart());


        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
        response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");

        System.out.println("STATUS"+sslCommerzValidatorResponse.getStatus());
        if(sslCommerzValidatorResponse.getStatus().equals("VALID")) {
            System.out.println("IF");
            TransactionResponseValidator transactionResponseValidator = new TransactionResponseValidator();
            System.out.println("TransactionResponseValidator");
            ParameterBuilder parameterBuilder = new ParameterBuilder();
            System.out.println("ParameterBuilder");
            String projectUrl = "";
            try {
                System.out.println("TRYING");

//                transactionResponseValidator.receiveSuccessResponse(parameterBuilder.successResponseParamBuilder(sslCommerzValidatorResponse));

                RandomJSON randomJSON = new RandomJSON();
                randomJSON = randomJSONRepository.findById(Integer.parseInt(ApplicationPreferences.getCart())).get();

                System.out.println("RandomJSON RandomJSON");
                Cart cart = cartService.createCartOnlinePayment((CartDto) new Gson().fromJson(randomJSON.getJson(), CartDto.class), sslCommerzValidatorResponse);

                System.out.println("CART");

                projectUrl = userBucketPath+"cart/success";
            } catch (Exception e) {
                System.out.println("CATCH FAIELD");

                RandomJSON randomJSON = new RandomJSON();
                randomJSON = randomJSONRepository.findById(Integer.parseInt(ApplicationPreferences.getCart())).get();

                System.out.println("RandomJSON RandomJSON");
                Cart cart = cartService.create((CartDto) new Gson().fromJson(randomJSON.getJson(), CartDto.class));


                e.printStackTrace();
                projectUrl = userBucketPath+"cart/failed";
            }
            System.out.println("response.sendRedirect");
            response.sendRedirect(projectUrl);
        }else{
            System.out.println("ELSE FAILED");


            RandomJSON randomJSON = new RandomJSON();
            randomJSON = randomJSONRepository.findById(Integer.parseInt(ApplicationPreferences.getCart())).get();

            System.out.println("RandomJSON RandomJSON");
            Cart cart = cartService.create((CartDto) new Gson().fromJson(randomJSON.getJson(), CartDto.class));



            String projectUrl = userBucketPath+"cart/failed";
            response.sendRedirect(projectUrl);
        }
    }


    @GetMapping(path = "/online/payment/check")
    public void getAll() {

//        int randomIds[] = {2556,2557,2574,2592,2638,2639,2664,2665,2690,2728,2729,2730,2737,2775,2776,2799,2836,2849,};
//        int randomIds[] = {0};
//        System.out.println("Placing Order:");
//        for (int i:randomIds) {
//            RandomJSON randomJSON = new RandomJSON();
//            randomJSON = randomJSONRepository.findById(i).get();
//
//            Cart cart = cartService.createCartOnlinePayment((CartDto) new Gson().fromJson(randomJSON.getJson(), CartDto.class), null);
//            System.out.println(new Gson().toJson(cart));
//        }

    }
}