package kosiorek.michal.restcinemaapi.domain.security;

import kosiorek.michal.restcinemaapi.application.dto.GetUserDto;
import kosiorek.michal.restcinemaapi.application.dto.RegisterUserResponseDto;
import kosiorek.michal.restcinemaapi.domain.base.BaseEntity;
import kosiorek.michal.restcinemaapi.domain.rating.Rating;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String username;
    private String password;

    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Rating> ratings;

    public GetUserDto toGetUserDto() {
        return GetUserDto.builder()
                .id(id)
                .username(username)
                .password(password)
                .role(role)
                .build();
    }

    public RegisterUserResponseDto toRegisterUserResponseDto() {
        return RegisterUserResponseDto.builder()
                .id(id)
                .username(username)
                .build();
    }
    }
