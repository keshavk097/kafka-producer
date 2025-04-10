package com.demokafka.kafka_producer.controller;

import com.demokafka.kafka_producer.model.StandingOrder;
import com.demokafka.kafka_producer.repository.StandingOrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/standing-orders")
@CrossOrigin
public class StandingOrderController {

    private final StandingOrderRepository repository;

    public StandingOrderController(StandingOrderRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<StandingOrder> getAllStandingOrders() {
        return repository.findAll();
    }

    @PostMapping("/insert")
    public ResponseEntity<Map<String, Object>> insert(@RequestBody StandingOrder standingOrder) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Optional: Add basic validation
            if (standingOrder.getAccountNumber() == null || standingOrder.getAccountNumber().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Failed to create standing order. Account number is invalid.");
                return ResponseEntity.badRequest().body(response);
            }
            LocalDate today = LocalDate.now();
            if (standingOrder.getStartDate() != null && standingOrder.getEndDate() != null) {
                if (!today.isBefore(standingOrder.getStartDate()) && !today.isAfter(standingOrder.getEndDate())) {
                    standingOrder.setStatus("active");
                } else {
                    standingOrder.setStatus("inactive");
                }
            }
            LocalDate nextExecutionDate = switch (standingOrder.getFrequency().toLowerCase()) {
                case "daily" -> today.plusDays(1);
                case "weekly" -> today.plusWeeks(1);
                case "monthly" -> today.plusMonths(1);
                default -> today;
            };
            standingOrder.setNextExecutionDate(nextExecutionDate);
            // Save to DB
            StandingOrder saved = repository.save(standingOrder);

            response.put("status", "success");
            response.put("message", "Standing order created successfully.");
            response.put("id", "SO" + saved.getId()); // Example: prefixing ID
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to create standing order. " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
