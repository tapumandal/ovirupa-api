package me.tapumandal.ovirupa.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import me.tapumandal.ovirupa.domain.product.ProductService;
import me.tapumandal.ovirupa.service.UserService;
import me.tapumandal.ovirupa.service.WarehouseService;


@Component
public class ResourceVerifier {

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    WarehouseService warehouseService;


    public boolean checkProduct(int id) {
        if(!productService.isActive(id)){
            return false;
        }
        return true;
    }

    public boolean checkUser(int id) {
        if(!userService.isActive(id)){
            return false;
        }
        return true;
    }

    public boolean checkWarehouse(int id) {
        if(!warehouseService.isActive(id)){
            return false;
        }
        return true;
    }

}


