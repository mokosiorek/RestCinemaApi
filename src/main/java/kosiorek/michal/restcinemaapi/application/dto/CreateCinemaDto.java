package kosiorek.michal.restcinemaapi.application.dto;

import kosiorek.michal.restcinemaapi.domain.cinemaroom.CinemaRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCinemaDto {

    private Long id;
    private String name;
    private String city;

    private Set<CreateCinemaRoomDto> cinemaRooms;


}
