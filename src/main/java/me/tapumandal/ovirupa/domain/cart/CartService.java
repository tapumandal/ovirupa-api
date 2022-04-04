package me.tapumandal.ovirupa.domain.cart;

import me.tapumandal.ovirupa.domain.payment_gateway.parametermappings.SSLCommerzValidatorResponse;
import me.tapumandal.ovirupa.service.Service;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface CartService extends Service<CartDto, Cart> {
    public List<Cart> getAllByUserID(Pageable pageable);
    public int getAllByIDEntityCount(Pageable pageable, int id);

    Cart createCartOnlinePayment(CartDto cartDto, SSLCommerzValidatorResponse sslCommerzInitResponse);

    String generateInvoice(CartDto cartDto);

    public List<Cart> getAllCartByDateRange(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
