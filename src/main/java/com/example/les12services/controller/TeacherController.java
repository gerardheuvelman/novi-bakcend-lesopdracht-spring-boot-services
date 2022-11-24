package com.example.les12services.controller;

import com.example.les12services.dto.TeacherDto;
import com.example.les12services.model.Teacher;
import com.example.les12services.repository.TeacherRepository;
import com.example.les12services.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService service;

    public TeacherController(TeacherService s) {
        this.service = s;
    }

    @GetMapping("")
    public ResponseEntity<Iterable<TeacherDto>> getTeachers() {
        return ResponseEntity.ok(service.getTeachers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> getTeacher(@PathVariable long id) {
        return ResponseEntity.ok(service.getTeacher(id));
    }

    @PostMapping("")
    public ResponseEntity<String> createTeacher(@Valid @RequestBody TeacherDto teacherDto, BindingResult br) {

        if (br.hasErrors()) {
            // something's wrong
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        else {
            // happy flow
            Long createdId = service.createTeacher(teacherDto);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/teachers/" + createdId).toUriString());
            return ResponseEntity.created(uri).body("Teacher created!");
        }
    }

//    @GetMapping("/after")
//    public ResponseEntity<Iterable<Teacher>> getTeachersAfter(
//            @RequestParam @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) LocalDate date) {
//        return ResponseEntity.ok(repos.findByDobAfter(date));
//    }
}
