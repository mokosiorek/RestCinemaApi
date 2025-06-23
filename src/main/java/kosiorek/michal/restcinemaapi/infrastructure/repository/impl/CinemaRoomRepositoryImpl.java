package kosiorek.michal.restcinemaapi.infrastructure.repository.impl;

import kosiorek.michal.restcinemaapi.domain.cinemaroom.CinemaRoom;
import kosiorek.michal.restcinemaapi.domain.cinemaroom.CinemaRoomRepository;
import kosiorek.michal.restcinemaapi.domain.movie.Movie;
import kosiorek.michal.restcinemaapi.infrastructure.repository.jpa.JpaCinemaRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CinemaRoomRepositoryImpl implements CinemaRoomRepository {

    private final JpaCinemaRoomRepository jpaCinemaRoomRepository;

    @Override
    public Optional<CinemaRoom> addOrUpdate(CinemaRoom item) {
        return Optional.of(jpaCinemaRoomRepository.save(item));
    }

    @Override
    public Optional<CinemaRoom> findById(Long id) {
        return jpaCinemaRoomRepository.findById(id);
    }

    @Override
    public Optional<CinemaRoom> deleteById(Long id) {

        var deleteOp = jpaCinemaRoomRepository.findById(id);
        jpaCinemaRoomRepository.deleteById(id);
        return deleteOp;
    }

    @Override
    public List<CinemaRoom> findAll() {
        return jpaCinemaRoomRepository.findAll();
    }

    @Override
    public List<CinemaRoom> findAllById(Iterable<Long> idsCollection) {
        return jpaCinemaRoomRepository.findAllById(idsCollection);
    }

    @Override
    public List<CinemaRoom> addAll(List<CinemaRoom> items) {
        return jpaCinemaRoomRepository.saveAll(items);
    }

}
