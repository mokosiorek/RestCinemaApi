package kosiorek.michal.restcinemaapi.domain.cinemaroom;

import kosiorek.michal.restcinemaapi.application.dto.GetCinemaRoomDto;
import kosiorek.michal.restcinemaapi.application.dto.ModifyCinemaRoomDto;
import kosiorek.michal.restcinemaapi.domain.base.BaseEntity;
import kosiorek.michal.restcinemaapi.domain.cinema.Cinema;
import kosiorek.michal.restcinemaapi.domain.seat.Seat;
import kosiorek.michal.restcinemaapi.domain.show.Show;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "cinemarooms")
public class CinemaRoom extends BaseEntity {

    private String name;

    Long roomRows;
    Long places;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @OneToMany(mappedBy = "cinemaRoom")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Show> shows;

    @OneToMany(mappedBy = "cinemaRoom")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Seat> seats;

    /*public Long getRoomRows(){
        return roomRows;
    }
    public Long getPlaces(){
        return places;
    }*/

    public GetCinemaRoomDto toGetCinemaRoomDto(){
        return GetCinemaRoomDto.builder()
                .id(id)
                .name(name)
                .getCinemaDto(cinema.toGetCinemaDto())
                .places(places)
                .roomRows(roomRows)
                .build();
    }

    public CinemaRoom update(ModifyCinemaRoomDto modifyCinemaRoomDto){
        return CinemaRoom.builder()
                .id(id)
                .name(name)
                .places(modifyCinemaRoomDto.getNewPlaces())
                .roomRows(modifyCinemaRoomDto.getNewRows())
                .build();

    }

    public CinemaRoom withChangedCinema(Cinema cinema) {
        return CinemaRoom.builder()
                .id(id)
                .name(name)
                .places(places)
                .roomRows(roomRows)
                .cinema(cinema)
                .build();
    }

    public CinemaRoom withChangedCinemaAndName(Cinema cinema, String name) {
        return CinemaRoom.builder()
                .id(id)
                .name(name)
                .places(places)
                .roomRows(roomRows)
                .cinema(cinema)
                .build();
    }


}
