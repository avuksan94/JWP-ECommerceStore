package hr.algebra.api.webshop2024api.ApiMapper;

import hr.algebra.api.webshop2024api.ApiDTO.DTOShoppingCart;
import hr.algebra.dal.webshop2024dal.Entity.ShoppingCart;
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
