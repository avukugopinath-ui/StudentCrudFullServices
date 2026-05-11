package com.example.studentcrud.service;

import com.example.studentcrud.dto.StudentEvent;
import com.example.studentcrud.entity.Student;
import com.example.studentcrud.exception.ResourceNotFoundException;
import com.example.studentcrud.kafka.StudentEventProducer;
import com.example.studentcrud.repository.StudentRepository;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentEventProducer eventProducer;

	public StudentService(StudentRepository studentRepository , StudentEventProducer eventProducer ) {
        this.studentRepository = studentRepository;
        this.eventProducer = eventProducer;
    }

    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    @Transactional
    public Student createStudent(Student student) {
        Student saved = studentRepository.save(student);
        publishEvent("STUDENT_CREATED", saved);
        return saved;
    }

    @Transactional
    public Student updateStudent(Long id, Student updatedStudent) {
        Student existing = getStudentById(id);
        existing.setFirstName(updatedStudent.getFirstName());
        existing.setLastName(updatedStudent.getLastName());
        existing.setEmail(updatedStudent.getEmail());
        Student saved = studentRepository.save(existing);
        publishEvent("STUDENT_UPDATED", saved);
        return saved;
    }

    @Transactional
    public void deleteStudent(Long id) {
        Student existing = getStudentById(id);
        studentRepository.delete(existing);
        publishEvent("STUDENT_DELETED", existing);
    }

	
	private void publishEvent(String eventType, Student student) {
		StudentEvent event = new StudentEvent(eventType, student.getId(), student.getEmail(), Instant.now());
		eventProducer.publish(event);
	}
	 
}
