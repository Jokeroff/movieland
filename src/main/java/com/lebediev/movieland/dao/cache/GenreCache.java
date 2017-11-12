package com.lebediev.movieland.dao.cache;

import com.lebediev.movieland.dao.GenreDao;
import com.lebediev.movieland.dao.jdbc.JdbcGenreDao;
import com.lebediev.movieland.dao.jdbc.entity.MovieToGenre;
import com.lebediev.movieland.entity.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
@Primary
public class GenreCache implements GenreDao {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private volatile List <Genre> genreListCached;

    @Autowired
    private JdbcGenreDao jdbcGenreDao;

    @Override
    public List <MovieToGenre> getMovieToGenreMappings() {
        return jdbcGenreDao.getMovieToGenreMappings();
    }

    @Override
    public List <Genre> getAllGenres() {
        log.info("Genre cache. Start getting genres from cache");
        long startTime = System.currentTimeMillis();
        if(genreListCached == null){
            invalidateCache();
        }
        List <Genre> genreListCopy = new ArrayList<>(genreListCached);
        log.info("Genre cache. Finish getting genres from cache. It took {} ms", System.currentTimeMillis() - startTime);
        return genreListCopy;
    }

    @Scheduled(fixedRateString="${genreCache.fixedRate.in.milliseconds}")
    private void invalidateCache() {
        log.info("Genre cache. Start getting genres from DB");
        long startTime = System.currentTimeMillis();
        genreListCached = jdbcGenreDao.getAllGenres();
        log.info("Genre cache. Finish getting genres from DB. It took {} ms", System.currentTimeMillis() - startTime);
    }

}


