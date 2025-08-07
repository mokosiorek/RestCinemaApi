package kosiorek.michal.restcinemaapi.servicetests;

import kosiorek.michal.restcinemaapi.application.dto.CreateCinemaRoomDto;
import kosiorek.michal.restcinemaapi.application.dto.GetCinemaRoomDto;
import kosiorek.michal.restcinemaapi.application.dto.ModifyCinemaRoomDto;
import kosiorek.michal.restcinemaapi.application.dto.UpdateCinemaRoomDto;
import kosiorek.michal.restcinemaapi.application.exception.CinemaRoomException;
import kosiorek.michal.restcinemaapi.application.service.CinemaRoomService;
import kosiorek.michal.restcinemaapi.domain.cinema.Cinema;
import kosiorek.michal.restcinemaapi.domain.cinemaroom.CinemaRoom;
import kosiorek.michal.restcinemaapi.domain.cinemaroom.CinemaRoomRepository;
import kosiorek.michal.restcinemaapi.domain.show.Show;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.CinemaRepositoryImpl;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.CinemaRoomRepositoryImpl;
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
public class CinemaRoomServiceTests {

    @TestConfiguration
    static class AppTestConfiguration {
        @MockBean
        CinemaRoomRepositoryImpl cinemaRoomRepository;
        @MockBean
        CinemaRepositoryImpl cinemaRepository;
        @MockBean
        ShowRepositoryImpl showRepository;

        @Bean
        CinemaRoomService cinemaRoomService() {return new CinemaRoomService(cinemaRoomRepository, cinemaRepository, showRepository);}
    }

    @Autowired
    CinemaRoomService cinemaRoomService;

    @Autowired
    CinemaRoomRepository cinemaRoomRepository;
    @Autowired
    CinemaRepositoryImpl cinemaRepository;
    @Autowired
    ShowRepositoryImpl showRepository;

    Cinema cinema1 = Cinema.builder().id(1L).name("Cinema 1").city("City 1").cinemaRooms(new HashSet<>()).build();
    Cinema cinema2 = Cinema.builder().id(2L).name("Cinema 2").city("City 2").cinemaRooms(new HashSet<>()).build();

    CinemaRoom cinemaRoom1 = CinemaRoom.builder().cinema(cinema1).roomRows(1L).places(10L).name("Name 1").build();
    CinemaRoom cinemaRoom2 = CinemaRoom.builder().cinema(cinema1).roomRows(2L).places(10L).name("Name 2").build();
    CinemaRoom cinemaRoom3 = CinemaRoom.builder().cinema(cinema1).roomRows(3L).places(10L).name("Name 3").build();

    Show show1 = Show.builder().cinemaRoom(cinemaRoom1).showTime(LocalDateTime.now().plusDays(1)).build();

    @Test
    public void getAllCinemaRoomsTest() {

        Mockito.when(cinemaRoomRepository.findAll()).thenReturn(List.of(cinemaRoom1, cinemaRoom2, cinemaRoom3));

        List<GetCinemaRoomDto> cinemaRooms = cinemaRoomService.getAllCinemaRooms();

        Assertions.assertNotNull(cinemaRooms);
        Assertions.assertEquals(3, cinemaRooms.size());
        Assertions.assertEquals(cinemaRoom1.getId(), cinemaRooms.get(0).getId());
        Assertions.assertEquals(cinemaRoom2.getId(), cinemaRooms.get(1).getId());
        Assertions.assertEquals(cinemaRoom3.getId(), cinemaRooms.get(2).getId());

    }

    @Test
    public void getCinemaRoomByIdTest() {

        Mockito.when(cinemaRoomRepository.findById(1L)).thenReturn(Optional.of(cinemaRoom1));

        GetCinemaRoomDto cinemaRoom = cinemaRoomService.getCinemaRoomById(1L);

        Assertions.assertNotNull(cinemaRoom);
        Assertions.assertEquals("Name 1",cinemaRoom.getName());
        Assertions.assertEquals(cinemaRoom.getGetCinemaDto(), cinema1.toGetCinemaDto() );
        Assertions.assertEquals(1L, cinemaRoom.getRoomRows());
        Assertions.assertEquals(1L, cinemaRoom.getPlaces());

    }

