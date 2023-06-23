package com.example.springboot.controller;

import com.example.springboot.model.Classes;
import com.example.springboot.model.Student;
import com.example.springboot.service.IClassesService;
import com.example.springboot.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private IStudentService studentService;

    @Autowired
    private IClassesService classesService;

    @ModelAttribute("classes")
    private Iterable<Classes> getClasses() {
        return classesService.findAll();
    }

    @Value("${path-upload}")
    private String upload;

    @GetMapping
    public ModelAndView listStudents() {
        ModelAndView modelAndView = new ModelAndView("/student/list");
        modelAndView.addObject("students", studentService.findAll());
        return modelAndView;
    }

    @GetMapping("/page")
    public ModelAndView pageStudents(@PageableDefault(value = 3) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("/student/page");
        modelAndView.addObject("students", studentService.findAllPage(pageable));
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createPage() {
        ModelAndView modelAndView = new ModelAndView("/student/create");
        modelAndView.addObject("student", new Student());
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("student") Student student,
                         RedirectAttributes redirect) throws DataIntegrityViolationException {
        Student studentCreate = studentService.save(student);
        if (studentCreate != null) {
            redirect.addFlashAttribute("message", "Create successfully!");
            return "redirect:/students";
        } else {
            return "/404";
        }
    }

    @GetMapping("/update/{id}")
    public ModelAndView updatePage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/student/update");
        Optional<Student> studentOptional = studentService.findOne(id);
        if (studentOptional.isPresent()) {
            modelAndView.addObject("student", studentOptional.get());
        } else {
            modelAndView.setViewName("/student/list");
            modelAndView.addObject("students", studentService.findAll());
        }
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute Student student, @PathVariable Long id,
                         RedirectAttributes redirect) throws DataIntegrityViolationException {
        Student studentUpdate = studentService.update(student, id);
        if (studentUpdate != null) {
            redirect.addFlashAttribute("message", "Update successfully!");
            return "redirect:/students";
        } else {
            return "/404";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirect) {
        Optional<Student> studentOptional = studentService.findOne(id);
        if (studentOptional.isPresent()) {
            studentService.delete(id);
            redirect.addFlashAttribute("message", "Delete successfully!");
            return "redirect:/students";
        } else {
            return "/404";
        }
    }

    @GetMapping("/sort_age_asc")
    public ModelAndView listStudentsByAgeAsc() {
        ModelAndView modelAndView = new ModelAndView("/student/list");
        modelAndView.addObject("students", studentService.sortByAgeAsc());
        return modelAndView;
    }

    @GetMapping("/sort_age_desc")
    public ModelAndView listStudentsByAgeDesc() {
        ModelAndView modelAndView = new ModelAndView("/student/list");
        modelAndView.addObject("students", studentService.sortByAgeDesc());
        return modelAndView;
    }

    @GetMapping("/sort_avg_asc")
    public ModelAndView listStudentsByAvgAsc() {
        ModelAndView modelAndView = new ModelAndView("/student/list");
        modelAndView.addObject("students", studentService.sortByAvgPointAsc());
        return modelAndView;
    }

    @GetMapping("/sort_avg_desc")
    public ModelAndView listStudentsByAvgDesc() {
        ModelAndView modelAndView = new ModelAndView("/student/list");
        modelAndView.addObject("students", studentService.sortByAvgPointDesc());
        return modelAndView;
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public String catchError() {
        return "/404";
    }
}
