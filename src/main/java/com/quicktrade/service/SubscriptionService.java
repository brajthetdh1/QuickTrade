package com.quicktrade.service;

import com.quicktrade.dto.UserSubscription;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SubscriptionService {

    // Thread-safe storage for user subscriptions
    private final Map<String, UserSubscription> userSubscriptions = new ConcurrentHashMap<>();

    /** Add or update a subscription */
    public void addSubscription(UserSubscription subscription) {
        userSubscriptions.put(subscription.getUserId(), subscription);
    }

    /** Get subscription for a user */
    public UserSubscription getSubscription(String userId) {
        return userSubscriptions.get(userId);
    }

    /** Get all subscriptions */
    public Map<String, UserSubscription> getAllSubscriptions() {
        return userSubscriptions;
    }
}
