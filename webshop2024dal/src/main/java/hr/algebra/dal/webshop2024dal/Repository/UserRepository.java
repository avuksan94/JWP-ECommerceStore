package hr.algebra.dal.webshop2024dal.Repository;

import hr.algebra.dal.webshop2024dal.Entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    @Transactional
    @Modifying
    @Query("DELETE FROM User WHERE username = ?1")
    void deleteByUsername(String username);

    //Custom query
    @Query(value = "select * from User u where u.username like %:keyword%", nativeQuery = true)
    List<User> findByKeyword(@Param("keyword") String keyword);
}
