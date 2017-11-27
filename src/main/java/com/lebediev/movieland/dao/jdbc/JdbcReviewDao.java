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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class JdbcReviewDao implements ReviewDao {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final ReviewRowMapper REVIEW_ROW_MAPPER = new ReviewRowMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Value("${query.getReviewById}")
    private String queryGetReviewById;
    @Autowired
    private ReviewEnrichmentService reviewEnrichmentService;
    @Value("${query.addReview}")
    private String queryAdd;


    @Override
    public List <Review> getReviewById(int id) {
        log.info("Start getting review by id = {}", id);
        long startTime = System.currentTimeMillis();
        List <Review> reviewList = jdbcTemplate.query(queryGetReviewById, REVIEW_ROW_MAPPER, id);
        reviewEnrichmentService.enrichReviewByUser(reviewList);
        log.info("Finish getting review by id = {}. It took {} ms", id, System.currentTimeMillis() - startTime);
        return reviewList;
    }

    @Override
    public Review add(Review review) {
        log.info("Start save review into db: {}", review);
        long startTime = System.currentTimeMillis();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(queryAdd, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, review.getMovieId());
            statement.setInt(2, review.getUserId());
            statement.setString(3, review.getText());
            return statement;
        }, keyHolder);
        review.setId(keyHolder.getKey().intValue());
        log.info("Finish save review into db: {}. It took {} ms", review, System.currentTimeMillis() - startTime);
        return review;
    }
}
