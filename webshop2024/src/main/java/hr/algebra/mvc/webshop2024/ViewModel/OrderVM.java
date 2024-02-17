package hr.algebra.mvc.webshop2024.ViewModel;

import hr.algebra.dal.webshop2024dal.Entity.Order;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class OrderVM {
    private Order order;
    private List<OrderItemVM> orderItems;

    public OrderVM(List<OrderItemVM> orderItems) {
        this.orderItems = orderItems;
    }

    public OrderVM(Order order, List<OrderItemVM> orderItems) {
        this.order = order;
        this.orderItems = orderItems;
    }
}