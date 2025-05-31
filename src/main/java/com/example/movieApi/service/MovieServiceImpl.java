package com.example.movieApi.service;

import com.example.movieApi.dto.MovieDto;
import com.example.movieApi.entities.Movie;
import com.example.movieApi.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

       if( Files.exists(Paths.get(path+ File.separator+file.getOriginalFilename()))){

           throw new RuntimeException("File with same name already exists! rename the file!");

       }
        String uploadedFileName = fileService.uploadFile(path,file);

        //2.set the value of field 'poster' as filename
        movieDto.setPoster(uploadedFileName);

        //3.map Dto to movie object
        Movie movie = new Movie(null,
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

    @Override
    public MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException {

//        1.check if movie exists
        Movie mv = movieRepository.findById(movieId).orElseThrow(()->new RuntimeException("Movie not found"));

//        2.check if  file is null if null do nothing ,if file is not null delete the existing file and add the new file and update the record
        String fileName = mv.getPoster();
        if(file!=null){

        Files.deleteIfExists(Paths.get(path+File.separator+fileName));
        fileName = fileService.uploadFile(path,file);

        }

//        3.set moviedtos poster value according to step 2
            movieDto.setPoster(fileName);

//        4.map it to movie object

        Movie movie = new Movie(mv.getMovieId(),
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
                );

//        5.save the object
       Movie updatedMovie = movieRepository.save(movie);

//        6.generate poster url
        String posterUrl = baseUrl+"/file/"+fileName;

//        7.map to moviedto and return it

        MovieDto response = new MovieDto(movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                posterUrl);

        return response;
    }

    @Override
    public String deleteMovie(Integer movieId) throws IOException {

//        1.check if the movie object  exists

        Movie movie = movieRepository.findById(movieId).orElseThrow(()->new RuntimeException("Movie not found"));
       
//        2.delete the file associated with it

            Files.deleteIfExists(Paths.get(path+File.separator+movie.getPoster()));


//        3.delete the object
            movieRepository.delete(movie);
        return "Movie deleted with id "+movieId;
    }
}
