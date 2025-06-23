package kosiorek.michal.restcinemaapi.application.dto;

import kosiorek.michal.restcinemaapi.domain.ticket.DiscountType;
import kosiorek.michal.restcinemaapi.domain.ticket.TicketType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTicketDto {

    private LocalDate orderDate;
    private BigDecimal price;
    private TicketType ticketType;
    private DiscountType discountType;

    private GetShowDto getShowDto;
    private GetSeatDto getSeatDto;

}
