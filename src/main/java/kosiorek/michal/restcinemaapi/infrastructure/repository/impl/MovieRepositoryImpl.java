package kosiorek.michal.restcinemaapi.infrastructure.repository.impl;

import kosiorek.michal.restcinemaapi.domain.movie.Movie;
import kosiorek.michal.restcinemaapi.domain.movie.MovieRepository;
import kosiorek.michal.restcinemaapi.infrastructure.repository.jpa.JpaMovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MovieRepositoryImpl implements MovieRepository {

    private final JpaMovieRepository jpaMovieRepository;

    @Override
    public Optional<Movie> addOrUpdate(Movie item) {
        return Optional.of(jpaMovieRepository.save(item));
    }

    @Override
    public Optional<Movie> findById(Long id) {
        return jpaMovieRepository.findById(id);
    }

    @Override
    public Optional<Movie> deleteById(Long id) {

        var deleteOp = jpaMovieRepository.findById(id);
        jpaMovieRepository.deleteById(id);
        return deleteOp;
    }

    @Override
    public List<Movie> findAll() {
        return jpaMovieRepository.findAll();
    }

    @Override
    public List<Movie> findAllById(Iterable<Long> idsCollection) {
        return jpaMovieRepository.findAllById(idsCollection);
    }

    @Override
    public List<Movie> addAll(List<Movie> items) {
        return jpaMovieRepository.saveAll(items);
    }

}
