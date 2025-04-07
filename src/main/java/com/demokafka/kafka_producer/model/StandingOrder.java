package com.demokafka.kafka_producer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "standing_order")
@Getter
@Setter
public class StandingOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer customerId;

    private String accountNumber;

    private String sortCode;

    private BigDecimal amount;

    private String frequency;

    private LocalDate nextExecutionDate;

    private String status;

    private LocalDateTime orderDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getters and setters
}
