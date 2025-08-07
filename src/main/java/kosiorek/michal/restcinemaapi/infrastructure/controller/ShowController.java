package kosiorek.michal.restcinemaapi.infrastructure.controller;

import kosiorek.michal.restcinemaapi.application.dto.*;
import kosiorek.michal.restcinemaapi.application.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shows")
public class ShowController {

    private final ShowService showService;

    @GetMapping("/room/{roomId}")
    public ResponseData<List<GetShowDto>> getShowsOfCinemaRoom(@PathVariable Long roomId){
        return ResponseData.<List<GetShowDto>>builder()
                .data(showService.getAllShowsOfCinemaRoom(roomId))
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<GetShowDto> addShow(@RequestBody CreateShowDto createShowDto){

        return ResponseData.<GetShowDto>builder()
                .data(showService.saveOrUpdateShow(createShowDto))
                .build();

    }

    @DeleteMapping("/{id}")
    public ResponseData<Long> deleteShow(@PathVariable Long id){
        return ResponseData.<Long>builder()
                .data(showService.deleteShow(id))
                .build();
    }

}
