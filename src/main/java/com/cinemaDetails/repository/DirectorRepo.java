package com.cinemaDetails.repository;

import com.cinemaDetails.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DirectorRepo extends JpaRepository<Director, Long> {
    Optional<Director> findByNameIgnoreCase(String name);
    void deleteByNameIgnoreCase(String name);
}
