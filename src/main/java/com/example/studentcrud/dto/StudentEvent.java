package com.example.studentcrud.dto;

import java.time.Instant;

public class StudentEvent {

    private String eventType;
    private Long studentId;
    private String email;
    private Instant timestamp;

    public StudentEvent() {
    }

    public StudentEvent(String eventType, Long studentId, String email, Instant timestamp) {
        this.eventType = eventType;
        this.studentId = studentId;
        this.email = email;
        this.timestamp = timestamp;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
