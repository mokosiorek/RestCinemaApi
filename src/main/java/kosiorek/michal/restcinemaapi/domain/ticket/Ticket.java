package kosiorek.michal.restcinemaapi.domain.ticket;

import kosiorek.michal.restcinemaapi.application.dto.GetTicketDto;
import kosiorek.michal.restcinemaapi.domain.base.BaseEntity;
import kosiorek.michal.restcinemaapi.domain.seat.Seat;
import kosiorek.michal.restcinemaapi.domain.show.Show;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntity {

    private LocalDate orderDate;
    private BigDecimal price;
    private TicketType ticketType;
    private DiscountType discountType;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "show_id")
    private Show show;

    public GetTicketDto toGetTicketDto(){
        return GetTicketDto.builder()
                .id(id)
                .orderDate(orderDate)
                .price(price)
                .ticketType(ticketType)
                .discountType(discountType)
                .getSeatDto(seat.toGetSeatDto())
                .getShowDto(show.toGetShowDto())
                .build();
    }

    public void updateSeatAndShow(Seat seat, Show show){
        this.seat = seat;
        this.show = show;
    }

}
