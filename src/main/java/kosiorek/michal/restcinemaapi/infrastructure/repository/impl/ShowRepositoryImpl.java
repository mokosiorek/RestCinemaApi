package kosiorek.michal.restcinemaapi.infrastructure.repository.impl;

import kosiorek.michal.restcinemaapi.domain.cinemaroom.CinemaRoom;
import kosiorek.michal.restcinemaapi.domain.show.Show;
import kosiorek.michal.restcinemaapi.domain.show.ShowRepository;
import kosiorek.michal.restcinemaapi.domain.ticket.Ticket;
import kosiorek.michal.restcinemaapi.infrastructure.repository.jpa.JpaShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ShowRepositoryImpl implements ShowRepository {

    private final JpaShowRepository jpaShowRepository;

    @Override
    public Optional<Show> addOrUpdate(Show item) {
        return Optional.of(jpaShowRepository.save(item));
    }

    @Override
    public Optional<Show> findById(Long id) {
        return jpaShowRepository.findById(id);
    }

    @Override
    public Optional<Show> deleteById(Long id) {

        var deleteOp = jpaShowRepository.findById(id);
        jpaShowRepository.deleteById(id);
        return deleteOp;
    }

    @Override
    public List<Show> findAll() {
        return jpaShowRepository.findAll();
    }

    @Override
    public List<Show> findAllById(Iterable<Long> idsCollection) {
        return jpaShowRepository.findAllById(idsCollection);
    }

    @Override
    public List<Show> addAll(List<Show> items) {
        return jpaShowRepository.saveAll(items);
    }

    public List<Show> findAllByCinemaRoom(Long cinemaRoomId){
        return jpaShowRepository.findAllByCinemaRoomId(cinemaRoomId);
    }

}
