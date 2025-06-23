package kosiorek.michal.restcinemaapi.infrastructure.controller;

import kosiorek.michal.restcinemaapi.application.dto.GetUserDto;
import kosiorek.michal.restcinemaapi.application.dto.RegisterUser;
import kosiorek.michal.restcinemaapi.application.dto.RegisterUserResponseDto;
import kosiorek.michal.restcinemaapi.application.dto.ResponseData;
import kosiorek.michal.restcinemaapi.application.service.UserService;
import kosiorek.michal.restcinemaapi.domain.security.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<RegisterUserResponseDto> createUser(@RequestBody RegisterUser registerUser) {
        return ResponseData.<RegisterUserResponseDto>builder()
                .data(userService.registerUser(registerUser))
                .build();

    }

    @GetMapping("/{username}")
    public ResponseData<GetUserDto> getUser(@PathVariable(name = "username") String username){

        return ResponseData.<GetUserDto>builder()
                .data(userService.getUserByUsername(username))
                .build();

    }

    @GetMapping("/id/{id}")
    public ResponseData<GetUserDto> getUserById(@PathVariable(name = "id") Long id){

        return ResponseData.<GetUserDto>builder()
                .data(userService.getUserById(id))
                .build();

    }

}
