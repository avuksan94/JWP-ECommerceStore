package hr.algebra.bl.webshop2024bl.Service;

import hr.algebra.dal.webshop2024dal.Entity.Image;

import java.util.List;

public interface ImageService {
    List<Image> findAll();
    Image findById(long id);
    Image save(Image obj);
    void deleteById(long id);
}
