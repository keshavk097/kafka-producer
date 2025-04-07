package com.demokafka.kafka_producer.repository;


import com.demokafka.kafka_producer.model.StandingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StandingOrderRepository extends JpaRepository<StandingOrder, Integer> {
}