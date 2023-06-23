package com.example.springboot.service;

import com.example.springboot.dto.ClassesDTO;
import com.example.springboot.model.Classes;

import java.util.List;

public interface IClassesService extends IGenerateService<Classes> {
    List<Classes> sortByQuantityAsc();

    List<Classes> sortByQuantityDesc();

    List<ClassesDTO> findAllByAvgPoint();

    void upQuantity(Classes classes);

    void downQuantity(Classes classes);
}
