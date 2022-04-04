package me.tapumandal.ovirupa.domain.inventory;

/**
 * Created by TapuMandal on 11/5/2021.
 * For any query ask online.tapu@gmail.com
 */

public interface InventoryService {

    public void updateProductQuantityByOrderStatus(int productId, String status, int orderQuantity);
}
