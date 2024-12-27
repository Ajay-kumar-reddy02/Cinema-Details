package com.cinemaDetails.service.impl;

import com.cinemaDetails.entity.Director;
import com.cinemaDetails.exception.DetailsAlreadyExists;
import com.cinemaDetails.exception.DetailsNotFoundException;
import com.cinemaDetails.payload.DirectorDTO;
import com.cinemaDetails.payload.DirectorResponse;
import com.cinemaDetails.repository.DirectorRepo;
import com.cinemaDetails.service.DirectorService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@Transactional
public class DirectorServiceImpl implements DirectorService {
    private ModelMapper modelMapper;
    private DirectorRepo directorRepo;
    public DirectorServiceImpl(DirectorRepo directorRepo, ModelMapper modelMapper){
        this.directorRepo = directorRepo;
        this.modelMapper = modelMapper;
    }

    //To add the director to Director Table in DB
    @Override
    public DirectorDTO addDirectorToDB(DirectorDTO directorDTO) {
        Director director = dtoToEntity(directorDTO);
        if(directorRepo.findByNameIgnoreCase(director.getName()).isPresent()){
            throw new DetailsAlreadyExists("This Director Details already exists");
        }
        Director directorFromDB = directorRepo.save(director);
        return entityToDto(directorFromDB);
    }

    //To update the director details in Director Table in DB
    //First need to check the director exists or not and then update
    @Override
    public String updateDirectorToDB(DirectorDTO directorDTO, String name) {
        if(directorRepo.findByNameIgnoreCase(name).isPresent()){
            Director director = directorRepo.findByNameIgnoreCase(name).get();
            director.setName(directorDTO.getName());
            directorRepo.save(director);
            return "Director Details Updated Successfully";
        }else{
            throw new DetailsNotFoundException("Director", "name", name);
        }
    }

    //To delete the director details from Director Table in DB
    //First need to check the director exists or not and then delete
    @Override
    public String deleteDirectorFromDB(String name) {
        if(directorRepo.findByNameIgnoreCase(name).isPresent()){
            directorRepo.deleteByNameIgnoreCase(name);
            return "Director Details Deleted Successfully";
        }else{
            throw new DetailsNotFoundException("Director", "name", name);
        }
    }

    //To Send all Directors details to client with pagination
    @Override
    public DirectorResponse getAllDirectors(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Director> directors = directorRepo.findAll(pageable);
        List<Director> content = directors.getContent();
        List<DirectorDTO> collect = content.stream().map((director) -> {return entityToDto(director);})
                                                    .collect(Collectors.toList());
        DirectorResponse directorResponse = new DirectorResponse();
        directorResponse.setDirectorDTOList(collect);
        directorResponse.setPageNo(directors.getNumber());
        directorResponse.setPageSize(directors.getSize());
        directorResponse.setTotalPages(directors.getTotalPages());
        directorResponse.setTotalElements(directors.getTotalElements());
        directorResponse.setLast(directors.isLast());
        return directorResponse;
    }


    //ModelMapper Methods
    private Director dtoToEntity(DirectorDTO directorDTO){
        return modelMapper.map(directorDTO, Director.class);
    }
    private DirectorDTO entityToDto(Director director){
        return modelMapper.map(director, DirectorDTO.class);
    }
}
