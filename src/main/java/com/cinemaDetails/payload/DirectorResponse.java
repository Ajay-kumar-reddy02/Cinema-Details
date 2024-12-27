package com.cinemaDetails.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
//Helps to send the Director details with pagination
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DirectorResponse {
    private List<DirectorDTO> directorDTOList;
    private Integer pageNo;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private boolean isLast;
}
