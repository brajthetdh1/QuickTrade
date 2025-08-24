package com.quicktrade.dto;

import java.util.Set;

public class UserSubscription {
    private String userId;
    private Set<String> symbols;

    public UserSubscription() {}

    public UserSubscription(String userId, Set<String> symbols) {
        this.userId = userId;
        this.symbols = symbols;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public Set<String> getSymbols() { return symbols; }
    public void setSymbols(Set<String> symbols) { this.symbols = symbols; }
}
