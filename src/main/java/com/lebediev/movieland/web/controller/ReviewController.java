package com.lebediev.movieland.web.controller;

import com.lebediev.movieland.dao.jdbc.entity.Role;
import com.lebediev.movieland.entity.Review;
import com.lebediev.movieland.entity.User;
import com.lebediev.movieland.service.ReviewService;
import com.lebediev.movieland.service.authentication.AuthService;
import com.lebediev.movieland.web.controller.utils.RoleRequired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.lebediev.movieland.web.controller.utils.JsonConverter.toReview;


@Controller
@RequestMapping(value = "/review", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ReviewController {
    private final static Logger LOG = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private AuthService authService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @RoleRequired(role = Role.USER)
    public Review add(@RequestBody String json) {
        LOG.info("Start saving review ");

        User user = authService.getUserThreadLocal();
        Review review = toReview(json);
        review.setUserId(user.getUserId());
        Review addedReview = reviewService.add(review);

        LOG.info("Finish saving review");
        return addedReview;
    }
}
