package kosiorek.michal.restcinemaapi.application.mapper;

import kosiorek.michal.restcinemaapi.application.dto.*;
import kosiorek.michal.restcinemaapi.domain.cinema.Cinema;
import kosiorek.michal.restcinemaapi.domain.cinemaroom.CinemaRoom;
import kosiorek.michal.restcinemaapi.domain.movie.Movie;
import kosiorek.michal.restcinemaapi.domain.rating.Rating;
import kosiorek.michal.restcinemaapi.domain.seat.Seat;
import kosiorek.michal.restcinemaapi.domain.security.User;
import kosiorek.michal.restcinemaapi.domain.show.Show;
import kosiorek.michal.restcinemaapi.domain.ticket.Ticket;

import java.util.HashSet;

public interface ModelMapper {

    static User fromGetUserDtoToUser(GetUserDto getUserDto) {

        return getUserDto==null?null:User.builder()
                .id(getUserDto.getId())
                .username(getUserDto.getUsername())
                .role(getUserDto.getRole())
                .ratings(new HashSet<>())
                .build();
    }

    static Cinema fromCreateCinemaDtoToCinema(CreateCinemaDto createCinemaDto) {
        return createCinemaDto == null ? null : Cinema.builder()
                .name(createCinemaDto.getName())
                .city(createCinemaDto.getCity())
                .cinemaRooms(new HashSet<>())
                .build();
    }

    static CinemaRoom fromCreateCinemaRoomDtoToCinemaRoom(CreateCinemaRoomDto createCinemaRoomDto) {
        return createCinemaRoomDto == null ? null : CinemaRoom.builder()
                .name(createCinemaRoomDto.getName())
                .cinema(createCinemaRoomDto.getGetCinemaDto() == null ? null : fromGetCinemaDtoToCinema(createCinemaRoomDto.getGetCinemaDto()))
                .roomRows(createCinemaRoomDto.getRoomRows())
                .places(createCinemaRoomDto.getPlaces())
                .shows(new HashSet<>())
                .build();

    }

    static Movie fromCreateMovieDtoToMovie(CreateMovieDto createMovieDto) {
        return createMovieDto == null ? null : Movie.builder()
                .title(createMovieDto.getTitle())
                .genre(createMovieDto.getGenre())
                .shows(new HashSet<>())
                .build();
    }

    static Cinema fromGetCinemaDtoToCinema(GetCinemaDto getCinemaDto){

        return getCinemaDto==null?null:Cinema.builder()
                .id(getCinemaDto.getId())
                .name(getCinemaDto.getName())
                .city(getCinemaDto.getCity())
                .cinemaRooms(new HashSet<>())
                .build();

    }

    static CinemaRoom fromGetCinemaRoomDtoToCinemaRoom(GetCinemaRoomDto getCinemaRoomDto){

        return getCinemaRoomDto==null?null:CinemaRoom.builder()
                .id(getCinemaRoomDto.getId())
                .name(getCinemaRoomDto.getName())
                .shows(new HashSet<>())
                .roomRows(getCinemaRoomDto.getRoomRows())
                .places(getCinemaRoomDto.getPlaces())
                .cinema(getCinemaRoomDto.getGetCinemaDto()==null?null:ModelMapper.fromGetCinemaDtoToCinema(getCinemaRoomDto.getGetCinemaDto()))
                .build();

    }

    static Movie fromGetMovieDtoToMovie(GetMovieDto getMovieDto){

        return getMovieDto==null?null: Movie.builder()
                .genre(getMovieDto.getGenre())
                .title(getMovieDto.getTitle())
                .shows(new HashSet<>())
                .ratings(new HashSet<>())
                .build();

    }


    static Show fromCreateShowDtoToShow(CreateShowDto createShowDto){

        return createShowDto == null ? null : Show.builder()
                .showTime(createShowDto.getShowTime())
                .movie(createShowDto.getGetMovieDto()==null?null:fromGetMovieDtoToMovie(createShowDto.getGetMovieDto()))
                .cinemaRoom(createShowDto.getGetCinemaRoomDto()==null?null: fromGetCinemaRoomDtoToCinemaRoom(createShowDto.getGetCinemaRoomDto()))
                .tickets(new HashSet<>())
                .build();

    }

    static Show fromGetShowDtoToShow(GetShowDto getShowDto){
        return getShowDto == null ? null : Show.builder()
                .id(getShowDto.getId())
                .showTime(getShowDto.getShowTime())
                .movie(getShowDto.getGetMovieDto()==null?null:fromGetMovieDtoToMovie(getShowDto.getGetMovieDto()))
                .cinemaRoom(getShowDto.getGetCinemaRoomDto()==null?null:fromGetCinemaRoomDtoToCinemaRoom(getShowDto.getGetCinemaRoomDto()))
                .tickets(new HashSet<>())
                .build();
    }

    static Seat fromGetSeatDtoToSeat(GetSeatDto getSeatDto){

        return getSeatDto == null ? null : Seat.builder()
                .id(getSeatDto.getId())
                .seatNumber(getSeatDto.getSeatNumber())
                .roomRow(getSeatDto.getRoomRow())
                .tickets(new HashSet<>())
                .cinemaRoom(getSeatDto.getGetCinemaRoomDto()==null?null:fromGetCinemaRoomDtoToCinemaRoom(getSeatDto.getGetCinemaRoomDto()))
                .build();

    }

    static Ticket fromCreateTicketDtoToTicket(CreateTicketDto createTicketDto){

        return createTicketDto == null ? null : Ticket.builder()
                .price(createTicketDto.getPrice())
                .orderDate(createTicketDto.getOrderDate())
                .discountType(createTicketDto.getDiscountType())
                .ticketType(createTicketDto.getTicketType())
                .seat(createTicketDto.getGetSeatDto()==null?null:fromGetSeatDtoToSeat(createTicketDto.getGetSeatDto()))
                .show(createTicketDto.getGetShowDto()==null?null:fromGetShowDtoToShow(createTicketDto.getGetShowDto()))
                .build();

    }

    static Rating fromCreateRatingDtoToRating(CreateRatingDto createRatingDto){
        return createRatingDto == null ? null : Rating.builder()
                .date(createRatingDto.getDate())
                .movieRating(createRatingDto.getMovieRating())
                .movie(createRatingDto.getGetMovieDto()==null?null:fromGetMovieDtoToMovie(createRatingDto.getGetMovieDto()))
                .user(createRatingDto.getGetUserDto()==null?null:fromGetUserDtoToUser(createRatingDto.getGetUserDto()))
                .build();

    }

    static Seat fromCreateSeatDtoToSeat(CreateSeatDto createSeatDto){
        return createSeatDto==null ? null : Seat.builder()
                .seatNumber(createSeatDto.getSeatNumber())
                .roomRow(createSeatDto.getRoomRow())
                .cinemaRoom(createSeatDto.getGetCinemaRoomDto()==null?null:fromGetCinemaRoomDtoToCinemaRoom(createSeatDto.getGetCinemaRoomDto()))
                .tickets(new HashSet<>())
                .build();
    }
}
