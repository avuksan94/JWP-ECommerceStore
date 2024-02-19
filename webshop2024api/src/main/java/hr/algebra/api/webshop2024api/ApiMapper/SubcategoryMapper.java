package hr.algebra.api.webshop2024api.ApiMapper;

import hr.algebra.api.webshop2024api.ApiDTO.DTOSubcategory;
import hr.algebra.dal.webshop2024dal.Entity.Subcategory;
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
