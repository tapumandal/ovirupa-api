package me.tapumandal.ovirupa.domain.cart;

import org.springframework.data.domain.Pageable;
import me.tapumandal.ovirupa.repository.Repository;

import java.time.LocalDateTime;
import java.util.List;

public interface CartRepository extends Repository<Cart> {

    public Cart getCartFirstTime(int cartId);
    public List<Cart> getAllByUserID(int userID, Pageable pageable);
    public int getAllByIDEntityCount(Pageable pageable, int id);

    public List<Cart> getAllCartByDateRange(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
