package com.lebediev.movieland.service;

import com.lebediev.movieland.entity.Genre;
import org.springframework.stereotype.Service;

import java.util.List;


public interface GenreService {
    List<Genre> getAllGenres();
}
