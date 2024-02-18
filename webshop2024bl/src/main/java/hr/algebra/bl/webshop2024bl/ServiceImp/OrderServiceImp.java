package hr.algebra.bl.webshop2024bl.ServiceImp;

import hr.algebra.bl.webshop2024bl.Service.OrderService;
import hr.algebra.dal.webshop2024dal.Entity.*;
import hr.algebra.dal.webshop2024dal.Repository.OrderItemRepository;
import hr.algebra.dal.webshop2024dal.Repository.OrderRepository;
import hr.algebra.dal.webshop2024dal.Repository.ProductRepository;
import hr.algebra.utils.CustomExceptions.CustomNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImp implements OrderService {
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final ProductRepository productRepo;

    public OrderServiceImp(OrderRepository orderRepo, OrderItemRepository orderItemRepo, ProductRepository productRepo) {
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
        this.productRepo = productRepo;
    }

    @Override
    public List<Order> findAll() {
        return orderRepo.findAll();
    }

    @Override
    public Order findById(long id) {
        Optional<Order> orderOptional = orderRepo.findById(id);

        if (orderOptional.isEmpty()){
            throw new CustomNotFoundException("Order id not found - " + id);
        }
        return orderOptional.get();
    }

    @Override
    public Optional<Order> findByUsername(String username) {
        return orderRepo.findByUsername(username);
    }

    @Override
    public List<Order> findAllByUsername(String username) {
        return orderRepo.findAllByUsername(username);
    }

    @Override
    @Transactional
    public Order findOrCreateOrder(String username) {
        Date now = new Date();
            return orderRepo.findByUsername(username)
                    .orElseGet(() -> orderRepo.save(new Order(username, now,new BigDecimal("0.00"),"N/A")));

    }

    @Override
    @Transactional
    public void addItemToOrder(String username, Long productId, Integer quantity, BigDecimal price) {
        Order order = findOrCreateOrder(username);
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        Optional<OrderItem> existingOrderItem = orderItemRepo.findByOrderAndProduct(order, product);

        if (existingOrderItem.isPresent()) {
            OrderItem orderItem = existingOrderItem.get();
            orderItem.setQuantity(orderItem.getQuantity() + quantity);
            orderItem.setPrice(orderItem.getPrice());
            orderItemRepo.save(orderItem);
        } else {
            OrderItem newOrderItem = new OrderItem(order, product, quantity,price);
            orderItemRepo.save(newOrderItem);
        }
    }

    @Override
    @Transactional
    public void removeItemFromOrder(String username, Long productId, Integer quantity, BigDecimal price) {
        Order order = findOrCreateOrder(username);
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        Optional<OrderItem> existingOrderItem = orderItemRepo.findByOrderAndProduct(order, product);

        if (existingOrderItem.isPresent()) {
            OrderItem orderItem = existingOrderItem.get();
            int newQuantity = orderItem.getQuantity() - quantity;

            if (newQuantity <= 0) {
                orderItemRepo.delete(orderItem);
            } else {
                orderItem.setQuantity(newQuantity);
                orderItemRepo.save(orderItem);
            }
        } else {
            throw new IllegalArgumentException("Cart item not found with Product ID: " + productId + " in cart");
        }
    }

    @Override
    @Transactional
    public void linkOrderToUser(String username) {
        Order order = orderRepo.findByUsername(username)
                .orElse(new Order());
        order.setUsername(username);
        orderRepo.save(order);
    }

    @Override
    @Transactional
    public Order save(Order obj) {
        return orderRepo.save(obj);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Optional<Order> checkIfExists = orderRepo.findById(id);
        if (checkIfExists.isEmpty()){
            throw new CustomNotFoundException("Order with that ID was not found: " + id);
        }
        orderRepo.deleteById(id);
    }
}
