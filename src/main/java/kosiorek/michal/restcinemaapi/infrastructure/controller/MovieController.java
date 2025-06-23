package kosiorek.michal.restcinemaapi.infrastructure.controller;

import kosiorek.michal.restcinemaapi.application.dto.*;
import kosiorek.michal.restcinemaapi.application.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public ResponseData<List<GetMovieDto>> getAllMovies(){

        return ResponseData.<List<GetMovieDto>>builder()
                .data(movieService.getAllMovies())
                .build();

    }

    @GetMapping("/{id}")
    public ResponseData<GetMovieDto> getMovie(@PathVariable Long id){
        return ResponseData.<GetMovieDto>builder()
                .data(movieService.getMovieById(id))
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<Long> addMovie(@RequestBody CreateMovieDto createMovieDto){

        return ResponseData.<Long>builder()
                .data(movieService.saveOrUpdateMovie(createMovieDto))
                .build();

    }

    @PutMapping("/{id}")
    public ResponseData<Long> updateMovie(@PathVariable Long id, @RequestBody UpdateMovieDto updateMovieDto){
        return ResponseData.<Long>builder()
                .data(movieService.updateMovie(id,updateMovieDto))
                .build();
    }

    @PatchMapping("/{id}")
    public ResponseData<Long> updatePatchMovie(@PathVariable Long id, @RequestBody UpdateMovieDto updateMovieDto){
        return ResponseData.<Long>builder()
                .data(movieService.updateMovie(id,updateMovieDto))
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseData<Long> deleteMovie(@PathVariable Long id){
        return ResponseData.<Long>builder()
                .data(movieService.deleteMovie(id))
                .build();
    }


}
