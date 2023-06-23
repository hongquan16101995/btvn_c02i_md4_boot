package com.example.springboot.repository;

import com.example.springboot.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStudentRepository extends PagingAndSortingRepository<Student, Long> {

    Page<Student> findAll(Pageable pageable);

    List<Student> findAllByOrderByAgeAsc();

    List<Student> findAllByOrderByAgeDesc();

    List<Student> findAllByOrderByAveragePointAsc();

    List<Student> findAllByOrderByAveragePointDesc();
}
