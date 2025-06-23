package kosiorek.michal.restcinemaapi.infrastructure.repository.jpa;

import kosiorek.michal.restcinemaapi.domain.cinemaroom.CinemaRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCinemaRoomRepository extends JpaRepository<CinemaRoom,Long> {
}
