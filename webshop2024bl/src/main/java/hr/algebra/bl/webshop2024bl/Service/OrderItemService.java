package hr.algebra.bl.webshop2024bl.Service;

import hr.algebra.dal.webshop2024dal.Entity.Order;
import hr.algebra.dal.webshop2024dal.Entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> findAll();
    OrderItem findById(long id);
    OrderItem save(OrderItem obj);
    void deleteById(long id);
}
