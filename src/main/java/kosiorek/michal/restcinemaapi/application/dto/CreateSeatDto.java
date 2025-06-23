package kosiorek.michal.restcinemaapi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSeatDto {

    private Long seatNumber;
    private Long roomRow;

    private GetCinemaRoomDto getCinemaRoomDto;

}
