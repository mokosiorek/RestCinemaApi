package kosiorek.michal.restcinemaapi.infrastructure.repository.impl;

import kosiorek.michal.restcinemaapi.domain.rating.Rating;
import kosiorek.michal.restcinemaapi.domain.rating.RatingRepository;
import kosiorek.michal.restcinemaapi.infrastructure.repository.jpa.JpaRatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RatingRepositoryImpl implements RatingRepository {

private final JpaRatingRepository jpaRatingRepository;

    @Override
    public Optional<Rating> addOrUpdate(Rating item) {
        return Optional.of(jpaRatingRepository.save(item));
    }

    @Override
    public Optional<Rating> findById(Long id) {
        return jpaRatingRepository.findById(id);
    }

    @Override
    public Optional<Rating> deleteById(Long id) {

        var deleteOp = jpaRatingRepository.findById(id);
        jpaRatingRepository.deleteById(id);
        return deleteOp;
    }

    @Override
    public List<Rating> findAll() {
        return jpaRatingRepository.findAll();
    }

    @Override
    public List<Rating> findAllById(Iterable<Long> idsCollection) {
        return jpaRatingRepository.findAllById(idsCollection);
    }

    @Override
    public List<Rating> addAll(List<Rating> items) {
        return jpaRatingRepository.saveAll(items);
    }

    public Optional<Rating> findByUserIdAndMovieId(Long userId, Long movieId){
       return jpaRatingRepository.findByUserIdAndMovieId(userId, movieId);
    }

    public List<Rating> findByMovieId(Long movieId){
        return jpaRatingRepository.findByMovieId(movieId);
    }
}
