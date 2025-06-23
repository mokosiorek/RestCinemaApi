package kosiorek.michal.restcinemaapi.infrastructure.repository.jpa;

import kosiorek.michal.restcinemaapi.domain.rating.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaRatingRepository extends JpaRepository<Rating,Long> {

    Optional<Rating> findByUserIdAndMovieId(Long userId, Long movieId);
    List<Rating> findByMovieId(Long movieId);

}
