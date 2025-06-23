package kosiorek.michal.restcinemaapi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetShowDto {

    private Long id;

    private LocalDateTime showTime;

    private GetCinemaRoomDto getCinemaRoomDto;
    private GetMovieDto getMovieDto;

}
