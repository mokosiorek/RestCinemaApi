package kosiorek.michal.restcinemaapi.servicetests;

import kosiorek.michal.restcinemaapi.application.dto.CreateShowDto;
import kosiorek.michal.restcinemaapi.application.dto.GetShowDto;
import kosiorek.michal.restcinemaapi.application.exception.ShowException;
import kosiorek.michal.restcinemaapi.application.service.ShowService;
import kosiorek.michal.restcinemaapi.domain.cinema.Cinema;
import kosiorek.michal.restcinemaapi.domain.cinemaroom.CinemaRoom;
import kosiorek.michal.restcinemaapi.domain.movie.Movie;
import kosiorek.michal.restcinemaapi.domain.show.Show;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.CinemaRoomRepositoryImpl;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.MovieRepositoryImpl;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.ShowRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ShowServiceTests {

    @TestConfiguration
    static class AppTestConfiguration {

        @MockBean
        private ShowRepositoryImpl showRepository;
        @MockBean
        private CinemaRoomRepositoryImpl cinemaRoomRepository;
        @MockBean
        private MovieRepositoryImpl movieRepository;

        @Bean
        public ShowService showService() {return new ShowService(showRepository, cinemaRoomRepository, movieRepository);}

    }

    @Autowired
    private ShowService showService;
    @Autowired
    private ShowRepositoryImpl showRepository;
    @Autowired
    private CinemaRoomRepositoryImpl cinemaRoomRepository;
    @Autowired
    private MovieRepositoryImpl movieRepository;

    Movie movie1 = Movie.builder().id(1L).title("Movie 1").genre("Genre 1").build();
    Cinema cinema1 = Cinema.builder().id(1L).name("Cinema 1").city("City 1").cinemaRooms(new HashSet<>()).build();
    CinemaRoom cinemaRoom1 = CinemaRoom.builder().id(1L).cinema(cinema1).roomRows(1L).places(10L).name("Name 1").build();

    Show show1 = Show.builder().id(1L).movie(movie1).showTime(LocalDateTime.now()).cinemaRoom(cinemaRoom1).build();
    Show show2 = Show.builder().id(2L).movie(movie1).showTime(LocalDateTime.now()).cinemaRoom(cinemaRoom1).build();
    Show show3 = Show.builder().id(3L).movie(movie1).showTime(LocalDateTime.now()).cinemaRoom(cinemaRoom1).build();

    @Test
    public void saveOrUpdateShowTests(){

        CreateShowDto createShowDto = CreateShowDto.builder()
                .getCinemaRoomDto(cinemaRoom1.toGetCinemaRoomDto())
                .getMovieDto(movie1.toGetMovieDto())
                .showTime(LocalDateTime.now())
                .build();

        Show showAfterSave = Show.builder().id(1L).movie(movie1).showTime(LocalDateTime.now()).cinemaRoom(cinemaRoom1).build();

        Mockito.when(movieRepository.findById(1L)).thenReturn(Optional.of(movie1));
        Mockito.when(cinemaRoomRepository.findById(1L)).thenReturn(Optional.of(cinemaRoom1));
        Mockito.when(showRepository.addOrUpdate(Mockito.any(Show.class))).thenReturn(Optional.of(showAfterSave));

        GetShowDto getShowDto = showService.saveOrUpdateShow(createShowDto);

        Assertions.assertEquals(showAfterSave.toGetShowDto(), getShowDto);

    }

    @Test
    public void saveOrUpdateShowNullTest(){

        Assertions.assertThrows(ShowException.class, ()-> showService.saveOrUpdateShow(null));

    }

    @Test
    public void deleteShowByIdTest(){

        Mockito.when(showRepository.deleteById(1L)).thenReturn(Optional.of(show1));

        Long deletedShow = showService.deleteShow(1L);
        Assertions.assertEquals(1L, deletedShow);

    }

    @Test
    public void deleteShowByIdNullTest(){
        Assertions.assertThrows(ShowException.class, ()-> showService.deleteShow(null));
    }

    @Test
    public void getAllShowsOfCinemaRoomTest(){

        Mockito.when(showRepository.findAllByCinemaRoom(1L)).thenReturn(List.of(show1,show2,show3));

        List<GetShowDto> shows = showService.getAllShowsOfCinemaRoom(1L);

        Assertions.assertEquals(3, shows.size());
        Assertions.assertEquals(show1.toGetShowDto(), shows.get(0));
        Assertions.assertEquals(show2.toGetShowDto(), shows.get(1));
        Assertions.assertEquals(show3.toGetShowDto(), shows.get(2));

    }

    @Test
    public void getAllShowsIfCinemaRoomNullTest(){
        Assertions.assertThrows(ShowException.class, ()-> showService.getAllShowsOfCinemaRoom(null));
    }

}
