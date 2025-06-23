package kosiorek.michal.restcinemaapi.domain.movie;


import kosiorek.michal.restcinemaapi.application.dto.GetMovieDto;
import kosiorek.michal.restcinemaapi.application.dto.UpdateMovieDto;
import kosiorek.michal.restcinemaapi.domain.base.BaseEntity;
import kosiorek.michal.restcinemaapi.domain.rating.Rating;
import kosiorek.michal.restcinemaapi.domain.show.Show;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "movies")
public class Movie extends BaseEntity {

    private String title;
    private String genre;

    @OneToMany(mappedBy = "movie")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Show> shows;

    @OneToMany(mappedBy = "movie")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Rating> ratings;

    public GetMovieDto toGetMovieDto(){
        return GetMovieDto.builder()
                .id(id)
                .genre(genre)
                .title(title)
                .build();
    }

    public Movie update(UpdateMovieDto updateMovieDto){

        return Movie.builder()
                .id(updateMovieDto.getId())
                .genre(updateMovieDto.getGenre())
                .title(updateMovieDto.getTitle())
                .build();

    }

}
