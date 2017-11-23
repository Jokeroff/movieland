package com.lebediev.movieland.service;

import com.lebediev.movieland.dao.CountryDao;
import com.lebediev.movieland.entity.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    @Autowired
    private CountryDao countryDao;

    @Override
    public List <Country> getAll() {
        return countryDao.getAll();
    }
}
