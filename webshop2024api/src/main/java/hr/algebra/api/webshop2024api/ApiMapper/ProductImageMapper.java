package hr.algebra.api.webshop2024api.ApiMapper;

import hr.algebra.api.webshop2024api.ApiDTO.DTOProductImage;
import hr.algebra.dal.webshop2024dal.Entity.ProductImage;
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
