package com.example.les12services.service;

import com.example.les12services.dto.TeacherDto;
import com.example.les12services.exceptions.ResourceNotFoundException;
import com.example.les12services.model.Teacher;
import com.example.les12services.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TeacherService {

    private final TeacherRepository repos;

    // constructor injection (instead of @Autowired)
    public TeacherService(TeacherRepository r) {
        this.repos = r;
    }

    public Iterable<TeacherDto> getTeachers() {
        Iterable<Teacher> allTeachers = repos.findAll();
        ArrayList<TeacherDto> resultList = new ArrayList<>();
        for (Teacher t : allTeachers) {
          TeacherDto newTeacherDto = new TeacherDto();
          newTeacherDto.firstName = t.getFirstName();
          newTeacherDto.lastName = t.getLastName();
          newTeacherDto.dob = t.getDob();
          resultList.add(newTeacherDto);
        }
        return resultList;
    }

    public TeacherDto getTeacher(long id) {
        if (repos.findById(id).isPresent()) {
            Teacher teacher = repos.findById(id).get();
            TeacherDto teacherDto = new TeacherDto();

            teacherDto.firstName = teacher.getFirstName();
            teacherDto.lastName = teacher.getLastName();
            teacherDto.dob = teacher.getDob();

            return teacherDto;
        } else {
            throw new ResourceNotFoundException();
        }
    }


    public Long createTeacher(TeacherDto teacherDto) {
        Teacher newTeacher = new Teacher();

        // map dto to entity
        newTeacher.setFirstName(teacherDto.firstName);
        newTeacher.setLastName(teacherDto.lastName);
        newTeacher.setDob(teacherDto.dob);

        Teacher savedTeacher = repos.save(newTeacher);
        return savedTeacher.getId();
    }
}
