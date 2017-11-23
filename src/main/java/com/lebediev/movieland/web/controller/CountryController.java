package com.lebediev.movieland.web.controller;


import com.lebediev.movieland.service.CountryService;
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
@RequestMapping(value = "/country", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CountryController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private CountryService countryService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getAll() {
        log.info("Start getting json all countries from controller (v1/country)");
        long startTime = System.currentTimeMillis();
        String countryList = toJson(countryService.getAll());
        log.info("Finish getting json all countries from controller (v1/country). It took {} ms", System.currentTimeMillis() - startTime);
        return countryList;
    }
}
