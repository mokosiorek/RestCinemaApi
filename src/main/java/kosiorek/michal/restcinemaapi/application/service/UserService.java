package kosiorek.michal.restcinemaapi.application.service;

import kosiorek.michal.restcinemaapi.application.dto.GetUserDto;
import kosiorek.michal.restcinemaapi.application.dto.RegisterUser;
import kosiorek.michal.restcinemaapi.application.dto.RegisterUserResponseDto;
import kosiorek.michal.restcinemaapi.application.exception.RegisterException;
import kosiorek.michal.restcinemaapi.application.exception.UserServiceException;
import kosiorek.michal.restcinemaapi.domain.security.User;
import kosiorek.michal.restcinemaapi.domain.security.UserRepository;
import kosiorek.michal.restcinemaapi.infrastructure.repository.impl.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepositoryImpl userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserResponseDto registerUser(RegisterUser registerUser) {

        // sprawdz czy nie null wtedy wyjatek
        if(Objects.isNull(registerUser)){
            throw new RegisterException("registration data null");
        }

        if(Objects.isNull(registerUser.getRole())){
            throw new RegisterException("registration data role is null");
        }

        if(userRepository.findByUsername(registerUser.getUsername()).isPresent()){
            throw new RegisterException("username exists");
        }

        // sprawdz czy hasla takie same
        if(!Objects.equals(registerUser.getPassword(), registerUser.getPasswordConfirmation())){
            throw new RegisterException("passwords do not match");
        }
        // utworz User i zapisz
        User user = User.builder()
                .username(registerUser.getUsername())
                .password(passwordEncoder.encode(registerUser.getPassword()))
                .role(registerUser.getRole())
                .enabled(true)
                .build();

        return userRepository
                .addOrUpdate(user)
                .map(User::toRegisterUserResponseDto)
                .orElseThrow(()->new RegisterException("add or update user error"));
    }

    public GetUserDto getUserByUsername(String username){
        return userRepository
                .findByUsername(username)
                .map(User::toGetUserDto)
                .orElseThrow(()-> new UserServiceException("get user exception"));
    }

    public GetUserDto getUserById(Long id){
        return userRepository
                .findById(id)
                .map(User::toGetUserDto)
                .orElseThrow(()->new UserServiceException("get user by id exception"));
    }
}
