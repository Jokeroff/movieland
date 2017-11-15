package com.lebediev.movieland.service.conversion;

import java.time.LocalDate;

public class CurrencyEntity {
    private Currency currency;
    private double rate;
    private LocalDate exchangeDate;

    public CurrencyEntity(Currency currency, double rate, LocalDate exchangeDate) {
        this.currency = currency;
        this.rate = rate;
        this.exchangeDate = exchangeDate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getRate() {
        return rate;
    }

    public LocalDate getExchangeDate() {
        return exchangeDate;
    }

    @Override
    public String toString() {
        return "CurrencyEntity{" +
                "currency=" + currency +
                ", rate=" + rate +
                ", exchangeDate=" + exchangeDate +
                '}';
    }
}