    @Test
    public void getCinemaRoomByIdIdNullTest(){

        Assertions.assertThrows(CinemaRoomException.class, () -> cinemaRoomService.getCinemaRoomById(null));

    }

    @Test
    public void saveOrUpdateCinemaRoomTest() {

        CreateCinemaRoomDto createCinemaRoomDto = CreateCinemaRoomDto.builder().getCinemaDto(cinema1.toGetCinemaDto())
                .name("Name Test").roomRows(1L).places(1L).build();

        CinemaRoom afterSave = CinemaRoom.builder().cinema(cinema1).name("Name Test").roomRows(1L).places(1L).build();

        Mockito.when(cinemaRoomRepository.addOrUpdate(Mockito.any(CinemaRoom.class)))
                .thenReturn(Optional.of(afterSave));
        Mockito.when(cinemaRepository.findById(cinema1.getId())).thenReturn(Optional.of(cinema1));

        GetCinemaRoomDto getCinemaRoomDto = cinemaRoomService.saveOrUpdateCinemaRoom(createCinemaRoomDto);

        Assertions.assertNotNull(getCinemaRoomDto);
        Assertions.assertEquals("Name Test",getCinemaRoomDto.getName());
        Assertions.assertEquals(1L,getCinemaRoomDto.getRoomRows());
        Assertions.assertEquals(1L,getCinemaRoomDto.getPlaces());
        Assertions.assertEquals(cinema1.toGetCinemaDto(),getCinemaRoomDto.getGetCinemaDto());


    }

    @Test
    public void saveOrUpdateCinemaRoomNullTest() {

        Assertions.assertThrows(CinemaRoomException.class, () -> cinemaRoomService.saveOrUpdateCinemaRoom(null));

    }

    @Test
    public void updateCinemaRoomTest() {

        UpdateCinemaRoomDto updateCinemaRoomDto = UpdateCinemaRoomDto.builder()
                .getCinemaDto(cinema2.toGetCinemaDto())
                .name("Name 2 TestUpdate")
                .build();

        CinemaRoom beforeUpdate = cinemaRoom1;
        CinemaRoom afterUpdate = CinemaRoom.builder()
                .cinema(cinema2)
                .roomRows(1L)
                .places(1L)
                .name("Name 2 TestUpdate").build();

        Mockito.when(cinemaRoomRepository.findById(1L)).thenReturn(Optional.of(beforeUpdate));
        Mockito.when(cinemaRoomRepository.addOrUpdate(Mockito.any(CinemaRoom.class))).thenReturn(Optional.of(afterUpdate));

        GetCinemaRoomDto getCinemaRoomDto = cinemaRoomService.updateCinemaRoom(1L, updateCinemaRoomDto);

        Assertions.assertNotNull(getCinemaRoomDto);
        Assertions.assertEquals("Name 2 TestUpdate",getCinemaRoomDto.getName());
        Assertions.assertEquals(1L,getCinemaRoomDto.getRoomRows());
        Assertions.assertEquals(1L,getCinemaRoomDto.getPlaces());
        Assertions.assertEquals(cinema2.toGetCinemaDto(),getCinemaRoomDto.getGetCinemaDto());

    }

    @Test
    public void updateCinemaRoomNullTest() {

        UpdateCinemaRoomDto updateCinemaRoomDto = UpdateCinemaRoomDto.builder()
                .getCinemaDto(cinema2.toGetCinemaDto())
                .name("Name 2 TestUpdate")
                .build();

        Assertions.assertThrows(CinemaRoomException.class, () -> cinemaRoomService.updateCinemaRoom(null, updateCinemaRoomDto));
        Assertions.assertThrows(CinemaRoomException.class, () -> cinemaRoomService.updateCinemaRoom(1L, null));

    }

    @Test
    public void deleteCinemaRoomTest() {

        Mockito.when(cinemaRoomRepository.deleteById(1L)).thenReturn(Optional.of(cinemaRoom1));

        Assertions.assertEquals(1L, cinemaRoomService.deleteCinemaRoomById(1L));

    }

