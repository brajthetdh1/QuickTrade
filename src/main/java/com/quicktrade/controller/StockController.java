package com.quicktrade.controller;

import com.quicktrade.dto.Stock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {

    @GetMapping("/stock/test")
    public Stock testStock() {
        return new Stock("AAPL", 120.5);
    }
}
