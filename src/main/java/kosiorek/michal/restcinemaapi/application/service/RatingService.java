package kosiorek.michal.restcinemaapi.application.service;

import kosiorek.michal.restcinemaapi.application.dto.CreateRatingDto;
import kosiorek.michal.restcinemaapi.application.dto.GetRatingDto;
import kosiorek.michal.restcinemaapi.application.exception.RatingException;
import kosiorek.michal.restcinemaapi.application.mapper.ModelMapper;
import kosiorek.michal.restcinemaapi.domain.rating.Rating;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.RatingRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RatingService {

    private final RatingRepositoryImpl ratingRepository;

    public Long saveOrUpdateRating(CreateRatingDto createRatingDto) {

        if(createRatingDto == null){
            throw new RatingException("save or update rating cannot be null");
        }

        if(checkIfUserRatedMovie(createRatingDto.getGetUserDto().getId(),createRatingDto.getGetMovieDto().getId()))
        {
            throw new RatingException("rating cannot be duplicated");
        }

        return ratingRepository.addOrUpdate(ModelMapper.fromCreateRatingDtoToRating(createRatingDto))
                .orElseThrow(()-> new RatingException("save or update rating error"))
                .getId();

    }

    public boolean checkIfUserRatedMovie(Long userId, Long movieId) {

        if(userId == null){
            throw new RatingException("user id cannot be null");
        }
        if(movieId == null){
            throw new RatingException("movie id cannot be null");
        }

        return ratingRepository.findByUserIdAndMovieId(userId,movieId).isPresent();

    }

    public Long deleteRating(Long id) {

        if(id == null){
            throw new RatingException("delete rating - id cannot be null");
        }

        return ratingRepository.deleteById(id)
                .orElseThrow(()-> new RatingException("delete rating error"))
                .getId();

    }

    public List<GetRatingDto> getAllRatingsOfMovie(Long movieId) {

        if(movieId == null){
            throw new RatingException("movie id cannot be null");
        }

        return ratingRepository.findByMovieId(movieId).stream()
                .map(Rating::toGetRatingDto)
                .collect(Collectors.toList());

    }

}
