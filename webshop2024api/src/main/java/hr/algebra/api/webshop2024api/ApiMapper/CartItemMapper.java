package hr.algebra.api.webshop2024api.ApiMapper;

import hr.algebra.api.webshop2024api.ApiDTO.DTOCartItem;
import hr.algebra.dal.webshop2024dal.Entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mapping(target = "cartItemId", source = "cartItemId")
    @Mapping(target = "shoppingCart", source = "shoppingCart")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "quantity", source = "quantity")
    DTOCartItem CartItemToDTOCartItem(CartItem source);

    @Mapping(target = "cartItemId", source = "cartItemId")
    @Mapping(target = "shoppingCart", source = "shoppingCart")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "quantity", source = "quantity")
    CartItem DTOCartItemToCartItem(DTOCartItem destination);
}
