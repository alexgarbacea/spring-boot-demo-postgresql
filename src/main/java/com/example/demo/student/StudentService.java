package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component//for spring
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();//returns a list
//old way of doing it without Interface
//        return Arrays.asList(
//                new Student(1L,
//                        "Alex",
//                        "alex@mail.ro",
//                        LocalDate.of(2000, Month.JANUARY, 5),
//                        21
//                )
//        );
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional =  studentRepository.findByEmail(student.getEmail());
        if(studentOptional.isPresent()) {
            throw new IllegalStateException("used email");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists) {
            throw new IllegalStateException("student with id "+studentId+" does not exist");
        }
        else{
            studentRepository.deleteById(studentId);
        }
    }

    @Transactional//helps avoid queries
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "student with id "+studentId+" does not exist"
                ));
        if(name != null && name.length() > 0) {
            student.setName(name);
        }

        if(email != null && email.length() > 0) {
            Optional<Student> studentOptional = studentRepository.findByEmail(email);
            if(studentOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }
            student.setEmail(email);
        }
    }
}
