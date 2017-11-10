package com.lebediev.movieland.dao.cache;

import com.lebediev.movieland.dao.GenreDao;
import com.lebediev.movieland.dao.jdbc.JdbcGenreDao;
import com.lebediev.movieland.dao.jdbc.entity.MovieToGenre;
import com.lebediev.movieland.entity.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


@Service
@Primary
public class GenreCache implements GenreDao {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private volatile List <Genre> genreListCached;

    @Autowired
    private JdbcGenreDao jdbcGenreDao;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();


    @Override
    public List <MovieToGenre> getMovieToGenreMappings() {
        return null;
    }

    @Override
    public List <Genre> getAllGenres() {
        log.info("Genre cache. Start getting genres from cache");
        long startTime = System.currentTimeMillis();

        List <Genre> genreListCopy = genreListCached;

        log.info("Genre cache. Finish getting genres from cache. It took {} ms", System.currentTimeMillis() - startTime);
        return genreListCopy;
    }

    private void invalidateCache() {
        log.info("Genre cache. Start getting genres from DB");
        long startTime = System.currentTimeMillis();

        genreListCached = jdbcGenreDao.getAllGenres();
        log.info("Genre cache. Finish getting genres from DB. It took {} ms", System.currentTimeMillis() - startTime);
    }

    @PostConstruct
    private void scheduleCaching() {
        log.info("Genre cache. Start scheduling cache refresh");
        long startTime = System.currentTimeMillis();
        final Runnable initializer = () -> {
            invalidateCache();
        };
        final ScheduledFuture <?> scheduledFuture = scheduler.scheduleAtFixedRate(initializer, 0, 4, TimeUnit.HOURS);
        log.info("Genre cache. Finish scheduling cache refresh. It took {} ms", System.currentTimeMillis() - startTime);
    }


}


