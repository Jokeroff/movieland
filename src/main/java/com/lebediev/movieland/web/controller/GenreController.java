package com.lebediev.movieland.web.controller;

import com.lebediev.movieland.service.GenreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.lebediev.movieland.web.controller.utils.JsonConverter.toJson;


@Controller
@RequestMapping(value = "/genre", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GenreController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private GenreService genreService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getAllGenres() {
        log.info("Start getting Json AllGenres from controller (v1/genre)");
        long startTime = System.currentTimeMillis();
        String allGenres = toJson(genreService.getAllGenres());
        log.info("Finish getting Json AllGenres from controller (v1/genre). It took {} ms", System.currentTimeMillis() - startTime);
        return allGenres;
    }
}
