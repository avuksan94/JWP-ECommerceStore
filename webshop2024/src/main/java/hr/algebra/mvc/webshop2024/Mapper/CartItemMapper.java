package hr.algebra.mvc.webshop2024.Mapper;

import hr.algebra.dal.webshop2024dal.Entity.Authority;
import hr.algebra.dal.webshop2024dal.Entity.CartItem;
import hr.algebra.mvc.webshop2024.DTO.DTOAuthority;
import hr.algebra.mvc.webshop2024.DTO.DTOCartItem;
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