    @Test
    public void deleteCinemaRoomNullTest() {
        Assertions.assertThrows(CinemaRoomException.class, () -> cinemaRoomService.deleteCinemaRoomById(null));
    }

    @Test
    public void modifyCinemaRoomNoCollidingShowsTest() {

        ModifyCinemaRoomDto modifyCinemaRoomDto = ModifyCinemaRoomDto.builder().newRows(3L).newPlaces(3L).build();

        Mockito.when(cinemaRoomRepository.findById(1L)).thenReturn(Optional.of(cinemaRoom1));

        CinemaRoom cinemaRoomAfterModify = CinemaRoom.builder()
                .id(1L)
                .name("Name 1")
                .roomRows(3L)
                .places(3L)
                .cinema(cinema1)
                .build();

        Mockito.when(cinemaRoomRepository.addOrUpdate(Mockito.any(CinemaRoom.class)))
                .thenReturn(Optional.of(cinemaRoomAfterModify));

        Mockito.when(showRepository.findAllByCinemaRoom(1L)).thenReturn(List.of());

        GetCinemaRoomDto getCinemaRoomDto = cinemaRoomService.modifyRoomSeats(1L,modifyCinemaRoomDto);

        Assertions.assertEquals(3L,getCinemaRoomDto.getRoomRows());
        Assertions.assertEquals(3L,getCinemaRoomDto.getPlaces());


    }

    @Test
    public void modifyCinemaRoomCollidingShowsTest() {

        ModifyCinemaRoomDto modifyCinemaRoomDto = ModifyCinemaRoomDto.builder().newRows(3L).newPlaces(3L).build();

        Mockito.when(cinemaRoomRepository.findById(1L)).thenReturn(Optional.of(cinemaRoom1));

        CinemaRoom cinemaRoomAfterModify = CinemaRoom.builder()
                .id(1L)
                .name("Name 1")
                .roomRows(3L)
                .places(3L)
                .cinema(cinema1)
                .build();

        Mockito.when(cinemaRoomRepository.addOrUpdate(Mockito.any(CinemaRoom.class)))
                .thenReturn(Optional.of(cinemaRoomAfterModify));

        Mockito.when(showRepository.findAllByCinemaRoom(1L)).thenReturn(List.of(show1));

        Assertions.assertThrows(CinemaRoomException.class, ()-> cinemaRoomService.modifyRoomSeats(1L,modifyCinemaRoomDto));
    }

    @Test
    public void modifyCinemaRoomNullTest() {

        ModifyCinemaRoomDto modifyCinemaRoomDto = ModifyCinemaRoomDto.builder().newRows(3L).newPlaces(3L).build();
        ModifyCinemaRoomDto modifyCinemaRoomDto1 = ModifyCinemaRoomDto.builder().newRows(0L).newPlaces(3L).build();
        ModifyCinemaRoomDto modifyCinemaRoomDto2 = ModifyCinemaRoomDto.builder().newRows(3L).newPlaces(0L).build();
        ModifyCinemaRoomDto modifyCinemaRoomDto3 = ModifyCinemaRoomDto.builder().newRows(null).newPlaces(0L).build();
        ModifyCinemaRoomDto modifyCinemaRoomDto4 = ModifyCinemaRoomDto.builder().newRows(3L).newPlaces(null).build();

        Assertions.assertThrows(CinemaRoomException.class, ()-> cinemaRoomService.modifyRoomSeats(1L,null));
        Assertions.assertThrows(CinemaRoomException.class, ()-> cinemaRoomService.modifyRoomSeats(null,modifyCinemaRoomDto));
        Assertions.assertThrows(CinemaRoomException.class, ()-> cinemaRoomService.modifyRoomSeats(1L,modifyCinemaRoomDto1));
        Assertions.assertThrows(CinemaRoomException.class, ()-> cinemaRoomService.modifyRoomSeats(1L,modifyCinemaRoomDto2));
        Assertions.assertThrows(CinemaRoomException.class, ()-> cinemaRoomService.modifyRoomSeats(1L,modifyCinemaRoomDto3));
        Assertions.assertThrows(CinemaRoomException.class, ()-> cinemaRoomService.modifyRoomSeats(1L,modifyCinemaRoomDto4));
    }

}
