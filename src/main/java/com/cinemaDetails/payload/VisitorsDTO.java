package com.cinemaDetails.payload;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//Helps to transfer data between client and controller. Used for Signin and SignUp API
public class VisitorsDTO {
    @NotBlank(message = "Username should not be null")
    private String username;
    @NotBlank(message = "Password should not be empty")
    private String password;
}
