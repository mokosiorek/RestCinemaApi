package kosiorek.michal.restcinemaapi.infrastructure.controller;

import kosiorek.michal.restcinemaapi.application.dto.*;
import kosiorek.michal.restcinemaapi.application.service.CinemaRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinemarooms")
public class CinemaRoomController {

    private final CinemaRoomService cinemaRoomService;

    @GetMapping
    public ResponseData<List<GetCinemaRoomDto>> getAllCinemaRooms(){

        return ResponseData.<List<GetCinemaRoomDto>>builder()
                .data(cinemaRoomService.getAllCinemaRooms())
                .build();

    }

    @GetMapping("/{id}")
    public ResponseData<GetCinemaRoomDto> getCinemaRoomById(@PathVariable Long id){
        return ResponseData.<GetCinemaRoomDto>builder()
                .data(cinemaRoomService.getCinemaRoomById(id))
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<Long> addCinemaRoom(@RequestBody CreateCinemaRoomDto createCinemaRoomDto){

        return ResponseData.<Long>builder()
                .data(cinemaRoomService.saveOrUpdateCinemaRoom(createCinemaRoomDto))
                .build();

    }

    @PutMapping("/{id}")
    public ResponseData<Long> updateCinemaRoom(@PathVariable Long id, @RequestBody UpdateCinemaRoomDto updateCinemaRoomDto){
        return ResponseData.<Long>builder()
                .data(cinemaRoomService.updateCinemaRoom(id,updateCinemaRoomDto))
                .build();
    }


    @PostMapping("/modify/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<GetCinemaRoomDto> modifyCinemaRoom(@PathVariable Long id,@RequestBody ModifyCinemaRoomDto modifyCinemaRoomDto){

        return ResponseData.<GetCinemaRoomDto>builder()
                .data(cinemaRoomService.modifyRoomSeats(id, modifyCinemaRoomDto))
                .build();

    }

    @DeleteMapping("/{id}")
    public ResponseData<Long> deleteCinemaRoom(@PathVariable Long id){
        return ResponseData.<Long>builder()
                .data(cinemaRoomService.deleteCinemaRoomById(id))
                .build();
    }

}
