package hr.algebra.api.webshop2024api.ApiDTO;

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
