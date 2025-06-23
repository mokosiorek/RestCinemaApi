package kosiorek.michal.restcinemaapi.infrastructure.controller;

import kosiorek.michal.restcinemaapi.application.dto.*;
import kosiorek.michal.restcinemaapi.application.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ratings")
public class RatingController {

    private final RatingService ratingService;

    @GetMapping("/movie/{movieId}")
    public ResponseData<List<GetRatingDto>> getAllRatingsOfMovie(@PathVariable Long movieId){

        return ResponseData.<List<GetRatingDto>>builder()
                .data(ratingService.getAllRatingsOfMovie(movieId))
                .build();

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<Long> addRating(@RequestBody CreateRatingDto createRatingDto){

        return ResponseData.<Long>builder()
                .data(ratingService.saveOrUpdateRating(createRatingDto))
                .build();

    }

    @DeleteMapping("/{id}")
    public ResponseData<Long> deleteMovie(@PathVariable Long id){
        return ResponseData.<Long>builder()
                .data(ratingService.deleteRating(id))
                .build();
    }

}
