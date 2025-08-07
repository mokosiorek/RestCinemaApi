package kosiorek.michal.restcinemaapi.application.service;

import kosiorek.michal.restcinemaapi.application.dto.CreateTicketDto;
import kosiorek.michal.restcinemaapi.application.dto.GetTicketDto;
import kosiorek.michal.restcinemaapi.application.exception.TicketException;
import kosiorek.michal.restcinemaapi.application.mapper.ModelMapper;
import kosiorek.michal.restcinemaapi.domain.seat.Seat;
import kosiorek.michal.restcinemaapi.domain.show.Show;
import kosiorek.michal.restcinemaapi.domain.ticket.Ticket;
import kosiorek.michal.restcinemaapi.domain.ticket.TicketType;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.SeatRepositoryImpl;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.ShowRepositoryImpl;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.TicketRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketService {

    private final TicketRepositoryImpl ticketRepository;
    private final SeatRepositoryImpl seatRepository;
    private final ShowRepositoryImpl showRepository;

    public GetTicketDto getTicketById(Long id){

        return ticketRepository.findById(id)
                .map(Ticket::toGetTicketDto)
                .orElseThrow(() ->new TicketException("get ticket by id error"));

    }

    public List<GetTicketDto> getTicketsByShowId(Long showId){

        if(showId == null){
            throw new TicketException("showId is null");
        }

        return ticketRepository.findAllByShowId(showId).stream()
                .map(Ticket::toGetTicketDto)
                .collect(Collectors.toList());

    }

    public GetTicketDto saveOrUpdateTicket(CreateTicketDto createTicketDto){

        if(createTicketDto==null){
            throw new TicketException("createTicketDto is null");
        }

        Seat seat = seatRepository.findById(createTicketDto.getGetSeatDto().getId())
                .orElseThrow(()-> new TicketException("get seat by id error"));

        Show show = showRepository.findById(createTicketDto.getGetShowDto().getId())
                .orElseThrow(()-> new TicketException("get show by id error"));

        Ticket ticket = ModelMapper.fromCreateTicketDtoToTicket(createTicketDto);
        ticket.updateSeatAndShow(seat,show);

        return ticketRepository.addOrUpdate(ticket)
                .map(Ticket::toGetTicketDto)
                .orElseThrow(() -> new TicketException("save or update ticket error"));

    }

    public Long deleteTicketById(Long id){

        if(id==null){
            throw new TicketException("delete ticket by id error - id null");
        }

        return ticketRepository.deleteById(id)
                .orElseThrow(()-> new TicketException("delete ticket by id error"))
                .getId();

    }

}
