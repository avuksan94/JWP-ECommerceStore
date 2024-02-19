package hr.algebra.api.webshop2024api.ApiMapper;

import hr.algebra.api.webshop2024api.ApiDTO.DTOUser;
import hr.algebra.dal.webshop2024dal.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "enabled", source = "enabled")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "authorities", source = "authorities")
    DTOUser UserToDTOUser(User source);

    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "enabled", source = "enabled")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "authorities", source = "authorities")
    User DTOUserToUser(DTOUser destination);
}
