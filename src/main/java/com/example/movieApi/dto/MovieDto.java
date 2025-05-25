package com.example.movieApi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {

    private Integer movieId;

    @NotBlank(message = "Please provide movie's title")
    private String title;

    @NotBlank(message = "Please provide movie's director")
    private String director;

    @NotBlank(message = "Please provide movie's studio")
    private String studio;

    private Set<String> movieCast;

    @NotNull(message = "Please provide movie's release Year")
    private Integer releaseYear;

    @NotBlank(message = "Please provide movie's poster")
    private String poster;

    @NotBlank(message = "Please provide movie's poster url")
    private String posterUrl;


}
