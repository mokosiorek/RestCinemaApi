package kosiorek.michal.restcinemaapi.application.service;

import kosiorek.michal.restcinemaapi.application.dto.CreateCinemaDto;
import kosiorek.michal.restcinemaapi.application.dto.GetCinemaDto;
import kosiorek.michal.restcinemaapi.application.dto.UpdateCinemaDto;
import kosiorek.michal.restcinemaapi.application.exception.CinemaException;
import kosiorek.michal.restcinemaapi.application.mapper.ModelMapper;
import kosiorek.michal.restcinemaapi.domain.cinema.Cinema;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.CinemaRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CinemaService {

    private final CinemaRepositoryImpl cinemaRepository;

    public List<GetCinemaDto> getAllCinemas(){

        return cinemaRepository.findAll().stream()
                .map(Cinema::toGetCinemaDto)
                .collect(Collectors.toList());

    }

    public GetCinemaDto getCinemaById(Long id){

        if(id == null){
            throw new CinemaException("get Cinema by id - id null");
        }

        return cinemaRepository.findById(id)
                .map(Cinema::toGetCinemaDto)
                .orElseThrow(()-> new CinemaException("get Cinema by id - error"));

    }

    public Long saveOrUpdateCinema(CreateCinemaDto createCinemaDto){

        if(createCinemaDto == null){
            throw new CinemaException("createCinemaDto - null");
        }

        return cinemaRepository.addOrUpdate(ModelMapper.fromCreateCinemaDtoToCinema(createCinemaDto))
                .orElseThrow(()->new CinemaException("save or update cinema - error")).getId();

    }

    public Long updateCinema(Long id, UpdateCinemaDto updateCinemaDto){

        if(id == null){
            throw new CinemaException("update Cinema by id - id null");
        }
        if(updateCinemaDto == null){
            throw new CinemaException("update cinema - updateCinemaDto null");
        }

        Cinema cinema = cinemaRepository.findById(id)
                .orElseThrow(()-> new CinemaException("update Cinema - find cinema by id error"));

        cinema = cinema.update(updateCinemaDto);

        return cinemaRepository.addOrUpdate(cinema)
                .orElseThrow(()-> new CinemaException("update cinema - add Or Update error"))
                .getId();

    }

    public Long deleteCinema(Long id){
        if(id == null){
            throw new CinemaException("delete Cinema by id - id null");
        }
        return cinemaRepository.deleteById(id)
                .orElseThrow(()-> new CinemaException("delete Cinema by id - delete By Id error"))
                .getId();

    }

}
