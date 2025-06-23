package kosiorek.michal.restcinemaapi.infrastructure.controller;

import kosiorek.michal.restcinemaapi.application.dto.CreateCinemaDto;
import kosiorek.michal.restcinemaapi.application.dto.GetCinemaDto;
import kosiorek.michal.restcinemaapi.application.dto.ResponseData;
import kosiorek.michal.restcinemaapi.application.dto.UpdateCinemaDto;
import kosiorek.michal.restcinemaapi.application.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinemas")
public class CinemaController {

    private final CinemaService cinemaService;

    @GetMapping
    public ResponseData<List<GetCinemaDto>> getAllCinemas(){

        return ResponseData.<List<GetCinemaDto>>builder()
                .data(cinemaService.getAllCinemas())
                .build();

    }

    @GetMapping("/{id}")
    public ResponseData<GetCinemaDto> getCinema(@PathVariable Long id){
        return ResponseData.<GetCinemaDto>builder()
                .data(cinemaService.getCinemaById(id))
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<Long> addCinema(@RequestBody CreateCinemaDto createCinemaDto){

        return ResponseData.<Long>builder()
                .data(cinemaService.saveOrUpdateCinema(createCinemaDto))
                .build();

    }

    @PutMapping("/{id}")
    public ResponseData<Long> updateCinema(@PathVariable Long id, @RequestBody UpdateCinemaDto updateCinemaDto){
        return ResponseData.<Long>builder()
                .data(cinemaService.updateCinema(id,updateCinemaDto))
                .build();
    }

    @PatchMapping("/{id}")
    public ResponseData<Long> updatePatchCinema(@PathVariable Long id, @RequestBody UpdateCinemaDto updateCinemaDto){
        return ResponseData.<Long>builder()
                .data(cinemaService.updateCinema(id,updateCinemaDto))
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseData<Long> deleteCinema(@PathVariable Long id){
        return ResponseData.<Long>builder()
                .data(cinemaService.deleteCinema(id))
                .build();
    }

}
