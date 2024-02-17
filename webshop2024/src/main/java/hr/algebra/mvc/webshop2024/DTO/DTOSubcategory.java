package hr.algebra.mvc.webshop2024.DTO;

import hr.algebra.dal.webshop2024dal.Entity.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class DTOSubcategory {
    private Long subcategoryId;
    @NotEmpty(message = "Subcategory name is required!")
    private String name;
    private DTOCategory category;

    public DTOSubcategory(String name, DTOCategory category) {
        this.name = name;
        this.category = category;
    }
}
