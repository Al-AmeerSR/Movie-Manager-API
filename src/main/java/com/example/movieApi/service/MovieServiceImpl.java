package com.example.movieApi.service;

import com.example.movieApi.dto.MovieDto;
import com.example.movieApi.entities.Movie;
import com.example.movieApi.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;

    private final FileService  fileService;

    @Value(("${project.poster}"))
    private String path ;

    @Value(("${base.url}"))
    private String baseUrl;

    public MovieServiceImpl(MovieRepository movieRepository,FileService  fileService) {
        this.movieRepository = movieRepository;
        this.fileService=fileService;
    }

    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {

       // 1.upload the file
        String uploadedFileName = fileService.uploadFile(path,file);

        //2.set the value of field 'poster' as filename
        movieDto.setPoster(uploadedFileName);

        //3.map Dto to movie object
        Movie movie = new Movie(movieDto.getMovieId(),
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster());

        //4.save the movie object
        Movie savedMovie = movieRepository.save(movie);

        //5.generate the poster url
        String posterUrl = baseUrl+"/file/"+uploadedFileName;

        //6.map movie object to dto object and return it


        return new MovieDto(savedMovie.getMovieId(),
                savedMovie.getTitle(),
                savedMovie.getDirector(),
                savedMovie.getStudio(),
                savedMovie.getMovieCast(),
                savedMovie.getReleaseYear(),
                savedMovie.getPoster(),
                posterUrl);
    }

    @Override
    public MovieDto getMovie(Integer movieId) {


        Movie movie = movieRepository.findById(movieId).orElseThrow(()->new RuntimeException("Movie not found"));
        String posterUrl = baseUrl+"/file/"+movie.getPoster();

        return new MovieDto(movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                posterUrl);
    }

    @Override
    public List<MovieDto> getAllMovies() {

    List<Movie> movies = movieRepository.findAll();

    List<MovieDto> movieDtos = new ArrayList<>();

    for(Movie movie : movies){

        String posterUrl = baseUrl+"/file/"+movie.getPoster();
        MovieDto movieDto = new MovieDto(movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                posterUrl);

        movieDtos.add(movieDto);


    }
        return movieDtos;
    }
}
