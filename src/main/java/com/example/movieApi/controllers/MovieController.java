package com.example.movieApi.controllers;


import com.example.movieApi.dto.MovieDto;
import com.example.movieApi.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }


    @PostMapping("/add-movie")
    public ResponseEntity<MovieDto> addMovieHandler(@RequestPart MultipartFile file, @RequestPart String movieDto) throws IOException {


            MovieDto dto = convertToMovieDto(movieDto);


            return new ResponseEntity<>(movieService.addMovie(dto,file), HttpStatus.CREATED);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDto> getMovieHandler(@PathVariable Integer movieId){


        return  ResponseEntity.ok(movieService.getMovie(movieId));
    }


    @GetMapping("/all")
    public ResponseEntity<List<MovieDto>> getAllMovieHandler(){


        return ResponseEntity.ok(movieService.getAllMovies());
    }



    private MovieDto convertToMovieDto(String movieDto) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(movieDto,MovieDto.class);

    }
}
