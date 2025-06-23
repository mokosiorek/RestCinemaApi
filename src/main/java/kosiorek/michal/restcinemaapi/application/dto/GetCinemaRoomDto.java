package kosiorek.michal.restcinemaapi.application.dto;

import kosiorek.michal.restcinemaapi.domain.cinema.Cinema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetCinemaRoomDto {

    private Long id;
    private String name;
    private Long roomRows;
    private Long places;
    private GetCinemaDto getCinemaDto;

}
