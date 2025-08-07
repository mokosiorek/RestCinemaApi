package kosiorek.michal.restcinemaapi.application.service;

import kosiorek.michal.restcinemaapi.application.dto.CreateSeatDto;
import kosiorek.michal.restcinemaapi.application.dto.GetSeatDto;
import kosiorek.michal.restcinemaapi.application.exception.SeatException;
import kosiorek.michal.restcinemaapi.application.mapper.ModelMapper;
import kosiorek.michal.restcinemaapi.domain.seat.Seat;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.SeatRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SeatService {

private final SeatRepositoryImpl seatRepository;

public List<GetSeatDto> getAllSeatsOfCinemaRoom(Long cinemaRoomId){

    if(cinemaRoomId==null){
        throw new SeatException("Cinema room id cannot be null");
    }

    return seatRepository.findAllByCinemaRoomId(cinemaRoomId).stream()
            .map(Seat::toGetSeatDto)
            .collect(Collectors.toList());

}

public GetSeatDto saveOrUpdateSeat(CreateSeatDto createSeatDto){

    if(createSeatDto==null) {
        throw new SeatException("create seat dto cannot be null");
    }

    return seatRepository.addOrUpdate(ModelMapper.fromCreateSeatDtoToSeat(createSeatDto))
            .map(Seat::toGetSeatDto)
            .orElseThrow(()-> new SeatException("save or update error"));

}

public Long deleteSeat(Long seatId){

    if(seatId==null){
        throw new SeatException("seatId cannot be null");
    }
    return seatRepository.deleteById(seatId)
            .orElseThrow(()-> new SeatException("delete seat error"))
            .getId();
}

}
