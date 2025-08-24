# QuickTrade Backend

**QuickTrade** is a **low-latency real-time stock feed system** built with Spring Boot. It simulates multiple stock price updates, maintains history, and sends alerts via WebSocket. The backend is designed to be extendable for real market feeds and per-user subscriptions.

---

## **Features Implemented So Far**

* Simulated stock price updates for multiple symbols (`AAPL`, `GOOG`, `AMZN`, `MSFT`, `TSLA`)
* **History tracking**: last 20 prices per stock
* **Global threshold alerts** for stock prices
* **Per-user subscriptions** with targeted alerts
* Async processing for low-latency WebSocket updates
* DTO-based design using clean class names (`Stock`, `UserSubscription`)

---

## **Project Structure**

```
quicktrade/
├─ src/main/java/com/quicktrade/
│   ├─ QuickTradeApplication.java
│   ├─ config/WebSocketConfig.java
│   ├─ controller/
│   │   └─ StockController.java
│   ├─ dto/
│   │   ├─ Stock.java
│   │   └─ UserSubscription.java
│   └─ service/
│       ├─ StockService.java
│       ├─ AlertService.java
│       └─ SubscriptionService.java
└─ pom.xml
```

---

## **Setup Instructions**

### 1. Prerequisites

* Java 17+
* Maven 3.6+
* (Optional) IDE: IntelliJ IDEA, Eclipse

### 2. Build and Run

```bash
# Clone repo
git clone <repo-url>
cd quicktrade

# Build project
mvn clean install

# Run backend
mvn spring-boot:run
```

### 3. WebSocket Endpoints

* **Stock Updates:** `/topic/stocks`
* **Global Alerts:** `/topic/alerts`
* **Per-user Alerts:** `/topic/alerts/{userId}`

Clients can connect using **STOMP over WebSocket**:

```text
ws://localhost:8080/ws-stock
```

---

### 4. REST Endpoints

* **Get stock history:**
  `GET /stock/history/{symbol}` → returns last 20 prices

* **Subscribe user to stocks:**
  `POST /user/subscribe`
  **Body:**

  ```json
  {
      "userId": "user1",
      "symbols": ["AAPL", "TSLA"]
  }
  ```

---

## **Next Steps / Planned Enhancements**

* Integrate **Kafka or Redis Streams** for real stock data
* Add **dynamic per-user thresholds**
* Add **metrics and monitoring** (Prometheus + Grafana)
* Implement **WebFlux reactive backend** for ultra-low latency
* Dockerize for deployment

---

## **Technologies Used**

* Java 17
* Spring Boot 3
* Spring WebSocket (STOMP + SockJS)
* Maven

---

## **Contact / Author**

* Author: Braj Bhushan kumar
* GitHub: [https://github.com/yourusername/quicktrade](https://github.com/brajthetdh1/QuickTrade)
