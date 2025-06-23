package kosiorek.michal.restcinemaapi.domain.cinemaroom;

import java.util.function.Function;

public interface CinemaRoomUtils {
    Function<CinemaRoom, Long> toRoomRow = cinemaRoom -> cinemaRoom.roomRows;
    Function<CinemaRoom, Long> toPlaces = cinemaRoom -> cinemaRoom.places;
}
