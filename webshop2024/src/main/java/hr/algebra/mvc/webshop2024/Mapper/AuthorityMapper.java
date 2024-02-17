package hr.algebra.mvc.webshop2024.Mapper;

import hr.algebra.dal.webshop2024dal.Entity.Authority;
import hr.algebra.mvc.webshop2024.DTO.DTOAuthority;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorityMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "authority", source = "authority")
    DTOAuthority AuthorityToDTOAuthority(Authority source);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "authority", source = "authority")
    Authority DTOAuthorityToAuthority(DTOAuthority destination);
}
