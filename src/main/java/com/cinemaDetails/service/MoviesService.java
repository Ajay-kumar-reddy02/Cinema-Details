package com.cinemaDetails.service;

import com.cinemaDetails.payload.MovieDTO4Movies;
import com.cinemaDetails.payload.MoviesDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface MoviesService {
    String addMovieDetails(@Valid MoviesDTO moviesDTO);

    String updateMovieDetails(@Valid MoviesDTO moviesDTO);

    String deleteMovieDetails(String name);

    List<MovieDTO4Movies> getMovie(String name);
}
