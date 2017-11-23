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
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.Security;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CachedCurrency {
    private final static Logger LOG = LoggerFactory.getLogger(CachedCurrency.class);
    private volatile List <CurrencyEntity> currencyList;
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Value("${currency.url}")
    private String url;

    public CurrencyEntity getCurrencyEntity(Currency currency) {
        LOG.info("Start getting currency from cache");
        List <CurrencyEntity> currencyEntityListCached = new ArrayList <>(currencyList);
        for (CurrencyEntity currencyEntity : currencyEntityListCached) {
            if (currencyEntity.getCurrency() == currency) {
                LOG.info("Finish getting currency from cache");
                return currencyEntity;
            }
        }
        throw new RuntimeException("Could not get cached currency " + currency);
    }


    private static List <CurrencyEntity> getCurrenciesFromUrl(String url) {
        LOG.info("Start getting currency list from url = {}", url);
        long startTime = System.currentTimeMillis();
        List <CurrencyEntity> currencyEntityList = new ArrayList <>();
        try {
            URL urlReady = new URL(url);
            URLConnection connection = urlReady.openConnection();
            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            try {
                JsonNode rootNode = OBJECT_MAPPER.readTree(reader);
                for (JsonNode node : rootNode) {
                    String currencyName = node.get("cc").asText();
                    if (Currency.isValid(currencyName)) {
                        LocalDate date = LocalDate.parse(node.get("exchangedate").asText(), FORMATTER);
                        CurrencyEntity currencyEntity = new CurrencyEntity(Currency.getCurrency(currencyName), node.get("rate").asDouble(), date);
                        currencyEntityList.add(currencyEntity);
                    }
                }
                if (currencyEntityList.size() == 0) {
                    LOG.error("Could not get currency list from url: " + url);
                    throw new RuntimeException("Nothing to return - list is empty");
                }
                LOG.info("Finish getting currency list from url: {}. It took {} ms", url, System.currentTimeMillis() - startTime);
                return currencyEntityList;

            } finally {
                reader.close();
            }
        } catch (IOException e) {
            LOG.warn("Getting currency list from url: {} failed. Dummy currency list returned", url);
            return  getDummyCurrencyList();
        }
    }

    public static boolean isExpired(CurrencyEntity currencyEntity) {
        return currencyEntity.getExchangeDate().isBefore(LocalDate.now());
    }

    private static List<CurrencyEntity> getDummyCurrencyList(){
        CurrencyEntity dummyUsd = new CurrencyEntity(Currency.USD, 1, LocalDate.of(1900,1,1));
        CurrencyEntity dummyEur = new CurrencyEntity(Currency.EUR, 1, LocalDate.of(1900,1,1));
         return Arrays.asList(dummyUsd, dummyEur);
    }

    @Scheduled(cron = "${currency.cache.cron.start.at}")
    @PostConstruct
    private void invalidateCache() {
        LOG.info("Start invalidating currency cache");
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
        long startTime = System.currentTimeMillis();
        currencyList = getCurrenciesFromUrl(url);
        LOG.info("Finish invalidating currency cache. It took {} ms", System.currentTimeMillis() - startTime);
    }
}
