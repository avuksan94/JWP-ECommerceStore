package hr.algebra.api.webshop2024api.ApiMapper;

import hr.algebra.api.webshop2024api.ApiDTO.DTOOrderItem;
import hr.algebra.dal.webshop2024dal.Entity.OrderItem;
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
