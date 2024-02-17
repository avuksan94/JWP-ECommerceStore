package hr.algebra.mvc.webshop2024.Mapper;

import hr.algebra.dal.webshop2024dal.Entity.OrderItem;
import hr.algebra.mvc.webshop2024.DTO.DTOOrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    @Mapping(target = "orderItemId", source = "orderItemId")
    @Mapping(target = "order", source = "order")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "price", source = "price")
    DTOOrderItem OrderItemToDTOOrderItem(OrderItem source);

    @Mapping(target = "orderItemId", source = "orderItemId")
    @Mapping(target = "order", source = "order")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "price", source = "price")
    OrderItem DTOOrderItemToOrderItem(DTOOrderItem destination);
}
