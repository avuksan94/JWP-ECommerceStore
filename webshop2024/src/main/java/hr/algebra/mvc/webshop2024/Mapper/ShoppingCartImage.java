package hr.algebra.mvc.webshop2024.Mapper;

import hr.algebra.dal.webshop2024dal.Entity.ShoppingCart;
import hr.algebra.mvc.webshop2024.DTO.DTOShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShoppingCartImage {
    @Mapping(target = "cartId", source = "cartId")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "sessionId", source = "sessionId")
    DTOShoppingCart ShoppingCartToDTOShoppingCart(ShoppingCart source);

    @Mapping(target = "cartId", source = "cartId")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "sessionId", source = "sessionId")
    ShoppingCart DTOShoppingCartToShoppingCart(DTOShoppingCart destination);
}
