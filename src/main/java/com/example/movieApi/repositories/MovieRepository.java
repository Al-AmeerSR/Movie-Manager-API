package com.example.movieApi.repositories;

import com.example.movieApi.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieRepository extends JpaRepository <Movie,Integer>{



}
