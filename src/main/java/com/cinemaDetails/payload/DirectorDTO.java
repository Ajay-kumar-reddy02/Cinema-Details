package com.cinemaDetails.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
//Helps to send data between Client and Entity & Entity and Client
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DirectorDTO {
    @NotBlank(message = "Name should not be null, blank, and whitespaces")
    private String name;
    private Set<MoviesResponse> moviesEntities;
}
