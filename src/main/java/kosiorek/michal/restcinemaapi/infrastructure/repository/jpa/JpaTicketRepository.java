package kosiorek.michal.restcinemaapi.infrastructure.repository.jpa;

import kosiorek.michal.restcinemaapi.domain.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaTicketRepository extends JpaRepository<Ticket,Long> {

    List<Ticket> findTicketByShowId(Long showId);

}
