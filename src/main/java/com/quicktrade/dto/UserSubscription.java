package com.quicktrade.dto;

import java.util.Map;
import java.util.Set;

public class UserSubscription {
    private String userId;
    private Set<String> symbols;                  // Stocks user is interested in
    private Map<String, Double> thresholds;       // Per-stock thresholds

    public UserSubscription() {}

    public UserSubscription(String userId, Set<String> symbols, Map<String, Double> thresholds) {
        this.userId = userId;
        this.symbols = symbols;
        this.thresholds = thresholds;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public Set<String> getSymbols() { return symbols; }
    public void setSymbols(Set<String> symbols) { this.symbols = symbols; }

    public Map<String, Double> getThresholds() { return thresholds; }
    public void setThresholds(Map<String, Double> thresholds) { this.thresholds = thresholds; }
}
