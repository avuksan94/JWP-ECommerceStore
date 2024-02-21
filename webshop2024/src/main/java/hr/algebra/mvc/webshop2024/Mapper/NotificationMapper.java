package hr.algebra.mvc.webshop2024.Mapper;

import hr.algebra.dal.webshop2024dal.Entity.Image;
import hr.algebra.dal.webshop2024dal.Entity.Notification;
import hr.algebra.mvc.webshop2024.DTO.DTOImage;
import hr.algebra.mvc.webshop2024.DTO.DTONotification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    @Mapping(target = "notificationId", source = "notificationId")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "readAt", source = "readAt")
    @Mapping(target = "viewed", source = "viewed")
    DTONotification NotificationToDTONotification(Notification source);

    @Mapping(target = "notificationId", source = "notificationId")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "readAt", source = "readAt")
    @Mapping(target = "viewed", source = "viewed")
    Notification DTONotificationToNotification(DTONotification destination);
}
