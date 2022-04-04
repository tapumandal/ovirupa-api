package me.tapumandal.ovirupa.domain.inventory;

import me.tapumandal.ovirupa.domain.product.Product;
import me.tapumandal.ovirupa.domain.product.ProductRepository;
import me.tapumandal.ovirupa.domain.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by TapuMandal on 11/5/2021.
 * For any query ask online.tapu@gmail.com
 */


@Service
public class InventoryServiceImpl implements InventoryService{

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Override
    public void updateProductQuantityByOrderStatus(int productId, String status, int orderQuantity) {
//      ["Pending", "Processing", "Picked", "Delivered", "Cancel", "Delete"];
        System.out.println("STATUS:"+status );
        Product product = productRepository.getById(productId);
        if(status.equals("Pending")){
            System.out.println("ORDER STATUS: Pending");
            System.out.println("Calculation: "+product.getQuantity()+"-"+orderQuantity);
            product.setQuantity( product.getQuantity()-orderQuantity );
        }
        else if(status.equals("Cancel")){
            System.out.println("ORDER STATUS: Cancel");
            System.out.println("Calculation: "+product.getQuantity()+"+"+orderQuantity);
            product.setQuantity( product.getQuantity()+orderQuantity );
        }
        else if(status.equals("Delete")){
            System.out.println("ORDER STATUS: Delete");
            System.out.println("Calculation: "+product.getQuantity()+"+"+orderQuantity);
            product.setQuantity( product.getQuantity()+orderQuantity );
        }

        productRepository.update(product);
    }
}
