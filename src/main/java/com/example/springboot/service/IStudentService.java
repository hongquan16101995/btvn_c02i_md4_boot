package com.example.springboot.service;

import com.example.springboot.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IStudentService extends IGenerateService<Student> {

    Page<Student> findAllPage(Pageable pageable);
    List<Student> findAllByName();

    List<Student> sortByAgeAsc();

    List<Student> sortByAgeDesc();

    List<Student> sortByAvgPointAsc();

    List<Student> sortByAvgPointDesc();
}
