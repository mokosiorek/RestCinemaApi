package kosiorek.michal.restcinemaapi.infrastructure.repository.jpa;

import kosiorek.michal.restcinemaapi.domain.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMovieRepository extends JpaRepository<Movie,Long> {
}
