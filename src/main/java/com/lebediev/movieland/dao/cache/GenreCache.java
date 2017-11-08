package com.lebediev.movieland.dao.cache;

import com.lebediev.movieland.dao.GenreDao;
import com.lebediev.movieland.dao.jdbc.JdbcGenreDao;
import com.lebediev.movieland.dao.jdbc.entity.MovieToGenre;
import com.lebediev.movieland.entity.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class GenreCache implements GenreDao {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private List<Genre> genreListCached;
    private boolean firstInit = false;
    @Autowired
    JdbcGenreDao jdbcGenreDao;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    @Override
    public List<MovieToGenre> getMovieToGenreMappings() {
        return null;
    }

    @Override
    public List<Genre> getAllGenres() {
        log.info("Genre cache. Start getting genres from cache");
        long startTime = System.currentTimeMillis();
        if (!firstInit) {
            log.info("Genre cache. First init");
            initCache();
            scheduleCashing();
            firstInit = true;
        }
        log.info("Genre cache. Finish getting genres from cache. It took {} ms", System.currentTimeMillis() - startTime);
        return genreListCached;
    }

    private void initCache() {
        log.info("Genre cache. Start getting genres from DB");
        long startTime = System.currentTimeMillis();
        genreListCached = jdbcGenreDao.getAllGenres();
        log.info("Genre cache. Finish getting genres from DB. It took {} ms", System.currentTimeMillis() - startTime);
    }

    private void scheduleCashing() {
        log.info("Genre cache. Start scheduling cache refresh");
        long startTime = System.currentTimeMillis();
        final Runnable initializer = () -> {
            initCache();
        };
        final ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(initializer, 10, 10, TimeUnit.MINUTES);
        log.info("Genre cache. Finish scheduling cache refresh. It took {} ms", System.currentTimeMillis() - startTime);
    }
}
