package hr.algebra.bl.webshop2024bl.Service;

import hr.algebra.dal.webshop2024dal.Entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    User findByUsername(String username);
    User findByEmail(String email);
    User save(User user);
    void deleteByUsername(String username);
    List<User> getByKeyword(String keyword);
    void createAdminUser(String username, String rawPassword,String email);
    void createShopperUser(String username, String rawPassword,String email);
}
