package hr.algebra.mvc.webshop2024.Mapper;

import hr.algebra.dal.webshop2024dal.Entity.Product;
import hr.algebra.dal.webshop2024dal.Entity.ProductImage;
import hr.algebra.mvc.webshop2024.DTO.DTOProduct;
import hr.algebra.mvc.webshop2024.DTO.DTOProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {
    @Mapping(target = "productImageId", source = "productImageId")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "image", source = "image")
    DTOProductImage ProductImageToDTOProductImage(ProductImage source);

    @Mapping(target = "productImageId", source = "productImageId")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "image", source = "image")
    ProductImage DTOProductImageToProductImage(DTOProductImage destination);
}
