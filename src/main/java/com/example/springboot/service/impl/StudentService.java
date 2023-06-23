package com.example.springboot.service.impl;

import com.example.springboot.model.Student;
import com.example.springboot.repository.IStudentRepository;
import com.example.springboot.service.IClassesService;
import com.example.springboot.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService implements IStudentService {
    @Autowired
    private IStudentRepository iStudentRepository;

    @Autowired
    private IClassesService classesService;

    @Value("${path-upload}")
    private String upload;

    @Override
    public Page<Student> findAllPage(Pageable pageable) {
        return iStudentRepository.findAll(pageable);
    }

    @Override
    public Iterable<Student> findAll() {
        return iStudentRepository.findAll();
    }

    @Override
    public Student save(Student student) throws DataIntegrityViolationException {
        MultipartFile image = student.getImage();
        try {
            if (!image.isEmpty()) {
                FileCopyUtils.copy(image.getBytes(), new File(upload + image.getOriginalFilename()));
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
        student.setCardPhoto(image.getOriginalFilename());
        Student studentCreate = iStudentRepository.save(student);
        classesService.upQuantity(studentCreate.getClasses());
        return studentCreate;
    }

    @Override
    public Student update(Student student, Long id) {
        Optional<Student> studentOptional = findOne(id);
        if (studentOptional.isPresent()) {
            MultipartFile image = student.getImage();
            boolean check = image.isEmpty();
            try {
                if (!check) {
                    FileCopyUtils.copy(image.getBytes(), new File(upload + image.getOriginalFilename()));
                }
            } catch (IOException io) {
                io.printStackTrace();
            }
            if (!check) {
                student.setCardPhoto(image.getOriginalFilename());
            } else {
                student.setCardPhoto(studentOptional.get().getCardPhoto());
            }
            student.setId(student.getId());
            return iStudentRepository.save(student);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Student> studentOptional = findOne(id);
        iStudentRepository.deleteById(id);
        classesService.downQuantity(studentOptional.get().getClasses());
    }

    @Override
    public Optional<Student> findOne(Long id) {
        return iStudentRepository.findById(id);
    }

    @Override
    public List<Student> findAllByName() {
        return null;
    }

    @Override
    public List<Student> sortByAgeAsc() {
        return iStudentRepository.findAllByOrderByAgeAsc();
    }

    @Override
    public List<Student> sortByAgeDesc() {
        return iStudentRepository.findAllByOrderByAgeDesc();
    }

    @Override
    public List<Student> sortByAvgPointAsc() {
        return iStudentRepository.findAllByOrderByAveragePointAsc();
    }

    @Override
    public List<Student> sortByAvgPointDesc() {
        return iStudentRepository.findAllByOrderByAveragePointDesc();
    }
}
