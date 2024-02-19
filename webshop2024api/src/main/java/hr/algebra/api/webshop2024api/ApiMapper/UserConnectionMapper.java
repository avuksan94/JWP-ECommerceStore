package hr.algebra.api.webshop2024api.ApiMapper;

import hr.algebra.api.webshop2024api.ApiDTO.DTOUserConnection;
import hr.algebra.dal.webshop2024dal.Entity.UserConnection;
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
