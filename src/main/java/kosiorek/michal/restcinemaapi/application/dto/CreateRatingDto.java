package kosiorek.michal.restcinemaapi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateRatingDto {

    private Integer movieRating;
    private LocalDate date;

    private GetUserDto getUserDto;
    private GetMovieDto getMovieDto;

}
