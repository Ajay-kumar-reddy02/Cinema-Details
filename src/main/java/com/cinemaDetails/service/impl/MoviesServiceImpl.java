package com.cinemaDetails.service.impl;

import com.cinemaDetails.entity.Director;
import com.cinemaDetails.entity.MoviesEntity;
import com.cinemaDetails.exception.DetailsNotFoundException;
import com.cinemaDetails.payload.MovieDTO4Movies;
import com.cinemaDetails.payload.MoviesDTO;
import com.cinemaDetails.repository.DirectorRepo;
import com.cinemaDetails.repository.MoviesRepo;
import com.cinemaDetails.service.MoviesService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class MoviesServiceImpl implements MoviesService {

    private final ModelMapper modelMapper;
    private final MoviesRepo moviesRepo;
    private final DirectorRepo directorRepo;

    public MoviesServiceImpl(ModelMapper modelMapper, MoviesRepo moviesRepo, DirectorRepo directorRepo) {
        this.modelMapper = modelMapper;
        this.moviesRepo = moviesRepo;
        this.directorRepo = directorRepo;
    }

    //To Add the movie details, first need to check the director existence and then update
    @Override
    public String addMovieDetails(MoviesDTO moviesDTO) {
        String directorName = moviesDTO.getDirectorName();
        log.info("The director name is {}", directorName);
        if (directorRepo.findByNameIgnoreCase(directorName).isPresent()){
            Director director = directorRepo.findByNameIgnoreCase(directorName).get();
            MoviesEntity moviesEntity = DTOtoEntity(moviesDTO, director);
            moviesRepo.save(moviesEntity);
            return "Movie Added Successfully";
        }else{
            throw new DetailsNotFoundException("Director", "name", directorName);
        }
    }

    //To update the movie details, first need to check the director and movie existence and then update
    @Override
    public String updateMovieDetails(MoviesDTO moviesDTO) {
        List<Optional<MoviesEntity>> movieCheck = moviesRepo.findByMovieNameIgnoreCase(moviesDTO.getMovieName());
        Optional<Director> directorCheck = directorRepo.findByNameIgnoreCase(moviesDTO.getDirectorName());
        if((!movieCheck.isEmpty()) && directorCheck.isPresent()){
            //Here I have to do loop to match the director and movie and then i have to update -- Real Scenario
            movieCheck.get(0).get().setMovieName(moviesDTO.getMovieName());
            movieCheck.get(0).get().setResult(moviesDTO.getResult());
            movieCheck.get(0).get().setDirector(directorCheck.get());
            moviesRepo.save(movieCheck.get(0).get());
            return "Movie Details Updated Successfully";
        }else{
            throw new DetailsNotFoundException("Director or Movie", "name", moviesDTO.getDirectorName() + " "
            + moviesDTO.getMovieName());
        }
    }

    //To delete the movie details, first need to check the movie existence and then delete
    @Override
    public String deleteMovieDetails(String name) {
        List<Optional<MoviesEntity>> movieCheck = moviesRepo.findByMovieNameIgnoreCase(name);
        if(!movieCheck.isEmpty()){
            moviesRepo.deleteByMovieNameIgnoreCase(name);
            return "Movie Deleted Successfully";
        }else{
            throw new DetailsNotFoundException("Movie", "name", name);
        }
    }

    //To get the movie details by movie name
    @Override
    public List<MovieDTO4Movies> getMovie(String name) {
        List<Optional<MoviesEntity>> movies = moviesRepo.findByMovieNameIgnoreCase(name);
        if(!movies.isEmpty()) {
            return movies.stream().map((movie) -> EntityToDTO(movie.get())).collect(Collectors.toList());
        }else{
            throw new DetailsNotFoundException("Movie", "name", name);
        }
    }

    //To convert DTO TO ENTITY
    private MoviesEntity DTOtoEntity(MoviesDTO moviesDTO, Director director){
        MoviesEntity moviesEntity = new MoviesEntity();
        moviesEntity.setMovieName(moviesDTO.getMovieName());
        moviesEntity.setResult(moviesDTO.getResult());
        moviesEntity.setDirector(director);
        return moviesEntity;
    }

    private MovieDTO4Movies EntityToDTO(MoviesEntity moviesEntity){
        return modelMapper.map(moviesEntity, MovieDTO4Movies.class);
    }


}
