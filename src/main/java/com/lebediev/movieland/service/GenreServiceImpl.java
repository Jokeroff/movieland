package com.lebediev.movieland.service;

import com.lebediev.movieland.dao.GenreDao;
import com.lebediev.movieland.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreDao genreDao;

    @Override
    public List<Genre> getAllGenres() {
        return genreDao.getAllGenres();
    }
}
