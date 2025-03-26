package com.demokafka.kafka_producer.batchprocessor;

import com.demokafka.kafka_producer.service.KafkaProducerService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@EnableScheduling
@Component
public class FileBatchProcessor {

    private final KafkaProducerService kafkaProducerService;

    public FileBatchProcessor(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @Scheduled(cron = "${file.batch.cron:-}")
    public void processFile()
    {
        try {
            URL url = new URL("https://storage.googleapis.com/ngi-payments/StandingOrder.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            CSVParser csvParser = CSVFormat.DEFAULT.withHeader().parse(reader);

            for (CSVRecord csvRecord : csvParser) {
                String line=csvRecord.get(0)+","+csvRecord.get(1)+","+csvRecord.get(2)+","+csvRecord.get(3)+","
                        +csvRecord.get(4)+","+csvRecord.get(5)+","+csvRecord.get(6);
                Thread.sleep(2000);
                kafkaProducerService.sendMessage(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }
