package hr.algebra.bl.webshop2024bl.ServiceImp;

import hr.algebra.bl.webshop2024bl.Service.OrderService;
import hr.algebra.dal.webshop2024dal.Entity.Order;
import hr.algebra.dal.webshop2024dal.Entity.OrderItem;
import hr.algebra.dal.webshop2024dal.Repository.OrderRepository;
import hr.algebra.utils.CustomExceptions.CustomNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImp implements OrderService {
    private final OrderRepository orderRepo;

    public OrderServiceImp(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
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
    public Order save(Order obj) {
        return orderRepo.save(obj);
    }

    @Override
    public void deleteById(long id) {
        Optional<Order> checkIfExists = orderRepo.findById(id);
        if (checkIfExists.isEmpty()){
            throw new CustomNotFoundException("Order with that ID was not found: " + id);
        }
        orderRepo.deleteById(id);
    }
}
