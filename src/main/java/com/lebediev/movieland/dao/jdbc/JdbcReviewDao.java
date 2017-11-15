package com.lebediev.movieland.dao.jdbc;

import com.lebediev.movieland.dao.ReviewDao;
import com.lebediev.movieland.dao.jdbc.mapper.ReviewRowMapper;
import com.lebediev.movieland.entity.Review;
import com.lebediev.movieland.service.enrich.ReviewEnrichmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcReviewDao implements ReviewDao {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Value("${query.getReviewByMovieId}")
    private String queryGetReviewByMovieId;
    private static final ReviewRowMapper reviewRowMapper = new ReviewRowMapper();
    @Autowired
    private ReviewEnrichmentService reviewEnrichmentService;


    @Override
    public List <Review> getReviewByMovieId(int movieId) {
        log.info("Start getting review by movieId = {}", movieId);
        long startTime = System.currentTimeMillis();
        List <Review> reviewList = jdbcTemplate.query(queryGetReviewByMovieId, reviewRowMapper, movieId);
        reviewEnrichmentService.enrichReviewByUser(reviewList);
        log.info("Finish getting review by movieId = {}. It took {} ms", movieId, System.currentTimeMillis() - startTime);
        return reviewList;

    }

}
