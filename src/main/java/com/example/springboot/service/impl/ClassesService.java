package com.example.springboot.service.impl;

import com.example.springboot.dto.ClassesDTO;
import com.example.springboot.model.Classes;
import com.example.springboot.repository.IClassesRepository;
import com.example.springboot.service.IClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClassesService implements IClassesService {
    @Autowired
    private IClassesRepository iClassesRepository;

    @Override
    public Iterable<Classes> findAll() {
        return iClassesRepository.findAll();
    }

    @Override
    public Classes save(Classes classes) {
        return iClassesRepository.save(classes);
    }

    @Override
    public Classes update(Classes classes, Long id) {
        return  null;
    }

    @Override
    public void delete(Long id) {}

    @Override
    public Optional<Classes> findOne(Long id) {
        return iClassesRepository.findById(id);
    }

    @Override
    public List<Classes> sortByQuantityAsc() {
        return iClassesRepository.findAllByOrderByQuantityAsc();
    }

    @Override
    public List<Classes> sortByQuantityDesc() {
        return iClassesRepository.findAllByOrderByQuantityDesc();
    }

    @Override
    public List<ClassesDTO> findAllByAvgPoint() {
        List<Double> avg = iClassesRepository.findAllByAvg();
        List<Classes> classes = (List<Classes>) findAll();
        List<ClassesDTO> classesDTOS = new ArrayList<>();
        for (int i = 0; i < classes.size(); i++) {
            if (avg.get(i) == null) {
                classesDTOS.add(new ClassesDTO(classes.get(i), 0D));
            } else {
                classesDTOS.add(new ClassesDTO(classes.get(i), avg.get(i)));
            }
        }
        return classesDTOS;
    }

    @Override
    public void upQuantity(Classes classes) {
        classes.setQuantity(classes.getQuantity() + 1);
        iClassesRepository.save(classes);
    }

    @Override
    public void downQuantity(Classes classes) {
        classes.setQuantity(classes.getQuantity() - 1);
        iClassesRepository.save(classes);
    }
}
