package kosiorek.michal.restcinemaapi.servicetests;

import kosiorek.michal.restcinemaapi.application.dto.CreateCinemaDto;
import kosiorek.michal.restcinemaapi.application.dto.GetCinemaDto;
import kosiorek.michal.restcinemaapi.application.dto.UpdateCinemaDto;
import kosiorek.michal.restcinemaapi.application.exception.CinemaException;
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

    @Test
    public void getCinemaByIdIdNullTest(){

        Assertions.assertThrows(CinemaException.class, () -> cinemaService.getCinemaById(null));

    }


    @Test
    public void saveOrUpdateCinemaTest() {

        CreateCinemaDto createCinemaDto = CreateCinemaDto.builder().id(null).name("Cinema 2").city("City 2").build();
        Cinema cinemaAfterSave = Cinema.builder()
                .id(2L)
                .name(createCinemaDto.getName())
                .city(createCinemaDto.getCity())
                .build();

        Mockito.when(cinemaRepository.addOrUpdate(Mockito.any(Cinema.class)))
                .thenReturn(Optional.ofNullable(cinemaAfterSave));

            GetCinemaDto getCinemaDto = cinemaService.saveOrUpdateCinema(createCinemaDto);
            Assertions.assertEquals(2L,getCinemaDto.getId());
            Assertions.assertEquals("Cinema 2",getCinemaDto.getName());
            Assertions.assertEquals("City 2",getCinemaDto.getCity());

    }

    @Test
    public void saveOrUpdateCinemaNullTest() {

        Assertions.assertThrows(CinemaException.class, () -> cinemaService.saveOrUpdateCinema(null));

    }

    @Test
    public void updateCinemaTest() {

        UpdateCinemaDto updateCinemaDto = UpdateCinemaDto.builder().name("Cinema 2 update").city("City 2 update").build();

        Cinema cinemaBeforeUpdate = Cinema.builder()
                .id(2L)
                .name("Cinema 2")
                .city("City 2")
                .build();
        Cinema cinemaAfterUpdate = Cinema.builder()
                .id(2L)
                .name(updateCinemaDto.getName())
                .city(updateCinemaDto.getCity())
                .build();

        Mockito.when(cinemaRepository.findById(2L))
                .thenReturn(Optional.ofNullable(cinemaBeforeUpdate));
        Mockito.when(cinemaRepository.addOrUpdate(Mockito.any(Cinema.class)))
                .thenReturn(Optional.ofNullable(cinemaAfterUpdate));

        GetCinemaDto cinemaDto = cinemaService.updateCinema(2L,updateCinemaDto);

        Assertions.assertEquals(2L,cinemaDto.getId());
        Assertions.assertEquals("Cinema 2 update",cinemaDto.getName());
        Assertions.assertEquals("City 2 update",cinemaDto.getCity());

    }

    @Test
    public void updateCinemaNullTest() {

        UpdateCinemaDto updateCinemaDto = UpdateCinemaDto.builder().name("Cinema 2 update").city("City 2 update").build();

        Assertions.assertThrows(CinemaException.class, () -> cinemaService.updateCinema(null, updateCinemaDto ));
        Assertions.assertThrows(CinemaException.class, () -> cinemaService.updateCinema(2L, null ));

    }

    @Test
    public void deleteCinemaTest() {

        Mockito.when(cinemaRepository.deleteById(1L))
                .thenReturn(Optional.ofNullable(cinema1));

        Assertions.assertEquals(1L, cinemaService.deleteCinema(1L));

    }

    @Test
    public void deleteCinemaNullTest() {
        Assertions.assertThrows(CinemaException.class, () -> cinemaService.deleteCinema(null));
    }


}
