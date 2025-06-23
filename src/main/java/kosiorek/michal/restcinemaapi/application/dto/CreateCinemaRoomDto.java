package kosiorek.michal.restcinemaapi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCinemaRoomDto {

    private String name;

    private Long roomRows;
    private Long places;

    private GetCinemaDto getCinemaDto;


}
