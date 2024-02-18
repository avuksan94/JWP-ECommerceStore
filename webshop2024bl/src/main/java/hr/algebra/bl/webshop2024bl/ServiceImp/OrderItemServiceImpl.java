package hr.algebra.bl.webshop2024bl.ServiceImp;

import hr.algebra.bl.webshop2024bl.Service.OrderItemService;
import hr.algebra.dal.webshop2024dal.Entity.Image;
import hr.algebra.dal.webshop2024dal.Entity.Order;
import hr.algebra.dal.webshop2024dal.Entity.OrderItem;
import hr.algebra.dal.webshop2024dal.Entity.Product;
import hr.algebra.dal.webshop2024dal.Repository.OrderItemRepository;
import hr.algebra.utils.CustomExceptions.CustomNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepo;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepo) {
        this.orderItemRepo = orderItemRepo;
    }

    @Override
    public List<OrderItem> findAll() {
        return orderItemRepo.findAll();
    }

    @Override
    public OrderItem findById(long id) {
        Optional<OrderItem> orderItemOptional = orderItemRepo.findById(id);

        if (orderItemOptional.isEmpty()){
            throw new CustomNotFoundException("Order Item id not found - " + id);
        }
        return orderItemOptional.get();
    }

    @Override
    public Optional<OrderItem> findByOrderAndProduct(Order order, Product product) {
        return orderItemRepo.findByOrderAndProduct(order,product);
    }

    @Override
    public List<OrderItem> findByOrder(Order order) {
        return orderItemRepo.findByOrder(order);
    }

    @Override
    @Transactional
    public OrderItem save(OrderItem obj) {
        return orderItemRepo.save(obj);
    }

    @Override
    public void deleteById(long id) {
        Optional<OrderItem> checkIfExists = orderItemRepo.findById(id);
        if (checkIfExists.isEmpty()){
            throw new CustomNotFoundException("Order Item with that ID was not found: " + id);
        }
        orderItemRepo.deleteById(id);
    }
}
