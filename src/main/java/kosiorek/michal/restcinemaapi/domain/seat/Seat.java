package kosiorek.michal.restcinemaapi.domain.seat;

import kosiorek.michal.restcinemaapi.application.dto.GetSeatDto;
import kosiorek.michal.restcinemaapi.domain.base.BaseEntity;
import kosiorek.michal.restcinemaapi.domain.cinemaroom.CinemaRoom;
import kosiorek.michal.restcinemaapi.domain.ticket.Ticket;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "seats")
public class Seat extends BaseEntity {

    private Long seatNumber;
    private Long roomRow;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cinemaRoom_id")
    private CinemaRoom cinemaRoom;

    @OneToMany(mappedBy = "seat")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Ticket> tickets;

    public GetSeatDto toGetSeatDto(){

        return GetSeatDto.builder()
                .id(id)
                .roomRow(roomRow)
                .seatNumber(seatNumber)
                .getCinemaRoomDto(cinemaRoom.toGetCinemaRoomDto())
                .build();

    }


}
