package kosiorek.michal.restcinemaapi.infrastructure.repository.impl;

import kosiorek.michal.restcinemaapi.domain.seat.Seat;
import kosiorek.michal.restcinemaapi.domain.seat.SeatRepository;
import kosiorek.michal.restcinemaapi.domain.show.Show;
import kosiorek.michal.restcinemaapi.infrastructure.repository.jpa.JpaSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SeatRepositoryImpl implements SeatRepository {

    private final JpaSeatRepository jpaSeatRepository;

    @Override
    public Optional<Seat> addOrUpdate(Seat item) {
        return Optional.of(jpaSeatRepository.save(item));
    }

    @Override
    public Optional<Seat> findById(Long id) {
        return jpaSeatRepository.findById(id);
    }

    @Override
    public Optional<Seat> deleteById(Long id) {

        var deleteOp = jpaSeatRepository.findById(id);
        jpaSeatRepository.deleteById(id);
        return deleteOp;
    }

    @Override
    public List<Seat> findAll() {
        return jpaSeatRepository.findAll();
    }

    @Override
    public List<Seat> findAllById(Iterable<Long> idsCollection) {
        return jpaSeatRepository.findAllById(idsCollection);
    }

    @Override
    public List<Seat> addAll(List<Seat> items) {
        return jpaSeatRepository.saveAll(items);
    }

    public List<Seat> findAllByCinemaRoomId(Long cinemaRoomId) {
        return jpaSeatRepository.findAllByCinemaRoomIdOrderBySeatNumberAscRoomRowAsc(cinemaRoomId);
    }

}
