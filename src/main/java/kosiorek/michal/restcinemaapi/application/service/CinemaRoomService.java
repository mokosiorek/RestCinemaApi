package kosiorek.michal.restcinemaapi.application.service;

import kosiorek.michal.restcinemaapi.application.dto.*;
import kosiorek.michal.restcinemaapi.application.exception.CinemaException;
import kosiorek.michal.restcinemaapi.application.exception.CinemaRoomException;
import kosiorek.michal.restcinemaapi.application.mapper.ModelMapper;
import kosiorek.michal.restcinemaapi.domain.cinema.Cinema;
import kosiorek.michal.restcinemaapi.domain.cinemaroom.CinemaRoom;
import kosiorek.michal.restcinemaapi.domain.show.Show;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.CinemaRepositoryImpl;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.CinemaRoomRepositoryImpl;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.ShowRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CinemaRoomService {

    private final CinemaRoomRepositoryImpl cinemaRoomRepository;
    private final CinemaRepositoryImpl cinemaRepository;
    private final ShowRepositoryImpl showRepository;

    public List<GetCinemaRoomDto> getAllCinemaRooms(){

       return cinemaRoomRepository.findAll().stream()
                .map(CinemaRoom::toGetCinemaRoomDto)
                .collect(Collectors.toList());

    }

    public GetCinemaRoomDto getCinemaRoomById(Long id){

        if(id==null){
            throw new CinemaRoomException("get cinema room by id - id null");
        }
        return cinemaRoomRepository.findById(id)
                .map(CinemaRoom::toGetCinemaRoomDto)
                .orElseThrow(()-> new CinemaRoomException("get cinema room by id - find by id error"));

    }

    public Long saveOrUpdateCinemaRoom(CreateCinemaRoomDto createCinemaRoomDto){

        if(createCinemaRoomDto==null){
            throw new CinemaRoomException("save or update Cinema Room - create Cinema room Dto null");
        }

        CinemaRoom cinemaRoom = ModelMapper.fromCreateCinemaRoomDtoToCinemaRoom(createCinemaRoomDto);
        Cinema cinema = cinemaRepository.findById(createCinemaRoomDto.getGetCinemaDto().getId())
                .orElseThrow(()-> new CinemaException("save or update Cinema Room - get cinema by id error"));
        cinemaRoom.withChangedCinema(cinema);

        return cinemaRoomRepository.addOrUpdate(cinemaRoom)
               .orElseThrow(()-> new CinemaRoomException("add or update cinema room - error"))
               .getId();

    }

    public Long updateCinemaRoom(Long id, UpdateCinemaRoomDto updateCinemaRoomDto){

        if(id==null){
            throw new CinemaRoomException("update Cinema Room - id null");
        }
        if(updateCinemaRoomDto==null){
            throw new CinemaRoomException("update Cinema Room - updateCinemaRoomDto null");
        }

        CinemaRoom cinemaRoom = cinemaRoomRepository.findById(id)
                .orElseThrow(()-> new CinemaRoomException("update Cinema Room - find by id error"));

        CinemaRoom cinemaRoomUpdated = cinemaRoom.withChangedCinemaAndName(ModelMapper.fromGetCinemaDtoToCinema(updateCinemaRoomDto.getGetCinemaDto()),updateCinemaRoomDto.getName());

       return cinemaRoomRepository.addOrUpdate(cinemaRoomUpdated)
                .orElseThrow(()-> new CinemaRoomException("update Cinema Room - find by id error"))
                .getId();
    }

    public Long deleteCinemaRoomById(Long id){
        if(id==null){
            throw new CinemaRoomException("delete Cinema Room by id - id null");
        }
        return cinemaRoomRepository.deleteById(id)
                .orElseThrow(()-> new CinemaRoomException("delete Cinema Room by id - id null"))
                .getId();

    }

    public GetCinemaRoomDto modifyRoomSeats(Long id, ModifyCinemaRoomDto modifyCinemaRoomDto){

        if(id==null){
            throw new CinemaRoomException("get cinema room by id - id null");
        }
        if(modifyCinemaRoomDto.getNewRows()==null){
            throw new CinemaRoomException("get cinema room by id - new Room rows can't be null");
        }
        if(modifyCinemaRoomDto.getNewRows()<1){
            throw new CinemaRoomException("get cinema room by id - new Room rows can't be less than 1");
        }

        if(modifyCinemaRoomDto.getNewPlaces()==null){
            throw new CinemaRoomException("get cinema room by id - new places can't be null");
        }
        if(modifyCinemaRoomDto.getNewPlaces()<1){
            throw new CinemaRoomException("get cinema room by id - new places can't be less than 1");
        }

        CinemaRoom room = cinemaRoomRepository.findById(id)
                .orElseThrow(()-> new CinemaRoomException("get cinema room by id - find by id error"));

        List<Show> showsInRoom = showRepository.findAllByCinemaRoom(id);

        if(showsInRoom.stream().anyMatch(show -> show.getShowTime().isAfter(LocalDateTime.now()))){
          throw new CinemaRoomException("cinema room is used or is going to be used - can't change rows and places");
        }

        room = room.update(modifyCinemaRoomDto);

        CinemaRoom roomFromDB = cinemaRoomRepository.addOrUpdate(room)
                .orElseThrow(()->new CinemaRoomException("cinema room save error"));

        return roomFromDB.toGetCinemaRoomDto();
    }

}
