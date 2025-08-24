package com.quicktrade.controller;

import com.quicktrade.dto.UserSubscription;
import com.quicktrade.service.SubscriptionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final SubscriptionService subscriptionService;

    public UserController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/subscribe")
    public String subscribe(@RequestBody UserSubscription sub) {
        subscriptionService.addSubscription(sub);
        return "Subscribed successfully";
    }
}
