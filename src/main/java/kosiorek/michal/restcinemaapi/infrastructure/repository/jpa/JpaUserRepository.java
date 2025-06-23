package kosiorek.michal.restcinemaapi.infrastructure.repository.jpa;

import kosiorek.michal.restcinemaapi.domain.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

}
