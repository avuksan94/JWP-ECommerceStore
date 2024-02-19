package hr.algebra.api.webshop2024api.ApiDTO;

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
