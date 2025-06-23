package kosiorek.michal.restcinemaapi.infrastructure.repository.jpa;

import kosiorek.michal.restcinemaapi.domain.cinema.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCinemaRepository extends JpaRepository<Cinema,Long> {
}
