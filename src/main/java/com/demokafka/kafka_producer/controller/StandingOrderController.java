package com.demokafka.kafka_producer.controller;

import com.demokafka.kafka_producer.model.StandingOrder;
import com.demokafka.kafka_producer.repository.StandingOrderRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/standing-orders")
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
	public String insert(@RequestBody StandingOrder standingOrder) {
		repository.save(standingOrder);
		return "Message sent to Database";
	}
}
