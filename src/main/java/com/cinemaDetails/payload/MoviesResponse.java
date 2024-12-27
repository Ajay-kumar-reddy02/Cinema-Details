package com.cinemaDetails.payload;

import lombok.Getter;
import lombok.Setter;

//It is written only suitable for the Director entity
@Getter
@Setter
public class MoviesResponse {
    private String movieName;
    private String result;
}
