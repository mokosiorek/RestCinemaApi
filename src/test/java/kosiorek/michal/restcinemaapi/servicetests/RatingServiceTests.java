package kosiorek.michal.restcinemaapi.servicetests;

import kosiorek.michal.restcinemaapi.application.dto.CreateRatingDto;
import kosiorek.michal.restcinemaapi.application.dto.GetRatingDto;
import kosiorek.michal.restcinemaapi.application.exception.RatingException;
import kosiorek.michal.restcinemaapi.application.service.RatingService;
import kosiorek.michal.restcinemaapi.domain.movie.Movie;
import kosiorek.michal.restcinemaapi.domain.rating.Rating;
import kosiorek.michal.restcinemaapi.domain.security.User;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.RatingRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class RatingServiceTests {

    @TestConfiguration
    static class AppTestConfiguration {

        @MockBean
        private RatingRepositoryImpl ratingRepository;

        @Bean
        public RatingService ratingService() {return new RatingService(ratingRepository);}
    }

    @Autowired
    private RatingService ratingService;
    @Autowired
    private RatingRepositoryImpl ratingRepository;

    private Movie movie1 = Movie.builder().id(1L).title("Movie 1").genre("Genre 1").build();
    private User user1 = User.builder().id(1L).username("User1").build();

    private Rating rating1 = Rating.builder().id(1L).user(user1).movie(movie1).movieRating(1).date(LocalDate.now()).build();
    private Rating rating2 = Rating.builder().id(2L).user(user1).movie(movie1).movieRating(2).date(LocalDate.now()).build();
    private Rating rating3 = Rating.builder().id(3L).user(user1).movie(movie1).movieRating(3).date(LocalDate.now()).build();

    @Test
    public void saveOrUpdateRatingTest() {

        CreateRatingDto createRatingDto = CreateRatingDto.builder()
                .movieRating(1)
                .date(LocalDate.now())
                .getMovieDto(movie1.toGetMovieDto())
                .getUserDto(user1.toGetUserDto())
                .build();

        Rating ratingAfterSave = Rating.builder()
                .id(1L)
                .movieRating(1)
                .date(LocalDate.now())
                .movie(movie1)
                .user(user1)
                .build();

        Mockito.when(ratingRepository.addOrUpdate(Mockito.any(Rating.class))).thenReturn(Optional.of(ratingAfterSave));

        GetRatingDto getRatingDto = ratingService.saveOrUpdateRating(createRatingDto);
        Assertions.assertEquals(ratingAfterSave.toGetRatingDto(), getRatingDto);

    }

    @Test
    public void saveOrUpdateRatingNoDuplicateTest(){

        CreateRatingDto createRatingDto = CreateRatingDto.builder()
                .movieRating(1)
                .date(LocalDate.now())
                .getMovieDto(movie1.toGetMovieDto())
                .getUserDto(user1.toGetUserDto())
                .build();

        Rating ratingAfterSave = Rating.builder()
                .id(1L)
                .movieRating(1)
                .date(LocalDate.now())
                .movie(movie1)
                .user(user1)
                .build();

        Rating existingRating = Rating.builder()
                .id(1L)
                .movieRating(1)
                .date(LocalDate.now())
                .movie(movie1)
                .user(user1)
                .build();

        Mockito.when(ratingRepository.addOrUpdate(Mockito.any(Rating.class))).thenReturn(Optional.of(ratingAfterSave));
        Mockito.when(ratingRepository.findByUserIdAndMovieId(createRatingDto.getGetUserDto().getId(),createRatingDto.getGetMovieDto().getId()))
                        .thenReturn(Optional.of(existingRating));

        Assertions.assertThrows(RatingException.class, ()-> ratingService.saveOrUpdateRating(createRatingDto));
    }

    @Test
    public void saveOrUpdateRatingNullTest(){
        Assertions.assertThrows(RatingException.class, ()-> ratingService.saveOrUpdateRating(null));
    }

    @Test
    public void checkIfUserRatedMovieTest(){

        Rating existingRating = Rating.builder()
                .id(1L)
                .movieRating(1)
                .date(LocalDate.now())
                .movie(movie1)
                .user(user1)
                .build();

        Mockito.when(ratingRepository.findByUserIdAndMovieId(existingRating.toGetRatingDto().getGetUserDto().getId(),existingRating.toGetRatingDto().getGetMovieDto().getId()))
                .thenReturn(Optional.of(existingRating));

        Boolean result = ratingService.checkIfUserRatedMovie(existingRating.toGetRatingDto().getGetUserDto().getId(),existingRating.toGetRatingDto().getGetMovieDto().getId());

        Assertions.assertEquals(true, result);

    }

    @Test
    public void checkIfUserRatedMovieFalseTest(){

        Mockito.when(ratingRepository.findByUserIdAndMovieId(1L,1L))
                .thenReturn(Optional.empty());

        Boolean result = ratingService.checkIfUserRatedMovie(1L,1L);

        Assertions.assertEquals(false, result);

    }

    @Test
    public void deleteRatingTest(){

        Mockito.when(ratingRepository.deleteById(1L)).thenReturn(Optional.of(rating1));

        Assertions.assertEquals(rating1.getId(), ratingService.deleteRating(1L));

    }

    @Test
    public void deleteRatingNullTest(){
        Assertions.assertThrows(RatingException.class, ()-> ratingService.deleteRating(null));
    }

    @Test
    public void getAllRatingsOfMovieTest(){

        Mockito.when(ratingRepository.findByMovieId(movie1.getId())).thenReturn(List.of(rating1,rating2,rating3));

        List<GetRatingDto> ratings = ratingService.getAllRatingsOfMovie(movie1.getId());

        Assertions.assertEquals(3, ratings.size());
        Assertions.assertEquals(rating1.toGetRatingDto(), ratings.get(0));
        Assertions.assertEquals(rating2.toGetRatingDto(), ratings.get(1));
        Assertions.assertEquals(rating3.toGetRatingDto(), ratings.get(2));

    }

}
