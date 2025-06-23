package kosiorek.michal.restcinemaapi.application.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import kosiorek.michal.restcinemaapi.application.dto.CreateCinemaDto;
import kosiorek.michal.restcinemaapi.application.dto.CreateMovieDto;
import kosiorek.michal.restcinemaapi.application.exception.CinemaException;
import kosiorek.michal.restcinemaapi.application.mapper.ModelMapper;
import kosiorek.michal.restcinemaapi.domain.cinema.Cinema;
import kosiorek.michal.restcinemaapi.domain.cinemaroom.CinemaRoom;
import kosiorek.michal.restcinemaapi.domain.cinemaroom.CinemaRoomUtils;
import kosiorek.michal.restcinemaapi.domain.movie.Movie;
import kosiorek.michal.restcinemaapi.domain.seat.Seat;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.CinemaRepositoryImpl;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.CinemaRoomRepositoryImpl;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.MovieRepositoryImpl;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.SeatRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class JSONService {

    private static final Type CINEMA_TYPE = new TypeToken<List<CreateCinemaDto>>() {}.getType();

    private static final Type MOVIE_TYPE = new TypeToken<List<CreateMovieDto>>() {}.getType();

    private final CinemaRepositoryImpl cinemaRepository;
    private final CinemaRoomRepositoryImpl cinemaRoomRepository;
    private final MovieRepositoryImpl movieRepository;
    private final SeatRepositoryImpl seatRepository;

    public void importCinemasFromJSON() {


        Gson gson = new Gson();

        try (Reader reader = new FileReader("cinemas.json")) {

            // Convert JSON File to Java Object
            List<CreateCinemaDto> createCinemaDtos = gson.fromJson(reader, CINEMA_TYPE);
            createCinemaDtos.forEach(cinema -> {
                Cinema c = cinemaRepository.addOrUpdate(ModelMapper.fromCreateCinemaDtoToCinema(cinema))
                        .orElseThrow(() -> new CinemaException("import from json - add or update cinema error"));

                cinema.getCinemaRooms().forEach(cinemaRoom -> {
                    CinemaRoom cRoom = CinemaRoom.builder()
                            .name(cinemaRoom.getName())
                            .roomRows(cinemaRoom.getRoomRows())
                            .places(cinemaRoom.getPlaces())
                            .cinema(c)
                            .build();
                    CinemaRoom cr = cinemaRoomRepository.addOrUpdate(cRoom)
                            .orElseThrow(() -> new CinemaException("import from json - add or update cinema room error"));

                    for(long i = 1; i<= CinemaRoomUtils.toRoomRow.apply(cr); i++){

                        for(long j=1; j<=CinemaRoomUtils.toPlaces.apply(cr);j++){
                            Seat seat = Seat.builder()
                                    .cinemaRoom(cr)
                                    .roomRow(i)
                                    .seatNumber(j)
                                    .build();
                            seatRepository.addOrUpdate(seat);
                        }

                    }


                });

            });

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void importMoviesFromJSON() {

        Gson gson = new Gson();

        try (Reader reader = new FileReader("movies.json")) {

            // Convert JSON File to Java Object
            List<CreateMovieDto> createMovieDtos = gson.fromJson(reader, MOVIE_TYPE);
            createMovieDtos.forEach(movie -> {
                Movie m = movieRepository.addOrUpdate(ModelMapper.fromCreateMovieDtoToMovie(movie))
                        .orElseThrow(() -> new CinemaException("import from json - add or update movie error"));
            });


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
