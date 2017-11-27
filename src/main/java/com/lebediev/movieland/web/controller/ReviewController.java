package com.lebediev.movieland.web.controller;

import com.lebediev.movieland.entity.Review;
import com.lebediev.movieland.service.ReviewService;
import com.lebediev.movieland.service.authentication.AuthService;
import com.lebediev.movieland.service.authentication.UserToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    public Review add(@RequestHeader String uuid, @RequestBody String json){
        LOG.info("Start saving review for /review for uuid: {}", uuid);
        UserToken userToken = authService.authorize(UUID.fromString(uuid));
        Review review;
        if( userToken.isUser()){
            review = toReview(json);
            review.setUserId(userToken.getUser().getUserId());
            review = reviewService.add(review);
        }
        else {
            LOG.info("Saving review failed. 'USER' role not assigned for user with uuid: {}. ", uuid);
            throw new SecurityException("You must have USER role to add review!");
        }
        LOG.info("Finish saving review for /review for uuid: {}", uuid);
        return review;
    }
}
