package com.cinemaDetails.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MoviesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "movie_name", nullable = false)
    private String movieName;
    @Column(name="result", nullable = false)
    private String result;
    @ManyToOne
    @JoinColumn(name = "director_name", referencedColumnName ="name", nullable = false)
    private Director director;
}
