package hr.algebra.mvc.webshop2024.DTO;

import hr.algebra.dal.webshop2024dal.Entity.Image;
import hr.algebra.dal.webshop2024dal.Entity.Product;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class DTOProductImage {
    private Long productImageId;
    private DTOProduct product;
    private DTOImage image;

    public DTOProductImage(DTOProduct product, DTOImage image) {
        this.product = product;
        this.image = image;
    }
}
