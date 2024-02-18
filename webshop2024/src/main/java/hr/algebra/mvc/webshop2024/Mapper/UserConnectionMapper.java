package hr.algebra.mvc.webshop2024.Mapper;

import hr.algebra.dal.webshop2024dal.Entity.User;
import hr.algebra.dal.webshop2024dal.Entity.UserConnection;
import hr.algebra.mvc.webshop2024.DTO.DTOUser;
import hr.algebra.mvc.webshop2024.DTO.DTOUserConnection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserConnectionMapper {
    @Mapping(target = "username", source = "username")
    @Mapping(target = "lastConnection", source = "lastConnection")
    @Mapping(target = "ipAddress", source = "ipAddress")
    DTOUserConnection UserConnectionToDTOUserConnection(UserConnection source);

    @Mapping(target = "username", source = "username")
    @Mapping(target = "lastConnection", source = "lastConnection")
    @Mapping(target = "ipAddress", source = "ipAddress")
    UserConnection DTOUserConnectionToUserConnection(DTOUserConnection destination);
}
