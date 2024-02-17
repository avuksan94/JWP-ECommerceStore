package hr.algebra.bl.webshop2024bl.Service;

import hr.algebra.dal.webshop2024dal.Entity.Authority;

import java.util.List;

public interface AuthorityService {
    List<Authority> findAll();
    Authority findById(long id);
    Authority save(Authority authority);
    void deleteById(long id);
}
