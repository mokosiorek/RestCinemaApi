package kosiorek.michal.restcinemaapi.infrastructure.repository.impl;

import kosiorek.michal.restcinemaapi.domain.cinema.Cinema;
import kosiorek.michal.restcinemaapi.domain.cinema.CinemaRepository;
import kosiorek.michal.restcinemaapi.domain.cinemaroom.CinemaRoom;
import kosiorek.michal.restcinemaapi.domain.cinemaroom.CinemaRoomRepository;
import kosiorek.michal.restcinemaapi.infrastructure.repository.jpa.JpaCinemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CinemaRepositoryImpl implements CinemaRepository {

    private final JpaCinemaRepository jpaCinemaRepository;

    @Override
    public Optional<Cinema> addOrUpdate(Cinema item) {
        return Optional.of(jpaCinemaRepository.save(item));
    }

    @Override
    public Optional<Cinema> findById(Long id) {
        return jpaCinemaRepository.findById(id);
    }

    @Override
    public Optional<Cinema> deleteById(Long id) {

        var deleteOp = jpaCinemaRepository.findById(id);
        jpaCinemaRepository.deleteById(id);
        return deleteOp;
    }

    @Override
    public List<Cinema> findAll() {
        return jpaCinemaRepository.findAll();
    }

    @Override
    public List<Cinema> findAllById(Iterable<Long> idsCollection) {
        return jpaCinemaRepository.findAllById(idsCollection);
    }

    @Override
    public List<Cinema> addAll(List<Cinema> items) {
        return jpaCinemaRepository.saveAll(items);
    }

}
