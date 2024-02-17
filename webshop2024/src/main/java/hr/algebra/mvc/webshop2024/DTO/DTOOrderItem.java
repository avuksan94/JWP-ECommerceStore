package hr.algebra.mvc.webshop2024.DTO;

import hr.algebra.dal.webshop2024dal.Entity.Order;
import hr.algebra.dal.webshop2024dal.Entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class DTOOrderItem {
    private Long orderItemId;
    private DTOOrder order;
    private DTOProduct product;
    private Integer quantity;
    private BigDecimal price;

    public DTOOrderItem(DTOOrder order, DTOProduct product, Integer quantity, BigDecimal price) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }
}
