package com.lebediev.movieland.service.conversion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.Security;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CachedCurrency {
    private final static Logger LOG = LoggerFactory.getLogger(CachedCurrency.class);
    private volatile List<CurrencyEntity> currencyList;
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    @Value("${currency.url}")
    private String url; //= "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";

    public CurrencyEntity getCurrencyEntity(Currency currency){
        LOG.info("Start getting currency from cache");
        long startTime = System.currentTimeMillis();
        List<CurrencyEntity> currencyEntityListCached = new ArrayList<>(currencyList);
        for(CurrencyEntity currencyEntity : currencyEntityListCached){
            if(currencyEntity.getCurrency() == currency){
                LOG.info("Finish getting currency from cache. It took {} ms", System.currentTimeMillis() - startTime);
                return currencyEntity;
            }
        }
        throw new RuntimeException("Could not get cached currency " + currency);
    }


    public static List<CurrencyEntity> getCurrenciesFromUrl(String url){
        LOG.info("Start getting currency list from url = {}", url);
        long startTime = System.currentTimeMillis();
        ObjectMapper objectMapper = new ObjectMapper();
        List<CurrencyEntity> currencyEntityList = new ArrayList<>();

        try{
            Security.insertProviderAt(new BouncyCastleProvider(), 1);

            URL urlReady = new URL(url);
            URLConnection connection = urlReady.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            JsonNode rootNode = objectMapper.readTree(reader);
            for (JsonNode node : rootNode) {
                String currencyName = node.get("cc").asText();
                if (Currency.isValid(currencyName)) {
                    LocalDate date = LocalDate.parse(node.get("exchangedate").asText(), FORMATTER);
                    CurrencyEntity currencyEntity = new CurrencyEntity(Currency.getCurrency(currencyName), node.get("rate").asDouble(), date);
                    currencyEntityList.add(currencyEntity);
                }
            }
            if(currencyEntityList.size() == 0){
                LOG.error("Could not get currency list from url: " + url);
                throw new RuntimeException("Nothing to return - list is empty");
            }
            reader.close();
            LOG.info("Finish getting currency list from url: {}. It took {} ms",url, System.currentTimeMillis() - startTime);
            return currencyEntityList;

        }  catch (IOException e) {
            throw new RuntimeException("Error in connection for url = " + url + " : " + e);
        }
    }

    public static boolean isExpired(CurrencyEntity currencyEntity){
        return  currencyEntity.getExchangeDate().isBefore(LocalDate.now());
   }

    @Scheduled(cron = "${currency.cache.cron.start.at}")
    @PostConstruct
    private void invalidateCache() {
        LOG.info("Start invalidating currency cache");
        long startTime = System.currentTimeMillis();
        currencyList = getCurrenciesFromUrl(url);
        LOG.info("Finish invalidating currency cache. It took {} ms", System.currentTimeMillis() - startTime);
    }
}
