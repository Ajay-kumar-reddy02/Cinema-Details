package com.cinemaDetails.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

//It is using to get the data from the client end only
@Getter
@Setter
public class MoviesDTO {
    @NotBlank(message = "Movie Name cannot be blank")
    private String movieName;
    @NotBlank(message = "Type valid movie result")
    private String result;
    @NotBlank(message = "Director Name cannot be blank")
    private String directorName;
}
