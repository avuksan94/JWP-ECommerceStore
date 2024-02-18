package hr.algebra.bl.webshop2024bl.ServiceImp;

import hr.algebra.bl.webshop2024bl.Service.AuthorityService;
import hr.algebra.dal.webshop2024dal.Entity.Authority;
import hr.algebra.dal.webshop2024dal.Repository.AuthorityRepository;
import hr.algebra.utils.CustomExceptions.CustomNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authRepo;

    public AuthorityServiceImpl(AuthorityRepository authRepo) {
        this.authRepo = authRepo;
    }

    @Override
    public List<Authority> findAll() {
        return authRepo.findAll();
    }

    @Override
    public Authority findById(long id) {
        Optional<Authority> authorityOptional = authRepo.findById(id);

        if (authorityOptional.isEmpty()){
            throw new CustomNotFoundException("Authority id not found - " + id);
        }

        return authorityOptional.get();
    }

    @Override
    @Transactional
    public Authority save(Authority authority) {
        return authRepo.save(authority);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Optional<Authority> checkIfExists = authRepo.findById(id);
        if (checkIfExists.isEmpty()){
            throw new CustomNotFoundException("Authority with that ID was not found: " + id);
        }
        authRepo.deleteById(id);
    }

}

