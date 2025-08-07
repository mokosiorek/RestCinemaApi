package kosiorek.michal.restcinemaapi.servicetests;

import kosiorek.michal.restcinemaapi.application.dto.CreateSeatDto;
import kosiorek.michal.restcinemaapi.application.dto.GetSeatDto;
import kosiorek.michal.restcinemaapi.application.exception.SeatException;
import kosiorek.michal.restcinemaapi.application.service.SeatService;
import kosiorek.michal.restcinemaapi.domain.cinema.Cinema;
import kosiorek.michal.restcinemaapi.domain.cinemaroom.CinemaRoom;
import kosiorek.michal.restcinemaapi.domain.seat.Seat;
import kosiorek.michal.restcinemaapi.domain.seat.SeatRepository;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.SeatRepositoryImpl;
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
public class SeatServiceTests {

    @TestConfiguration
    static class AppTestConfiguration {
        @MockBean
        private SeatRepositoryImpl seatRepository;
        @Bean
        public SeatService seatService() {return new SeatService(seatRepository);}
    }

    @Autowired
    private SeatService seatService;

    @Autowired
    private SeatRepositoryImpl seatRepository;

    Cinema cinema1 = Cinema.builder().id(1L).name("Cinema 1").city("City 1").cinemaRooms(new HashSet<>()).build();
    CinemaRoom cinemaRoom1 = CinemaRoom.builder().id(1L).cinema(cinema1).roomRows(1L).places(10L).name("Name 1").build();

    Seat seat1 = Seat.builder().id(1L).seatNumber(1L).roomRow(1L).cinemaRoom(cinemaRoom1).build();
    Seat seat2 = Seat.builder().id(2L).seatNumber(2L).roomRow(1L).cinemaRoom(cinemaRoom1).build();
    Seat seat3 = Seat.builder().id(3L).seatNumber(3L).roomRow(1L).cinemaRoom(cinemaRoom1).build();

    @Test
    public void getAllSeatsOfCinemaRoomTest(){

        Mockito.when(seatRepository.findAllByCinemaRoomId(1L))
                .thenReturn(List.of(seat1, seat2, seat3));

        List<GetSeatDto> seats = seatService.getAllSeatsOfCinemaRoom(cinemaRoom1.getId());

        Assertions.assertEquals(3, seats.size());
        Assertions.assertEquals(seat1.toGetSeatDto(), seats.get(0));
        Assertions.assertEquals(seat2.toGetSeatDto(), seats.get(1));
        Assertions.assertEquals(seat3.toGetSeatDto(), seats.get(2));

    }

    @Test
    public void getAllSeatsOfCinemaRoomNullTest(){
        Assertions.assertThrows(SeatException.class, ()-> seatService.getAllSeatsOfCinemaRoom(null));
    }

    public void saveOrUpdateSeatTest(){

        CreateSeatDto createSeatDto = CreateSeatDto.builder().seatNumber(1L).roomRow(1L).getCinemaRoomDto(cinemaRoom1.toGetCinemaRoomDto()).build();

        Seat seatAfterSave = Seat.builder().id(1L).seatNumber(1L).roomRow(1L).cinemaRoom(cinemaRoom1).build();

        Mockito.when(seatRepository.addOrUpdate(Mockito.any(Seat.class))).thenReturn(Optional.of(seatAfterSave));

        GetSeatDto getSeatDto = seatService.saveOrUpdateSeat(createSeatDto);

        Assertions.assertEquals(createSeatDto.getSeatNumber(), getSeatDto.getSeatNumber());
        Assertions.assertEquals(createSeatDto.getRoomRow(), getSeatDto.getRoomRow());
        Assertions.assertEquals(createSeatDto.getGetCinemaRoomDto(), getSeatDto.getGetCinemaRoomDto());

    }

    @Test
    public void saveOrUpdateSeatNullTest(){
        Assertions.assertThrows(SeatException.class, ()-> seatService.saveOrUpdateSeat(null));
    }

    @Test
    public void deleteSeatTest(){

        Mockito.when(seatRepository.deleteById(1L)).thenReturn(Optional.of(seat1));

        Long deletedSeat = seatService.deleteSeat(1L);
        Assertions.assertEquals(1L, deletedSeat);

    }

    @Test
    public void deleteSeatNullTest(){
        Assertions.assertThrows(SeatException.class, ()-> seatService.deleteSeat(null));
    }

}
