package me.tapumandal.ovirupa.domain.product;

import me.tapumandal.ovirupa.service.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService extends Service<ProductDto, Product> {

    public List<ProductBusiness> getAllBusiness(Pageable pageable, String category, String selectedParentMenu);
    public int getAllBusinessEntityCount(Pageable pageable, String flag);

    List<ProductBusiness> searchProduct(Pageable pageable, String query);
    public int searchProductEntityCount(Pageable pageable, String query);

    public Product getByName(String name);

    boolean deactivateById(int id);

    boolean activateById(int id);
}
