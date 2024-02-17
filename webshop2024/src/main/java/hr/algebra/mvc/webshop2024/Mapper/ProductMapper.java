package hr.algebra.mvc.webshop2024.Mapper;

import hr.algebra.dal.webshop2024dal.Entity.Order;
import hr.algebra.dal.webshop2024dal.Entity.Product;
import hr.algebra.mvc.webshop2024.DTO.DTOOrder;
import hr.algebra.mvc.webshop2024.DTO.DTOProduct;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "subcategory", source = "subcategory")
    DTOProduct ProductToDTOProduct(Product source);

    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "subcategory", source = "subcategory")
    Product DTOProductToProduct(DTOProduct destination);
}
