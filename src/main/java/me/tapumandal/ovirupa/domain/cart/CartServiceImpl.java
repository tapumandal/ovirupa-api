package me.tapumandal.ovirupa.domain.cart;

import me.tapumandal.ovirupa.domain.inventory.InventoryService;
import me.tapumandal.ovirupa.domain.payment_gateway.parametermappings.SSLCommerzValidatorResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import me.tapumandal.ovirupa.domain.business_settings.BusinessSettingsRepository;
import me.tapumandal.ovirupa.domain.product.ProductRepository;
import me.tapumandal.ovirupa.domain.promotions.PromotionRepository;
import me.tapumandal.ovirupa.domain.ref_code.RefCodeRewardRepository;
import me.tapumandal.ovirupa.entity.ListFilter;
import me.tapumandal.ovirupa.entity.Notification;
import me.tapumandal.ovirupa.repository.UserRepository;
import me.tapumandal.ovirupa.util.ApplicationPreferences;
import me.tapumandal.ovirupa.util.MyPagenation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.prefs.Preferences;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PromotionRepository promotionRepository;

    @Autowired
    RefCodeRewardRepository refCodeRewardRepository;

    @Autowired
    BusinessSettingsRepository businessSettingsRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    InventoryService inventoryService;

    private Notification notifications = new Notification(1);


    private static Preferences preferences;

    private Cart cart;

    public CartServiceImpl(){}

    public CartServiceImpl(Cart cart){
        this.cart = cart;
    }

    @Override
    public Cart create(CartDto cartDto) {
        System.out.println("SERVICE: 1");
        Cart entity = new Cart(cartDto);
        System.out.println("SERVICE: 2");
        entity.setPaymentMethod("CASH_ON_DELIVERY");
        entity.setUserId(ApplicationPreferences.getUser().getId());
        System.out.println("SERVICE: 3");
        Optional<Cart> cart = null;
//        try{
            System.out.println("SERVICE: 4");
            int cartId = cartRepository.create(entity);
            System.out.println("SERVICE: 5");
            System.out.println("AFTER ORDER CREATE");
            for (CartProduct cartProduct: entity.productList) {
                System.out.println("SERVICE: 6 :"+cartProduct.getId());
                inventoryService.updateProductQuantityByOrderStatus(cartProduct.getProductId(), "Pending", cartProduct.getOrderQuantity());
            }

            System.out.println("SERVICE: 7");

            cart = Optional.ofNullable(cartRepository.getCartFirstTime(cartId));

            System.out.println("SERVICE: 8");

            if(cart.get().getSelectedDiscountName().equals("RefCode") || cart.get().getSelectedDiscountName().equals("PromoCode") || cart.get().getSelectedDiscountName().equals("RefCodeReward")){
                //System.out.println("IT'S "+cart.get().getSelectedDiscountName());
                if(cart.get().getSelectedDiscountName().equals("RefCodeReward")){
                    useRefCodeReward();
                }else{
                    useAppliedPromoCode();
                }
            }else{
                //System.out.println("MY CART: NO A PROMO/REWARD");
            }

            int userId = ApplicationPreferences.getUser().getId();

//        }catch (Exception e){
//            System.out.println(e);
//        }

//        template.convertAndSend("/topic/notification", notifications);

        if(cart.isPresent()){
            return cart.get();
        }else{
            return null;
        }
    }


    @Override
    public Cart createCartOnlinePayment(CartDto cartDto, SSLCommerzValidatorResponse sslCommerzInitResponse){

        Cart entity = new Cart(cartDto);

        entity.setPaymentMethod("ONLINE");
        entity.setUserId(ApplicationPreferences.getUser().getId());
//        entity.setInvoiceNo(generateInvoice(entity));

        Optional<Cart> cart;
        try{

            int cartId = cartRepository.create(entity);

            cart = Optional.ofNullable(cartRepository.getById(cartId));

            int userId = ApplicationPreferences.getUser().getId();

        }catch (Exception e){
            return null;
        }

//        template.convertAndSend("/topic/notification", notifications);

        if(cart.isPresent()){
            return cart.get();
        }else{
            return null;
        }
    }

    private boolean useAppliedPromoCode() {

//        int userId = ApplicationPreferences.getUser().getId();
//        User user = userRepository.getById(userId);
//        if(user.getUserPromo() == null){
//            return false;
//        }
//
//        if(user.getUserPromo().getType().equals("RefCode")) {
//            user.getUserPromo().setActive(false);
//            user.getRefCodeReward().setFirstOrder(true);
//            RefCodeReward refCodeReward = refCodeRewardRepository.getRefCodeByCode(user.getUserPromo().getCode());
//            //System.out.println("ReferredCodeReward B:"+ new Gson().toJson(refCodeReward));
//
//            BusinessSettings businessSettings = businessSettingsRepository.getById(0);
//
//            refCodeReward.setTotalCredit(refCodeReward.getTotalCredit()+businessSettings.getRefCodeReturnReward());
//            refCodeReward.setNumberOfReward(refCodeReward.getNumberOfReward()+1);
//            refCodeReward.setRewardAmountList((refCodeReward.getRewardAmountList()+","+businessSettings.getRefCodeReturnReward()).trim());
//
//            refCodeRewardRepository.update(refCodeReward);
//            //System.out.println("ReferredCodeReward A:"+ new Gson().toJson(refCodeReward));
//            return true;
//
//        }else if(user.getUserPromo().getType().equals("PromoCode")) {
//            Promotion promotion = promotionRepository.applyPromotion(user.getUserPromo().getCode());
//            promotion.setNumberOfApply(promotion.getNumberOfApply()+1);
//            promotionRepository.update(promotion);
//
//            user.getUserPromo().setActive(false);
//            userRepository.update(user);
//            return true;
//        }
        return false;
    }

    private boolean useRefCodeReward() {

//        int userId = ApplicationPreferences.getUser().getId();
//        User user = userRepository.getById(userId);
//
//        RefCodeReward refCodeReward = user.getRefCodeReward();
//
//        BusinessSettings businessSettings = businessSettingsRepository.getById(0);
//
//        int amountCutt = (refCodeReward.getTotalCredit()-refCodeReward.getTotalAmountClaimed()) - businessSettings.getRefCodeCreditAmount();
//        if( amountCutt < 0 ){
//            amountCutt = (refCodeReward.getTotalCredit()-refCodeReward.getTotalAmountClaimed());
//        }else{
//            amountCutt = businessSettings.getRefCodeCreditAmount();
//        }
//        refCodeReward.setNumberOfRewardClaim(refCodeReward.getNumberOfRewardClaim()+1);
//        refCodeReward.setTotalAmountClaimed(refCodeReward.getTotalAmountClaimed()+amountCutt);
//
//        refCodeRewardRepository.update(refCodeReward);
        return true;
    }

    @Override
    public String generateInvoice(CartDto cartDto) {

        Cart entity = new Cart(cartDto);

        String invoiceCode = "";

        preferences = Preferences.userRoot().node(ApplicationPreferences.class.getName());
        int userID = Integer.parseInt(preferences.get("userId", "0"));
        int totalOrder = cartRepository.getAllByIDEntityCount(null, userID);
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM");
        String[] dateMonth = localDateTime.format(formatter).split("-");

        int day = Integer.valueOf(dateMonth[0]);
        int month = Integer.valueOf(dateMonth[1]);


        totalOrder = userID*(totalOrder+1);

        String endCode = "";
        if(totalOrder<10){
            endCode = "000"+String.valueOf(totalOrder);
        }else if(totalOrder<100){
            endCode = "00"+String.valueOf(totalOrder);
        }else if(totalOrder<1000){
            endCode = "0"+String.valueOf(totalOrder);
        }else if(totalOrder<10000){
            endCode = String.valueOf(totalOrder);
        }else{
            endCode = "S"+String.valueOf(totalOrder);
        }

//        invoiceCode = areaCode.toUpperCase()+day+month+endCode;
        invoiceCode = day+month+endCode;
        //System.out.println("InvoiceCode: "+invoiceCode);

        return invoiceCode;
    }

    @Override
    public Cart update(CartDto cartDto) {

        Optional<Cart> cart;
//        try{
            Cart tmpCart = cartRepository.getById(cartDto.getId());

            for (CartProduct cartProduct: tmpCart.productList) {
                if(cartDto.getStatus().equals("Delete") && tmpCart.getStatus().equals("Cancel")) {
                }else {
                    inventoryService.updateProductQuantityByOrderStatus(cartProduct.getProductId(), cartDto.getStatus(), cartProduct.getOrderQuantity());
                }
            }

            tmpCart.setStatus(cartDto.getStatus());
            int proId = cartRepository.update(tmpCart);
            cart = Optional.ofNullable(cartRepository.getById(proId));


//        }catch (Exception e){
//            return null;
//        }

        if(cart.isPresent()){
            return cart.get();
        }else{
            return null;
        }

    }

    @Override
    public List<Cart> getAll(Pageable pageable, ListFilter listFilter) {

        Optional<List<Cart>> carts = Optional.ofNullable(cartRepository.getAll(pageable, listFilter));

        List<Cart> cartList = carts.get();

        int i = 0;
        for (Cart cartTmp: carts.get()) {
            int j = 0;

            for (CartProduct cartProduct: cartTmp.getProductList()) {

                cartList.get(i).getProductList().get(j).setBuyingPricePerUnitAssigned(productRepository.getByIdAny(cartProduct.getProductId()).getBuyingPricePerUnit());

                j++;
            }
            i++;
        }

        if(carts.isPresent()){
            return carts.get();
        }else{
            return null;
        }
    }

    @Override
    public Cart getById(int id) {

        Optional<Cart> cart = Optional.ofNullable(cartRepository.getById(id));

        if(cart.isPresent()){
            return cart.get();
        }else{
            return null;
        }
    }

    @Override
    public boolean deleteById(int id) {
//        try {
            return cartRepository.delete(id);
//        }catch (Exception ex){
//            return false;
//        }
    }

    @Override
    public Cart getByValue(String kye, String value) {
        return null;
    }

    @Override
    public List<Cart> getAllByValue(String kye, String value) {
        return null;
    }

    @Override
    public boolean isActive(int id) {
        Optional<Cart> cart = Optional.ofNullable(cartRepository.getById(id));
        if(cart.isPresent()){
            if(cart.get().isActive()){
                return true;
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean isDeleted(int id) {
        return false;
    }

    @Override
    public MyPagenation getPageable(Pageable pageable) {
        return cartRepository.getPageable(pageable);
    }

    @Override
    public int getAllEntityCount(Pageable pageable, ListFilter listFilter) {
        return cartRepository.getAllEntityCount(pageable, listFilter);
    }

    @Override
    public List<Cart> getAllByUserID(Pageable pageable) {

        preferences = Preferences.userRoot().node(ApplicationPreferences.class.getName());
        int phoneNumber = Integer.parseInt(preferences.get("phoneNumber", "0"));

        Optional<List<Cart>> carts = Optional.ofNullable(cartRepository.getAllByUserID(phoneNumber, pageable));

        if(carts.isPresent()){
            return carts.get();
        }else{
            return null;
        }
    }

    @Override
    public int getAllByIDEntityCount(Pageable pageable, int id) {
        return cartRepository.getAllByIDEntityCount(pageable, id);
    }


    @Override
    public List<Cart> getAllCartByDateRange(LocalDateTime start, LocalDateTime end, Pageable pageable){

        return cartRepository.getAllCartByDateRange(start, end, pageable);
    }
}
