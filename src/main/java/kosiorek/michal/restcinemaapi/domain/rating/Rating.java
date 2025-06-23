package kosiorek.michal.restcinemaapi.domain.rating;

import kosiorek.michal.restcinemaapi.application.dto.GetRatingDto;
import kosiorek.michal.restcinemaapi.domain.base.BaseEntity;
import kosiorek.michal.restcinemaapi.domain.movie.Movie;
import kosiorek.michal.restcinemaapi.domain.security.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "ratings")
public class Rating extends BaseEntity {

    private Integer movieRating;
    private LocalDate date;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public GetRatingDto toGetRatingDto(){
        return GetRatingDto.builder()
                .id(id)
                .date(date)
                .getMovieDto(movie.toGetMovieDto())
                .getUserDto(user.toGetUserDto())
                .build();
    }

}
