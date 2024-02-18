package hr.algebra.bl.webshop2024bl.ServiceImp;

import hr.algebra.bl.webshop2024bl.Service.SecurityService;
import hr.algebra.bl.webshop2024bl.Service.UserService;
import hr.algebra.dal.webshop2024dal.Entity.Authority;
import hr.algebra.dal.webshop2024dal.Entity.User;
import hr.algebra.dal.webshop2024dal.Enum.Role;
import hr.algebra.dal.webshop2024dal.Repository.AuthorityRepository;
import hr.algebra.dal.webshop2024dal.Repository.UserRepository;
import hr.algebra.utils.CustomExceptions.CustomNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final AuthorityRepository authorityRepo;
    private final SecurityService securityService;

    public UserServiceImpl(UserRepository userRepo, AuthorityRepository authorityRepo, SecurityService securityService) {
        this.userRepo = userRepo;
        this.authorityRepo = authorityRepo;
        this.securityService = securityService;
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> userOptional = Optional.ofNullable(userRepo.findByUsername(username));

        if (userOptional.isEmpty()){
            throw new CustomNotFoundException("User with that username not found - " + username);
        }

        return userOptional.get();
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> userOptional = Optional.ofNullable(userRepo.findByEmail(email));

        if (userOptional.isEmpty()){
            throw new CustomNotFoundException("User with that email not found - " + email);
        }

        return userOptional.get();
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepo.save(user);
    }

    @Override
    @Transactional
    public void deleteByUsername(String username) {
        userRepo.deleteByUsername(username);
    }

    @Override
    public List<User> getByKeyword(String keyword) {
        return userRepo.findByKeyword(keyword);
    }

    @Override
    @Transactional
    public void createAdminUser(String username, String rawPassword,String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(securityService.doBCryptPassEncoding(rawPassword));
        user.setEmail(email);
        user.setEnabled(true);
        userRepo.save(user);

        Authority authority = new Authority();
        authority.setUser(user);
        authority.setAuthority(Role.ROLE_ADMIN);
        authorityRepo.save(authority);
    }

    @Override
    @Transactional
    public void createShopperUser(String username, String rawPassword,String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(securityService.doBCryptPassEncoding(rawPassword));
        user.setEmail(email);
        user.setEnabled(true);
        userRepo.save(user);

        Authority authority = new Authority();
        authority.setUser(user);
        authority.setAuthority(Role.ROLE_SHOPPER);
        authorityRepo.save(authority);
    }

}
