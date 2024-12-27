package com.cinemaDetails.payload;

import lombok.Getter;
import lombok.Setter;
//Acts as a bridge between Movies Entity and Client
@Getter
@Setter
public class MovieDTO4Movies {
    private String movieName;
    private String result;
    private DirectorDTO4Movies director;
}
