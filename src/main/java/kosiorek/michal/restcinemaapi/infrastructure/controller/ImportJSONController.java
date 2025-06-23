package kosiorek.michal.restcinemaapi.infrastructure.controller;

import kosiorek.michal.restcinemaapi.application.service.JSONService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/import")
public class ImportJSONController {

    private final JSONService jsonService;

    @GetMapping(value = "/cinemas")
    public void importCinemaDataFromJSON(){

        jsonService.importCinemasFromJSON();

    }

    @GetMapping(value = "/movies")
    public void importMoviesDataFromJSON(){

        jsonService.importMoviesFromJSON();

    }

}
