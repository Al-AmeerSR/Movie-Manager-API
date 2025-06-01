package com.example.movieApi.dto;

import java.util.List;

public record MoviePageResponse(List<MovieDto> movieDto,
                               Integer pageNumber,
                               Integer pageSize,
                               long totalElements,
                               long totalPages,
                               Boolean isLast) {

}
