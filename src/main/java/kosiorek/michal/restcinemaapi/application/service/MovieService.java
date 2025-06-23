package kosiorek.michal.restcinemaapi.application.service;

import kosiorek.michal.restcinemaapi.application.dto.*;
import kosiorek.michal.restcinemaapi.application.exception.CinemaException;
import kosiorek.michal.restcinemaapi.application.exception.MovieException;
import kosiorek.michal.restcinemaapi.application.mapper.ModelMapper;
import kosiorek.michal.restcinemaapi.domain.cinema.Cinema;
import kosiorek.michal.restcinemaapi.domain.movie.Movie;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.MovieRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieService {

    private final MovieRepositoryImpl movieRepository;

    public List<GetMovieDto> getAllMovies(){

        return movieRepository.findAll()
                .stream()
                .map(Movie::toGetMovieDto)
                .collect(Collectors.toList());

    }

    public GetMovieDto getMovieById(Long id){

        if(id == null){
            throw new MovieException("get Movie by id - id null");
        }

        return movieRepository.findById(id)
                .map(Movie::toGetMovieDto)
                .orElseThrow(()-> new MovieException("get Cinema by id - error"));

    }

    public Long saveOrUpdateMovie(CreateMovieDto createMovieDto){

        return movieRepository.addOrUpdate(ModelMapper.fromCreateMovieDtoToMovie(createMovieDto))
                .orElseThrow(()->new MovieException("save or update movie - error")).getId();

    }

    public Long updateMovie(Long id, UpdateMovieDto updateMovieDto){

        if(id == null){
            throw new MovieException("update movie by id - id null");
        }
        if(updateMovieDto == null){
            throw new MovieException("update movie - updateMovieDto null");
        }

        Movie movie = movieRepository.findById(id)
                .orElseThrow(()-> new MovieException("update Cinema - find cinema by id error"));

        movie = movie.update(updateMovieDto);

        return movieRepository.addOrUpdate(movie)
                .orElseThrow(()-> new MovieException("update cinema - add Or Update error"))
                .getId();

    }

    public Long deleteMovie(Long id){
        if(id == null){
            throw new CinemaException("delete movie by id - id null");
        }
        return movieRepository.deleteById(id)
                .orElseThrow(()-> new MovieException("delete movie by id - delete By Id error"))
                .getId();

    }

}
