package kosiorek.michal.restcinemaapi.infrastructure.repository.impl;

import kosiorek.michal.restcinemaapi.domain.ticket.Ticket;
import kosiorek.michal.restcinemaapi.domain.ticket.TicketRepository;
import kosiorek.michal.restcinemaapi.infrastructure.repository.jpa.JpaTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TicketRepositoryImpl implements TicketRepository {

    private final JpaTicketRepository jpaTicketRepository;

    @Override
    public Optional<Ticket> addOrUpdate(Ticket item) {
        return Optional.of(jpaTicketRepository.save(item));
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        return jpaTicketRepository.findById(id);
    }

    @Override
    public Optional<Ticket> deleteById(Long id) {

        var deleteOp = jpaTicketRepository.findById(id);
        jpaTicketRepository.deleteById(id);
        return deleteOp;
    }

    @Override
    public List<Ticket> findAll() {
        return jpaTicketRepository.findAll();
    }

    @Override
    public List<Ticket> findAllById(Iterable<Long> idsCollection) {
        return jpaTicketRepository.findAllById(idsCollection);
    }

    @Override
    public List<Ticket> addAll(List<Ticket> items) {
        return jpaTicketRepository.saveAll(items);
    }

    public List<Ticket> findAllByShowId(Long showId) {
        return jpaTicketRepository.findTicketByShowId(showId);
    }
}
