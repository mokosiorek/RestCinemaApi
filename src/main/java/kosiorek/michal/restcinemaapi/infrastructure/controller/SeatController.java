package kosiorek.michal.restcinemaapi.infrastructure.controller;

import kosiorek.michal.restcinemaapi.application.dto.CreateRatingDto;
import kosiorek.michal.restcinemaapi.application.dto.CreateSeatDto;
import kosiorek.michal.restcinemaapi.application.dto.GetSeatDto;
import kosiorek.michal.restcinemaapi.application.dto.ResponseData;
import kosiorek.michal.restcinemaapi.application.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seats")
public class SeatController {

    private final SeatService seatService;

    @GetMapping("/room/{cinemaRoomId}")
    public ResponseData<List<GetSeatDto>> getAllSeatsOfCinemaRoom(@PathVariable Long cinemaRoomId){

        return ResponseData.<List<GetSeatDto>>builder()
                .data(seatService.getAllSeatsOfCinemaRoom(cinemaRoomId))
                .build();

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<Long> addSeat(@RequestBody CreateSeatDto createSeatDto){

        return ResponseData.<Long>builder()
                .data(seatService.saveOrUpdateSeat(createSeatDto))
                .build();

    }

    @DeleteMapping("/{id}")
    public ResponseData<Long> deleteSeat(@PathVariable Long id){
        return ResponseData.<Long>builder()
                .data(seatService.deleteSeat(id))
                .build();
    }

}

