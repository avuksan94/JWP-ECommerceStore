package hr.algebra.dal.webshop2024dal.Repository;

import hr.algebra.dal.webshop2024dal.Entity.UserConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserConnectionRepository extends JpaRepository<UserConnection,Long> {
}
