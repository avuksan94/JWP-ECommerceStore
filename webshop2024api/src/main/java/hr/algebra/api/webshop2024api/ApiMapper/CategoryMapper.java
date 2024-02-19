package hr.algebra.api.webshop2024api.ApiMapper;

import hr.algebra.api.webshop2024api.ApiDTO.DTOCategory;
import hr.algebra.dal.webshop2024dal.Entity.Category;
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
