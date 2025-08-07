package kosiorek.michal.restcinemaapi.servicetests;

import kosiorek.michal.restcinemaapi.application.dto.CreateMovieDto;
import kosiorek.michal.restcinemaapi.application.dto.GetMovieDto;
import kosiorek.michal.restcinemaapi.application.dto.UpdateMovieDto;
import kosiorek.michal.restcinemaapi.application.exception.MovieException;
import kosiorek.michal.restcinemaapi.application.service.MovieService;
import kosiorek.michal.restcinemaapi.domain.movie.Movie;
import kosiorek.michal.restcinemaapi.domain.movie.MovieRepository;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.MovieRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class MovieServiceTests {

    @TestConfiguration
    static class AppTestConfiguration {

        @MockBean
        private MovieRepositoryImpl movieRepository;

        @Bean
        public MovieService movieService() {return new MovieService(movieRepository);}

    }

    @Autowired
    private MovieService movieService;
    @Autowired
    private MovieRepository movieRepository;

    private Movie movie1 = Movie.builder().id(1L).title("Movie 1").genre("Genre 1").build();
    private Movie movie2 = Movie.builder().id(2L).title("Movie 2").genre("Genre 2").build();
    private Movie movie3 = Movie.builder().id(3L).title("Movie 3").genre("Genre 3").build();

    @Test
    public void getAllMoviesTest() {

        Mockito.when(movieRepository.findAll()).thenReturn(List.of(movie1, movie2, movie3));
        List<GetMovieDto> movies = movieService.getAllMovies();

        Assertions.assertEquals(3, movies.size());
        Assertions.assertEquals(movie1.toGetMovieDto(), movies.get(0));
        Assertions.assertEquals(movie2.toGetMovieDto(), movies.get(1));
        Assertions.assertEquals(movie3.toGetMovieDto(), movies.get(2));
    }

    @Test
    public void getMovieByIdTest() {

        Mockito.when(movieRepository.findById(1L)).thenReturn(Optional.of(movie1));

        GetMovieDto movie = movieService.getMovieById(1L);

        Assertions.assertEquals(movie1.toGetMovieDto(), movie);
    }

    @Test
    public void getMovieByIdNullTest() {
        Assertions.assertThrows(MovieException.class, ()-> movieService.getMovieById(1L));
    }

    @Test
    public void saveOrUpdateMovieTest() {

        CreateMovieDto createMovieDto = CreateMovieDto.builder().title("Movie 4").genre("Genre 4").build();
        Movie movieAfterSave = Movie.builder().id(4L)
                .title(createMovieDto.getTitle())
                .genre(createMovieDto.getGenre())
                .build();

        Mockito.when(movieRepository.addOrUpdate(Mockito.any(Movie.class))).thenReturn(Optional.of(movieAfterSave));

        GetMovieDto getMovieDto = movieService.saveOrUpdateMovie(createMovieDto);

        Assertions.assertEquals(movieAfterSave.toGetMovieDto(), getMovieDto);
        Assertions.assertEquals(4L, getMovieDto.getId());
        Assertions.assertEquals("Movie 4", getMovieDto.getTitle());
        Assertions.assertEquals("Genre 4", getMovieDto.getGenre());

    }

    @Test
    public void saveOrUpdateMovieNullTest() {
        Assertions.assertThrows(MovieException.class, ()-> movieService.saveOrUpdateMovie(null));
    }

    @Test
    public void deleteMovieByIdTest() {
        Mockito.when(movieRepository.deleteById(1L)).thenReturn(Optional.of(movie1));
        Assertions.assertEquals(1L, movieService.deleteMovie(1L));
    }

    @Test
    public void deleteMovieByIdNullTest() {
        Assertions.assertThrows(MovieException.class, ()-> movieService.deleteMovie(1L));
    }

    @Test
    public void updateMovieByIdTest() {

        UpdateMovieDto updateMovieDto = UpdateMovieDto.builder().id(4L).title("Title 4 Update").genre("Genre 4 Update").build();

        Movie movieBeforeUpdate = Movie.builder()
                .id(4L)
                .title("Title")
                .genre("Genre")
                .build();

        Movie movieAfterUpdate = Movie.builder()
                .id(4L)
                .title(updateMovieDto.getTitle())
                .genre(updateMovieDto.getGenre())
                .build();

        Mockito.when(movieRepository.findById(4L)).thenReturn(Optional.of(movieBeforeUpdate));
        Mockito.when(movieRepository.addOrUpdate(Mockito.any(Movie.class))).thenReturn(Optional.of(movieAfterUpdate));

        GetMovieDto movieDto = movieService.updateMovie(4L, updateMovieDto);

        Assertions.assertEquals(movieAfterUpdate.toGetMovieDto(), movieDto);
        Assertions.assertEquals(4L, movieDto.getId());
        Assertions.assertEquals("Title 4 Update", movieDto.getTitle());
        Assertions.assertEquals("Genre 4 Update", movieDto.getGenre());

    }

    @Test
    public void updateMovieByIdNullTest() {

        UpdateMovieDto updateMovieDto = UpdateMovieDto.builder().id(4L).title("Title 4 Update").genre("Genre 4 Update").build();

        Assertions.assertThrows(MovieException.class, ()-> movieService.updateMovie(null, null));
        Assertions.assertThrows(MovieException.class, ()-> movieService.updateMovie(1L, null));
        Assertions.assertThrows(MovieException.class, ()-> movieService.updateMovie(null, updateMovieDto));


    }

}
