package com.example.movieApi.service;

import com.example.movieApi.dto.MovieDto;
import com.example.movieApi.dto.MoviePageResponse;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {


    MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException;

    MovieDto getMovie(Integer movieId);

    List<MovieDto> getAllMovies();

    @Modifying
    MovieDto updateMovie(Integer movieId,MovieDto movieDto,MultipartFile multipartFile) throws IOException;

    @Modifying
    String deleteMovie(Integer movieId) throws IOException;

    MoviePageResponse getAllMoviesWithPagination(Integer pageNumber,Integer pageSize);

    MoviePageResponse getAllMoviesWithPaginationAndSorting(Integer pageNumber,Integer pageSize,String sortBy,String direction);

}
