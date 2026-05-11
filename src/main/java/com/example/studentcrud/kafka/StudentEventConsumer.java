package com.example.studentcrud.kafka;

import com.example.studentcrud.dto.StudentEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class StudentEventConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentEventConsumer.class);

    @KafkaListener(autoStartup = "false",topics = "${app.kafka.student-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(StudentEvent event) {
        LOGGER.info("Consumed Kafka event: type={}, studentId={}, email={}",
                event.getEventType(), event.getStudentId(), event.getEmail());
    }
}
