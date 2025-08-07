package kosiorek.michal.restcinemaapi.infrastructure.controller;

import kosiorek.michal.restcinemaapi.application.dto.*;
import kosiorek.michal.restcinemaapi.application.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    @GetMapping("/{id}")
    public ResponseData<GetTicketDto> getTicketById(@PathVariable Long id){
        return ResponseData.<GetTicketDto>builder()
                .data(ticketService.getTicketById(id))
                .build();
    }

    @GetMapping("/show/{showId}")
    public ResponseData<List<GetTicketDto>> getTicketsByShowId(@PathVariable Long showId){
        return ResponseData.<List<GetTicketDto>>builder()
                .data(ticketService.getTicketsByShowId(showId))
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<GetTicketDto> addTicket(@RequestBody CreateTicketDto createTicketDto){

        return ResponseData.<GetTicketDto>builder()
                .data(ticketService.saveOrUpdateTicket(createTicketDto))
                .build();

    }

    @DeleteMapping("/{id}")
    public ResponseData<Long> deleteTicket(@PathVariable Long id){
        return ResponseData.<Long>builder()
                .data(ticketService.deleteTicketById(id))
                .build();
    }

}
