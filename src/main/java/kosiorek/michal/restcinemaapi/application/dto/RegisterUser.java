package kosiorek.michal.restcinemaapi.application.dto;

import kosiorek.michal.restcinemaapi.domain.security.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUser {
    private String username;
    private String email;
    private String password;
    private String passwordConfirmation;
    private Role role;
}
