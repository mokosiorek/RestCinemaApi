package kosiorek.michal.restcinemaapi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateMovieDto {

    private Long id;
    private String title;
    private String genre;

}
