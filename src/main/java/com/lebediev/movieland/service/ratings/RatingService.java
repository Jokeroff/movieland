package com.lebediev.movieland.service.ratings;

import com.lebediev.movieland.dao.jdbc.entity.MovieRating;
import com.lebediev.movieland.entity.Movie;
import com.lebediev.movieland.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;


@Service
public class RatingService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MovieService movieService;

    private List<MovieRating> movieRatingCache = new ArrayList<>();
    private Queue<MovieRating> movieRatingsPrepared = new ConcurrentLinkedQueue<>();
    private ReentrantLock lock = new ReentrantLock();

    public void addRating(MovieRating movieRating) {
        log.info("Start adding movie rating to queue");

        try {
            lock.lock();
            movieRatingsPrepared.add(movieRating);
        } finally {
            log.info("Finish adding movie rating to queue");
            lock.unlock();
        }
    }

    public double getRating(int movieId) {
        log.info("Start calculating rating for movie = {}", movieId);

        double ratingSum = 0;
        int count = 0;

        for (MovieRating movieRating : movieRatingsPrepared) {
            if (movieRating.getMovieId() == movieId) {
                ratingSum += movieRating.getRating();
                count++;
            }
        }

        Optional<MovieRating> currentRating = movieRatingCache.stream().filter(n -> n.getMovieId() == movieId).findAny();

        if (currentRating.isPresent()) {
            ratingSum += currentRating.get().getRating();
            count += currentRating.get().getVoteCount();
        }

        BigDecimal ratingRounded = new BigDecimal(0);
        if (count != 0) {
            ratingRounded = new BigDecimal(ratingSum / count);
            ratingRounded = ratingRounded.setScale(2, RoundingMode.HALF_UP);
        }

        log.info("Finish calculating rating for movie = {}", movieId);
        return ratingRounded.doubleValue();
    }

    @PostConstruct
    private void invalidateCache() {
        log.info("Start invalidating ratings cache");
        long startTime = System.currentTimeMillis();

        movieRatingCache = movieService.getRatings();

        log.info("Finish invalidating ratings cache. It took {} ms", System.currentTimeMillis() - startTime);
    }

    @Scheduled(initialDelayString = "${movieRatingCache.fixedRate.in.milliseconds}", fixedRateString = "${movieRatingCache.fixedRate.in.milliseconds}")
    private void addRatings() {
        log.info("Start adding ratings from cache");
        long startTime = System.currentTimeMillis();

        try {
            lock.lock();
            if (!movieRatingsPrepared.isEmpty()) {
                movieService.addRatings(new ArrayList<>(movieRatingsPrepared));
                movieRatingsPrepared.clear();

                invalidateCache();
            }
        } finally {
            log.info("Finish adding ratings from cache. It took {} ms", System.currentTimeMillis() - startTime);
            lock.unlock();
        }
    }

    public void enrichByRatings(Movie movie) {
        log.info("Start enriching movie by ratings ");
        long startTime = System.currentTimeMillis();

        movie.setRating(getRating(movie.getId()));

        log.info("Finish enriching movie by ratings. It took {} ms", System.currentTimeMillis() - startTime);
    }

    public void enrichByRatings(List<Movie> movieList) {
        log.info("Start enriching movies by ratings ");
        long startTime = System.currentTimeMillis();

        for (Movie movie : movieList) {
            movie.setRating(getRating(movie.getId()));
        }
        log.info("Finish enriching movies by ratings. It took {} ms", System.currentTimeMillis() - startTime);
    }
}
