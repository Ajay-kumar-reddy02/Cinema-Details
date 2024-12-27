package com.cinemaDetails.repository;

import com.cinemaDetails.entity.MoviesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MoviesRepo extends JpaRepository<MoviesEntity, Long> {
    List<Optional<MoviesEntity>> findByMovieNameIgnoreCase(String movieName);
    void deleteByMovieNameIgnoreCase(String movieName);
}
