package hr.algebra.mvc.webshop2024.Mapper;

import hr.algebra.dal.webshop2024dal.Entity.Category;
import hr.algebra.mvc.webshop2024.DTO.DTOCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "categoryId", source = "categoryId")
    @Mapping(target = "name", source = "name")
    DTOCategory CategoryItemToDTOCategory(Category source);

    @Mapping(target = "categoryId", source = "categoryId")
    @Mapping(target = "name", source = "name")
    Category DTOCategoryToCategory(DTOCategory destination);
}
