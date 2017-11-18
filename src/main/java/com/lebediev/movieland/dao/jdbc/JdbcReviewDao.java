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
    @Value("${query.getReviewById}")
    private String queryGetReviewById;
    private static final ReviewRowMapper reviewRowMapper = new ReviewRowMapper();
    @Autowired
    private ReviewEnrichmentService reviewEnrichmentService;


    @Override
    public List <Review> getReviewById(int id) {
        log.info("Start getting review by id = {}", id);
        long startTime = System.currentTimeMillis();
        List <Review> reviewList = jdbcTemplate.query(queryGetReviewById, reviewRowMapper, id);
        reviewEnrichmentService.enrichReviewByUser(reviewList);
        log.info("Finish getting review by id = {}. It took {} ms", id, System.currentTimeMillis() - startTime);
        return reviewList;

    }

}
