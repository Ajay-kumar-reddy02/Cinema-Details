package com.cinemaDetails.controller;

import com.cinemaDetails.payload.DirectorDTO;
import com.cinemaDetails.payload.DirectorResponse;
import com.cinemaDetails.service.DirectorService;
import com.cinemaDetails.utils.ApplicationConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/director")
public class DirectorController {
    private DirectorService directorService;

    public DirectorController(DirectorService directorService){
        this.directorService = directorService;
    }

    //To add the Director Details to Director Table in DB
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<DirectorDTO> addDirector(@Valid @RequestBody DirectorDTO directorDTO){
        DirectorDTO directordto =directorService.addDirectorToDB(directorDTO);
        return new ResponseEntity<>(directordto, HttpStatus.CREATED);
    }

    //To update the Director details in Director Table in DB
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{name}")
    public ResponseEntity<String> updateDirector(@Valid @RequestBody DirectorDTO directorDTO,
                                                 @PathVariable String name){
        String message = directorService.updateDirectorToDB(directorDTO, name);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //To delete the Director details from the Director Table in DB
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{name}")
    public ResponseEntity<String> deleteDirector(@PathVariable String name){
        String message = directorService.deleteDirectorFromDB(name);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //To get all Director details from DB
    @GetMapping("/all")
    public ResponseEntity<DirectorResponse> getAllDirectors(
            @RequestParam(name="pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(name="pageSize", defaultValue=ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize){
        DirectorResponse directorResponse = directorService.getAllDirectors(pageNo, pageSize);
        return new ResponseEntity<>(directorResponse, HttpStatus.OK);
    }
}
