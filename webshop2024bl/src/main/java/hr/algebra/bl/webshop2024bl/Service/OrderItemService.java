package hr.algebra.bl.webshop2024bl.Service;

import hr.algebra.dal.webshop2024dal.Entity.Order;
import hr.algebra.dal.webshop2024dal.Entity.OrderItem;
import hr.algebra.dal.webshop2024dal.Entity.Product;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {
    List<OrderItem> findAll();
    OrderItem findById(long id);
    Optional<OrderItem> findByOrderAndProduct(Order order, Product product);
    List<OrderItem> findByOrder(Order order);
    OrderItem save(OrderItem obj);
    void deleteById(long id);
}
