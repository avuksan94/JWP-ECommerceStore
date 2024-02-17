package hr.algebra.mvc.webshop2024.DTO;

import hr.algebra.dal.webshop2024dal.Entity.Subcategory;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class DTOProduct {
    private Long productId;
    private String name;
    private String description;
    private BigDecimal price;
    private DTOSubcategory subcategory;

    public DTOProduct(String name, String description, BigDecimal price, DTOSubcategory subcategory) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.subcategory = subcategory;
    }
}
