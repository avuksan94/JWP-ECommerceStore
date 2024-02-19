package hr.algebra.api.webshop2024api.ApiMapper;

import hr.algebra.api.webshop2024api.ApiDTO.DTOOrder;
import hr.algebra.dal.webshop2024dal.Entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "purchaseDate", source = "purchaseDate")
    @Mapping(target = "totalAmount", source = "totalAmount")
    @Mapping(target = "paymentMethod", source = "paymentMethod")
    DTOOrder OrderToDTOOrder(Order source);

    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "purchaseDate", source = "purchaseDate")
    @Mapping(target = "totalAmount", source = "totalAmount")
    @Mapping(target = "paymentMethod", source = "paymentMethod")
    Order DTOOrderToOrder(DTOOrder destination);
}
