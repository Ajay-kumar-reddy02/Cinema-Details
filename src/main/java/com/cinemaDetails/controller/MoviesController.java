package com.cinemaDetails.controller;

import com.cinemaDetails.payload.MovieDTO4Movies;
import com.cinemaDetails.payload.MoviesDTO;
import com.cinemaDetails.service.MoviesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MoviesController {

    private MoviesService moviesService;
    public MoviesController(MoviesService moviesService){
        this.moviesService = moviesService;
    }

    //To add the movie details in the Movies table after checking the director existence in directors table
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<String> addMovieDetails(@Valid @RequestBody MoviesDTO moviesDTO){
        String message = moviesService.addMovieDetails(moviesDTO);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    //To update the movie details in the movies table after checking the movie and director existence in corresponding tables
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<String> updateMovieDetails(@Valid @RequestBody MoviesDTO moviesDTO){
        String message = moviesService.updateMovieDetails(moviesDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //To delete the movie details in the movie table after checking the movie existence in movies table
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{name}")
    public ResponseEntity<String> deleteMovieDetails(@PathVariable("name") String name){
        String message = moviesService.deleteMovieDetails(name);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<MovieDTO4Movies>> getMovie(@PathVariable String name){
        List<MovieDTO4Movies> movie = moviesService.getMovie(name);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }
}
