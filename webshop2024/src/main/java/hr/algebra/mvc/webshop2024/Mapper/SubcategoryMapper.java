package hr.algebra.mvc.webshop2024.Mapper;

import hr.algebra.dal.webshop2024dal.Entity.ShoppingCart;
import hr.algebra.dal.webshop2024dal.Entity.Subcategory;
import hr.algebra.mvc.webshop2024.DTO.DTOShoppingCart;
import hr.algebra.mvc.webshop2024.DTO.DTOSubcategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubcategoryMapper {
    @Mapping(target = "subcategoryId", source = "subcategoryId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "category", source = "category")
    DTOSubcategory SubcategoryToDTOSubcategory(Subcategory source);

    @Mapping(target = "subcategoryId", source = "subcategoryId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "category", source = "category")
    Subcategory DTOSubcategoryToSubcategory(DTOSubcategory destination);
}
