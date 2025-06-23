package kosiorek.michal.restcinemaapi.domain.show;

import kosiorek.michal.restcinemaapi.application.dto.GetShowDto;
import kosiorek.michal.restcinemaapi.domain.base.BaseEntity;
import kosiorek.michal.restcinemaapi.domain.cinemaroom.CinemaRoom;
import kosiorek.michal.restcinemaapi.domain.movie.Movie;
import kosiorek.michal.restcinemaapi.domain.ticket.Ticket;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "shows")
public class Show extends BaseEntity {

    @Getter
    private LocalDateTime showTime;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cinemaroom_id")
    private CinemaRoom cinemaRoom;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @OneToMany(mappedBy = "show")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Ticket> tickets;

    public GetShowDto toGetShowDto(){
        return GetShowDto.builder()
                .id(id)
                .showTime(showTime)
                .getMovieDto(movie.toGetMovieDto())
                .getCinemaRoomDto(cinemaRoom.toGetCinemaRoomDto())
                .build();
    }

    public void updateCinemaRoomAndMovie(CinemaRoom cinemaRoom, Movie movie){
        this.cinemaRoom = cinemaRoom;
        this.movie = movie;
    }

}
