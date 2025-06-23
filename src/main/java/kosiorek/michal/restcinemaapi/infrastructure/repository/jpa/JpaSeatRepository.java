package kosiorek.michal.restcinemaapi.infrastructure.repository.jpa;

import kosiorek.michal.restcinemaapi.domain.seat.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaSeatRepository extends JpaRepository<Seat,Long> {

    public List<Seat> findAllByCinemaRoomIdOrderBySeatNumberAscRoomRowAsc(Long cinemaRoomId);


}
