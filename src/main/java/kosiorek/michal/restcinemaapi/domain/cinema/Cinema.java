package kosiorek.michal.restcinemaapi.domain.cinema;

import kosiorek.michal.restcinemaapi.application.dto.CreateCinemaDto;
import kosiorek.michal.restcinemaapi.application.dto.GetCinemaDto;
import kosiorek.michal.restcinemaapi.application.dto.UpdateCinemaDto;
import kosiorek.michal.restcinemaapi.application.exception.CinemaException;
import kosiorek.michal.restcinemaapi.domain.base.BaseEntity;
import kosiorek.michal.restcinemaapi.domain.cinemaroom.CinemaRoom;
import kosiorek.michal.restcinemaapi.domain.seat.Seat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "cinemas")
public class Cinema extends BaseEntity {

    private String name;
    private String city;

    @OneToMany(mappedBy = "cinema")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<CinemaRoom> cinemaRooms;

    public GetCinemaDto toGetCinemaDto(){

        return GetCinemaDto.builder()
                .id(id)
                .name(name)
                .city(city)
                .build();

    }

    public Cinema update(UpdateCinemaDto updateCinemaDto){

        return Cinema.builder()
                .id(id)
                .name(updateCinemaDto.getName()!=null? updateCinemaDto.getName() : name)
                .city(updateCinemaDto.getCity()!=null? updateCinemaDto.getCity() : city)
                .build();

    }

}
