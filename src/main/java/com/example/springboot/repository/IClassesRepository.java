package com.example.springboot.repository;

import com.example.springboot.model.Classes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IClassesRepository extends PagingAndSortingRepository<Classes, Long> {
    List<Classes> findAllByOrderByQuantityAsc();

    List<Classes> findAllByOrderByQuantityDesc();

    @Query(value = "select avg(student.averagePoint) from student " +
            "right join classes on classes.id = student.classes_id" +
            " group by classes.id", nativeQuery = true)
    List<Double> findAllByAvg();
}
