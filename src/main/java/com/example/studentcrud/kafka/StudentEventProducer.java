package com.example.studentcrud.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.studentcrud.dto.StudentEvent;

@Component
public class StudentEventProducer {

    private final KafkaTemplate<String, StudentEvent> kafkaTemplate;

    @Value("${app.kafka.student-topic}")
    private String studentTopic;

    public StudentEventProducer(KafkaTemplate<String, StudentEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(StudentEvent event) {
       kafkaTemplate.send(studentTopic, event.getStudentId().toString(), event);
    }
}
