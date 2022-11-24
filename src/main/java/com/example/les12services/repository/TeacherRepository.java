package com.example.les12services.repository;

import com.example.les12services.model.Teacher;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface TeacherRepository extends CrudRepository<Teacher, Long> {
    public Iterable<Teacher> findByDobAfter(LocalDate date);
}
