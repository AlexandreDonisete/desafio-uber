package com.desafio.uber.service;

import com.desafio.uber.model.MovieLocation;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Service
public class MovieLocationService {

    private final WebClient webClient;

    public MovieLocationService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://data.sfgov.org/resource/yitu-d5am.json").build();
    }

    public List<MovieLocation> getAllMovies() {
        return webClient.get().retrieve().bodyToFlux(MovieLocation.class).collectList().block();
    }

    public List<MovieLocation> filterByTitle(String query) {
        return getAllMovies().stream()
                .filter(movie -> movie.getTitle() != null && movie.getTitle().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }

    public List<String> autocomplete(String prefix) {
        return getAllMovies().stream()
                .map(MovieLocation::getTitle)
                .filter(Objects::nonNull)
                .filter(title -> title.toLowerCase().startsWith(prefix.toLowerCase()))
                .distinct()
                .sorted()
                .limit(10)
                .toList();
    }

}
