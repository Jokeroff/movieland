package com.lebediev.movieland.dao.jdbc.impl;

import com.lebediev.movieland.dao.GenreDAO;
import com.lebediev.movieland.dao.jdbc.mapper.MovieToGenreRowMapper;
import com.lebediev.movieland.entity.Genre;
import com.lebediev.movieland.entity.MovieToGenre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GenreDAOJdbc implements GenreDAO{
    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MovieToGenreRowMapper movieToGenreRowMapper;

    public List<MovieToGenre> getMovieToGenreMappings(){
        log.info("Start getting MovieToGenre mappings ");
        long startTime = System.currentTimeMillis();
        String query = "SELECT genreId, genreName, movieId FROM genre \n" +
                       "JOIN movie2genre USING (genreId)";
        log.info("Finish getting MovieToGenre mappings. It took {} ms", System.currentTimeMillis() - startTime);
        return jdbcTemplate.query(query, movieToGenreRowMapper);
    }

    public List<Genre> getGenresByMovieId(List<MovieToGenre> movieToGenreList, int movieId){
        List<Genre> genreList = new ArrayList <>();

        for (MovieToGenre movieToGenre : movieToGenreList){
            if (movieToGenre.getMovieId() == movieId){
                genreList.add(new Genre(movieToGenre.getGenreId(), movieToGenre.getGenreName()));
            }
        }
        return genreList;
    }
 }
