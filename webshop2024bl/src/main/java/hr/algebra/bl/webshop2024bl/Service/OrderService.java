package hr.algebra.bl.webshop2024bl.Service;

import hr.algebra.dal.webshop2024dal.Entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAll();
    Order findById(long id);
    Order save(Order obj);
    void deleteById(long id);
}
