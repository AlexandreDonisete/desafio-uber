package com.desafio.uber.controller;

import com.desafio.uber.model.MovieLocation;
import com.desafio.uber.service.MovieLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieLocationController {

    @Autowired
    private MovieLocationService service;

    @GetMapping
    public List<MovieLocation> getAll(@RequestParam Optional<String> title)  {
        return title.map(service::filterByTitle).orElseGet(service::getAllMovies);
    }

    @GetMapping("/autocomplete")
    public List<String> autocomplete(@RequestParam("q") String prefix) {
        return service.autocomplete(prefix);
    }
}
