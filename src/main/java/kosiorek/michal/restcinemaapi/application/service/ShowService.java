package kosiorek.michal.restcinemaapi.application.service;

import kosiorek.michal.restcinemaapi.application.dto.CreateCinemaRoomDto;
import kosiorek.michal.restcinemaapi.application.dto.CreateShowDto;
import kosiorek.michal.restcinemaapi.application.dto.GetShowDto;
import kosiorek.michal.restcinemaapi.application.exception.CinemaException;
import kosiorek.michal.restcinemaapi.application.exception.CinemaRoomException;
import kosiorek.michal.restcinemaapi.application.exception.MovieException;
import kosiorek.michal.restcinemaapi.application.exception.ShowException;
import kosiorek.michal.restcinemaapi.application.mapper.ModelMapper;
import kosiorek.michal.restcinemaapi.domain.cinema.Cinema;
import kosiorek.michal.restcinemaapi.domain.cinemaroom.CinemaRoom;
import kosiorek.michal.restcinemaapi.domain.movie.Movie;
import kosiorek.michal.restcinemaapi.domain.show.Show;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.CinemaRoomRepositoryImpl;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.MovieRepositoryImpl;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.ShowRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ShowService {

    private final ShowRepositoryImpl showRepository;
    private final CinemaRoomRepositoryImpl cinemaRoomRepository;
    private final MovieRepositoryImpl movieRepository;

    public GetShowDto saveOrUpdateShow(CreateShowDto createShowDto){

        if(createShowDto==null){
            throw new CinemaRoomException("save or update show - create show Dto null");
        }

        CinemaRoom cinemaRoom = cinemaRoomRepository.findById(createShowDto.getGetCinemaRoomDto().getId())
                .orElseThrow(()-> new CinemaRoomException("cinema room not found"));

        Movie movie = movieRepository.findById(createShowDto.getGetMovieDto().getId())
                .orElseThrow(()-> new MovieException("movie not found"));

        Show show = ModelMapper.fromCreateShowDtoToShow(createShowDto);

        show.updateCinemaRoomAndMovie(cinemaRoom, movie);

        return showRepository.addOrUpdate(show)
                .map(Show::toGetShowDto)
                .orElseThrow(()-> new CinemaRoomException("add or update show - error"));

    }

    public Long deleteShow(Long showId){

        if(showId==null){
            throw new ShowException("delete show - show id null");
        }

        return showRepository.deleteById(showId)
                .orElseThrow(()-> new ShowException("delete show error"))
                .getId();

    }

    public List<GetShowDto> getAllShowsOfCinemaRoom(Long cinemaRoomId){

        if(cinemaRoomId==null){
            throw new ShowException("get all shows of cinema room - cinema room id null");
        }
        return showRepository.findAllByCinemaRoom(cinemaRoomId).stream()
                .map(Show::toGetShowDto)
                .collect(Collectors.toList());
    }

}
