package com.quicktrade.service;

import com.quicktrade.dto.Stock;
import com.quicktrade.dto.UserSubscription;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class StockService {

    private final SimpMessagingTemplate messagingTemplate;

    // Simulation
    private final Random random = new Random();
    private final String[] symbols = {"AAPL", "GOOG", "AMZN", "MSFT", "TSLA"};

    // History storage
    private final Map<String, Deque<Stock>> stockHistory = new HashMap<>();
    private final int HISTORY_LIMIT = 20;

    // Thresholds per stock
    private final Map<String, Double> symbolThresholds = Map.of(
            "AAPL", 140.0,
            "GOOG", 2800.0,
            "AMZN", 3500.0,
            "MSFT", 300.0,
            "TSLA", 800.0
    );

    // Per-user subscriptions
    private final Map<String, UserSubscription> userSubscriptions = new HashMap<>();

    // Executor for async simulation
    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    public StockService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        startStockSimulation();
    }

    /** Add or update a user's subscription */
    public void addSubscription(UserSubscription subscription) {
        userSubscriptions.put(subscription.getUserId(), subscription);
    }

    /** Get history of a stock */
    public List<Stock> getHistory(String symbol) {
        return new ArrayList<>(stockHistory.getOrDefault(symbol, new LinkedList<>()));
    }

    /** Simulate stock price updates periodically */
    private void startStockSimulation() {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            for (String symbol : symbols) {
                executor.submit(() -> simulateStock(symbol));
            }
        }, 0, 500, TimeUnit.MILLISECONDS);
    }

    /** Simulate one stock update */
    private void simulateStock(String symbol) {
        double price = 100 + random.nextDouble() * 50;
        Stock stock = new Stock(symbol, price);

        // Send to all clients
        messagingTemplate.convertAndSend("/topic/stocks", stock);

        // Record history
        recordHistory(stock);

        // Check threshold alerts
        double threshold = symbolThresholds.get(symbol);
        if (price > threshold) {
            messagingTemplate.convertAndSend("/topic/alerts",
                    "ALERT: " + symbol + " crossed threshold " + threshold + ": " + price);
        }

        // Send per-user alerts
        userSubscriptions.forEach((userId, sub) -> {
            if (sub.getSymbols().contains(symbol)) {
                messagingTemplate.convertAndSend("/topic/alerts/" + userId,
                        "ALERT for " + userId + ": " + symbol + " -> " + price);
            }
        });
    }

    /** Record latest N prices per stock */
    private void recordHistory(Stock stock) {
        stockHistory.putIfAbsent(stock.getSymbol(), new LinkedList<>());
        Deque<Stock> queue = stockHistory.get(stock.getSymbol());
        if (queue.size() >= HISTORY_LIMIT) queue.pollFirst();
        queue.addLast(stock);
    }
}
