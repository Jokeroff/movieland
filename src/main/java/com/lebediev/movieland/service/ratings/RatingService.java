package com.lebediev.movieland.service.ratings;

import com.lebediev.movieland.dao.jdbc.entity.MovieRating;
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.locks.ReentrantLock;


@Service
public class RatingService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MovieService movieService;

    private volatile List<MovieRating> movieRatingCache = new ArrayList<>();
    private Set<MovieRating> movieRatingsPrepared = new HashSet<>();

    public void addRating(MovieRating movieRating) {
        log.info("Start adding movie rating to queue");

        movieRatingsPrepared.add(movieRating);

        log.info("Finish adding movie rating to queue");
    }

    public double getRating(int movieId) {
        log.info("Start calculating rating for movie = {}", movieId);

        DoubleAdder ratingSum = new DoubleAdder();
        AtomicInteger count = new AtomicInteger();

        for (MovieRating movieRating : movieRatingsPrepared) {
            if (movieRating.getMovieId() == movieId) {
                ratingSum.add(movieRating.getRating());
                count.incrementAndGet();
            }
        }

        Optional<MovieRating> movieRating = movieRatingCache.stream().filter(n -> n.getMovieId() == movieId).findAny();

        movieRating.ifPresent(movieRating1 -> {
            ratingSum.add(movieRating1.getRating());
            count.addAndGet(movieRating1.getVoteCount());
        });

        BigDecimal ratingRounded = new BigDecimal(Double.toString(ratingSum.sum() / count.get()));
        ratingRounded = ratingRounded.setScale(2, RoundingMode.HALF_UP);

        log.info("Finish calculating rating for movie = {}", movieId);
        return ratingRounded.doubleValue();
    }

    @PostConstruct
    private void invalidateCache() {
        log.info("Start invalidating ratings cache");
        long startTime = System.currentTimeMillis();
        ReentrantLock lock = new ReentrantLock();

        try {
            lock.lock();
            movieRatingCache = movieService.getRatings();
        } finally {
            lock.unlock();
        }

        log.info("Finish invalidating ratings cache. It took {} ms", System.currentTimeMillis() - startTime);
    }

    @Scheduled(initialDelayString = "${movieRatingCache.fixedRate.in.milliseconds}", fixedRateString = "${movieRatingCache.fixedRate.in.milliseconds}")
    private void addRatings() {
        log.info("Start adding ratings from cache");
        long startTime = System.currentTimeMillis();

        ReentrantLock lock = new ReentrantLock();
        try {
            lock.lock();
            if (!movieRatingsPrepared.isEmpty()) {
                movieService.addRatings(new ArrayList<>(movieRatingsPrepared));
                movieRatingsPrepared.clear();
            }
        } finally {
            lock.unlock();
        }

        invalidateCache();

        log.info("Finish adding ratings from cache. It took {} ms", System.currentTimeMillis() - startTime);
    }
}
