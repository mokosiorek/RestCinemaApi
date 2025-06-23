package kosiorek.michal.restcinemaapi;

import kosiorek.michal.restcinemaapi.application.dto.GetCinemaDto;
import kosiorek.michal.restcinemaapi.application.service.CinemaService;
import kosiorek.michal.restcinemaapi.domain.cinema.Cinema;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.CinemaRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class CinemaServiceTests {

    @TestConfiguration
    static class AppTestConfiguration {
        @MockBean
        private CinemaRepositoryImpl cinemaRepository;

        @Bean
        public CinemaService cinemaService() {return new CinemaService(cinemaRepository);}
    }

    @Autowired
    private CinemaService cinemaService;
    @Autowired
    private CinemaRepositoryImpl cinemaRepository;

    private Cinema cinema1 = Cinema.builder().id(1L).name("Cinema 1").city("City 1").cinemaRooms(new HashSet<>()).build();
    private Cinema cinema2 = Cinema.builder().id(2L).name("Cinema 2").city("City 2").cinemaRooms(new HashSet<>()).build();
    private Cinema cinema3 = Cinema.builder().id(3L).name("Cinema 3").city("City 3").cinemaRooms(new HashSet<>()).build();

    @Test
    public void getAllCinemasTest() {

        Mockito.when(cinemaRepository.findAll())
                .thenReturn(List.of(cinema1, cinema2, cinema3));

        List<GetCinemaDto> cinemas = cinemaService.getAllCinemas();
        Assertions.assertNotNull(cinemas);
        Assertions.assertEquals(3, cinemas.size());
        Assertions.assertNotEquals(4, cinemas.size());
        Assertions.assertEquals(cinema1.getId(), cinemas.get(0).getId());
        Assertions.assertEquals(cinema2.getId(), cinemas.get(1).getId());
        Assertions.assertEquals(cinema3.getId(), cinemas.get(2).getId());
    }

    @Test
    public void getCinemaByIdTest() {

        Mockito.when(cinemaRepository.findById(1L))
                .thenReturn(Optional.ofNullable(cinema1));

        GetCinemaDto cinema = cinemaService.getCinemaById(1L);

        Assertions.assertNotNull(cinema);
        Assertions.assertEquals(1L, cinema.getId());
        Assertions.assertEquals("Cinema 1", cinema.getName());
        Assertions.assertEquals("City 1", cinema.getCity());

    }

    /*
    @Test
    public void saveOrUpdateCinemaTest() {

        CreateCinemaDto createCinemaDto = CreateCinemaDto.builder().id(2L).name("Cinema 2").city("City 2").build();

        Mockito.when(cinemaRepository.addOrUpdate(cinema2))
                .thenReturn(Optional.of(cinema2));
        try (MockedStatic<ModelMapper> modelMapper = Mockito.mockStatic(ModelMapper.class)) {
            modelMapper.when(() -> ModelMapper.fromCreateCinemaDtoToCinema(createCinemaDto))
                    .thenReturn(cinema2);

            Long id = cinemaService.saveOrUpdateCinema(createCinemaDto);
            Assertions.assertEquals(2L,id);
        }

    }
    */

}
