package kosiorek.michal.restcinemaapi.infrastructure.repository.jpa;

import kosiorek.michal.restcinemaapi.domain.cinemaroom.CinemaRoom;
import kosiorek.michal.restcinemaapi.domain.show.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaShowRepository extends JpaRepository<Show,Long> {

    List<Show> findAllByCinemaRoomId(Long cinemaRoomId);

}
