package com.lebediev.movieland.service.conversion;

public enum Currency {
    USD("USD"),
    EUR("EUR");

    private final String name;

    Currency(String name){
        this.name = name;
    }

    public static Currency getCurrency(String name){
        for(Currency currency : Currency.values()){
            if (currency.name.equalsIgnoreCase(name)){
                return currency;
            }
        }
        throw new IllegalArgumentException("Wrong currency: " + name);
    }
    public static boolean isValid(String name){
        for(Currency currency : Currency.values()){
            if(currency.name.equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }
}
