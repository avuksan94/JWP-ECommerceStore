package hr.algebra.api.webshop2024api.ApiDTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class DTOCategory {
    private Long categoryId;
    @NotEmpty(message = "Category name is required")
    private String name;

    public DTOCategory(String name) {
        this.name = name;
    }

    public DTOCategory(Long categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }
}
