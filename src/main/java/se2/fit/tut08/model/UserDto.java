package se2.fit.tut08.model;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    @NotBlank(message = "Username is required")
    @Length(min = 8, message = "Username must have at least 8 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Length(min = 8, message = "Password must have at least 8 characters")
    private String password;

    @NotBlank(message = "Address is required")
    private String address;
    
    private String avatar;
}
