package hr.algebra.mvc.webshop2024.DTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class DTOImage {
    private Long imageId;
    @NotEmpty(message = "Image URL is required!")
    private String imageUrl;

    public DTOImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
