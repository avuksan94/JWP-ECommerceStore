package hr.algebra.mvc.webshop2024.Mapper;

import hr.algebra.dal.webshop2024dal.Entity.Image;
import hr.algebra.dal.webshop2024dal.Entity.Order;
import hr.algebra.mvc.webshop2024.DTO.DTOImage;
import hr.algebra.mvc.webshop2024.DTO.DTOOrder;
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
