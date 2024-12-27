package com.cinemaDetails.repository;

import com.cinemaDetails.entity.Visitors;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VisitorsRepo extends JpaRepository<Visitors, Long> {
    Optional<Visitors> findByUsername(String username);
    boolean existsByUsernameIgnoreCase(String username);
}
