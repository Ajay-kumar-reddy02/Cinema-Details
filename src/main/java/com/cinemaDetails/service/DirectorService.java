package com.cinemaDetails.service;

import com.cinemaDetails.payload.DirectorDTO;
import com.cinemaDetails.payload.DirectorResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DirectorService {
    DirectorDTO addDirectorToDB(DirectorDTO directorDTO);

    String updateDirectorToDB(@Valid DirectorDTO directorDTO, String name);

    String deleteDirectorFromDB(String name);

    DirectorResponse getAllDirectors(int pageNo, int pageSize);
}
