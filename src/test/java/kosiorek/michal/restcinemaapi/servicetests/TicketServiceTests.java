package kosiorek.michal.restcinemaapi.servicetests;

import kosiorek.michal.restcinemaapi.application.dto.CreateTicketDto;
import kosiorek.michal.restcinemaapi.application.dto.GetTicketDto;
import kosiorek.michal.restcinemaapi.application.exception.TicketException;
import kosiorek.michal.restcinemaapi.application.service.TicketService;
import kosiorek.michal.restcinemaapi.domain.cinema.Cinema;
import kosiorek.michal.restcinemaapi.domain.cinemaroom.CinemaRoom;
import kosiorek.michal.restcinemaapi.domain.movie.Movie;
import kosiorek.michal.restcinemaapi.domain.seat.Seat;
import kosiorek.michal.restcinemaapi.domain.show.Show;
import kosiorek.michal.restcinemaapi.domain.ticket.DiscountType;
import kosiorek.michal.restcinemaapi.domain.ticket.Ticket;
import kosiorek.michal.restcinemaapi.domain.ticket.TicketType;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.SeatRepositoryImpl;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.ShowRepositoryImpl;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.TicketRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class TicketServiceTests {

    @TestConfiguration
    static class AppTestConfiguration {
        @MockBean
        private TicketRepositoryImpl ticketRepository;
        @MockBean
        private SeatRepositoryImpl seatRepository;
        @MockBean
        private ShowRepositoryImpl showRepository;

        @Bean
        public TicketService ticketService() {return new TicketService(ticketRepository, seatRepository, showRepository);}
    }

    @Autowired
    private TicketService ticketService;
    @Autowired
    private TicketRepositoryImpl ticketRepository;
    @Autowired
    private SeatRepositoryImpl seatRepository;
    @Autowired
    private ShowRepositoryImpl showRepository;

    Movie movie1 = Movie.builder().id(1L).title("Movie 1").genre("Genre 1").build();
    Cinema cinema1 = Cinema.builder().id(1L).name("Cinema 1").city("City 1").cinemaRooms(new HashSet<>()).build();
    CinemaRoom cinemaRoom1 = CinemaRoom.builder().id(1L).cinema(cinema1).roomRows(1L).places(10L).name("Name 1").build();

    Seat seat1 = Seat.builder().id(1L).seatNumber(1L).roomRow(1L).cinemaRoom(cinemaRoom1).build();
    Show show1 = Show.builder().id(1L).movie(movie1).showTime(LocalDateTime.now()).cinemaRoom(cinemaRoom1).build();

    Ticket ticket1 = Ticket.builder().id(1L).seat(seat1).show(show1).price(BigDecimal.ONE).orderDate(LocalDate.now()).ticketType(TicketType.RESERVED).discountType(DiscountType.NORMAL).build();
    Ticket ticket2 = Ticket.builder().id(2L).seat(seat1).show(show1).price(BigDecimal.ONE).orderDate(LocalDate.now()).ticketType(TicketType.RESERVED).discountType(DiscountType.NORMAL).build();
    Ticket ticket3 = Ticket.builder().id(3L).seat(seat1).show(show1).price(BigDecimal.ONE).orderDate(LocalDate.now()).ticketType(TicketType.RESERVED).discountType(DiscountType.NORMAL).build();

    @Test
    public void getTicketByIdTest(){
        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket1));
        GetTicketDto getTicketDto = ticketService.getTicketById(1L);
        Assertions.assertEquals(ticket1.toGetTicketDto(), getTicketDto);
    }

    @Test
    public void getTicketByIdNullTest(){
        Assertions.assertThrows(TicketException.class, ()-> ticketService.getTicketById(null));
    }

    @Test
    public void getTicketByShowIdTest(){

        Mockito.when(ticketRepository.findAllByShowId(1L)).thenReturn(List.of(ticket1,ticket2,ticket3));

        List<GetTicketDto> tickets = ticketService.getTicketsByShowId(1L);
        Assertions.assertEquals(3, tickets.size());
        Assertions.assertEquals(ticket1.toGetTicketDto(), tickets.get(0));
        Assertions.assertEquals(ticket2.toGetTicketDto(), tickets.get(1));
        Assertions.assertEquals(ticket3.toGetTicketDto(), tickets.get(2));
    }

    @Test
    public void getTicketByShowIdNullTest(){
        Assertions.assertThrows(TicketException.class, ()-> ticketService.getTicketsByShowId(null));
    }

    @Test
    public void saveOrUpdateTicketTest(){

        CreateTicketDto createTicketDto = CreateTicketDto.builder()
                .getSeatDto(seat1.toGetSeatDto())
                .getShowDto(show1.toGetShowDto())
                .price(BigDecimal.ONE)
                .orderDate(LocalDate.now())
                .ticketType(TicketType.RESERVED)
                .discountType(DiscountType.NORMAL)
                .build();

        Ticket ticketAfterSave = Ticket.builder().id(1L).seat(seat1).show(show1).price(BigDecimal.ONE).orderDate(LocalDate.now()).ticketType(TicketType.RESERVED).discountType(DiscountType.NORMAL).build();

        Mockito.when(showRepository.findById(1L)).thenReturn(Optional.of(show1));
        Mockito.when(seatRepository.findById(1L)).thenReturn(Optional.of(seat1));
        Mockito.when(ticketRepository.addOrUpdate(Mockito.any(Ticket.class))).thenReturn(Optional.of(ticketAfterSave));

        GetTicketDto getTicketDto = ticketService.saveOrUpdateTicket(createTicketDto);

        Assertions.assertEquals(ticketAfterSave.toGetTicketDto(), getTicketDto);

    }

    @Test
    public void saveOrUpdateTicketNullTest(){
        Assertions.assertThrows(TicketException.class, ()-> ticketService.saveOrUpdateTicket(null));
    }

    @Test
    public void deleteTicketByIdTest(){
        Mockito.when(ticketRepository.deleteById(1L)).thenReturn(Optional.of(ticket1));
        Long id = ticketService.deleteTicketById(1L);
        Assertions.assertEquals(1L, id);
    }

    @Test
    public void deleteTicketByIdNullTest(){
        Assertions.assertThrows(TicketException.class, ()-> ticketService.deleteTicketById(null));
    }

}
