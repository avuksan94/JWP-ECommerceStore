package hr.algebra.bl.webshop2024bl.Service;

import hr.algebra.dal.webshop2024dal.Entity.Order;
import hr.algebra.dal.webshop2024dal.Entity.ShoppingCart;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> findAll();
    Order findById(long id);
    Optional<Order> findByUsername(String username);
    List<Order> findAllByUsername(String username);
    List<Order> findByUsernameAndPurchaseDateBetween(String username, LocalDateTime startDate, LocalDateTime endDate);
    Order findOrCreateOrder(String username);
    void addItemToOrder(String username, Long productId, Integer quantity, BigDecimal price);
    void removeItemFromOrder(String username, Long productId, Integer quantity, BigDecimal price);
    void linkOrderToUser(String username);
    Order save(Order obj);
    void deleteById(long id);
}
