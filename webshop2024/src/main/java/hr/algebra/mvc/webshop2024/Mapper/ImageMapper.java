package hr.algebra.mvc.webshop2024.Mapper;

import hr.algebra.dal.webshop2024dal.Entity.Category;
import hr.algebra.mvc.webshop2024.DTO.DTOCategory;
import hr.algebra.mvc.webshop2024.DTO.DTOImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import hr.algebra.dal.webshop2024dal.Entity.Image;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    @Mapping(target = "imageId", source = "imageId")
    @Mapping(target = "imageUrl", source = "imageUrl")
    DTOImage ImageToDTOImage(Image source);

    @Mapping(target = "imageId", source = "imageId")
    @Mapping(target = "imageUrl", source = "imageUrl")
    Image DTOImageToImage(DTOImage destination);
}
