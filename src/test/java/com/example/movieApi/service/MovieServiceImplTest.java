package com.example.movieApi.service;

import com.example.movieApi.exceptions.MovieNotFoundException;
import com.example.movieApi.repositories.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private FileService fileService;

    @InjectMocks
    MovieServiceImpl movieService;



    @Test
    void test_MovieNotFound(){

        //Arrange
        when(movieRepository.findById(99)).thenReturn(Optional.empty());

        //Assert and Act

        Assertions.assertThrows(MovieNotFoundException.class,()->movieService.getMovie(99));
    }
}
