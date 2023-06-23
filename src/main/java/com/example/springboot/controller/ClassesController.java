package com.example.springboot.controller;

import com.example.springboot.model.Classes;
import com.example.springboot.service.IClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/classes")
public class ClassesController {
    @Autowired
    private IClassesService classesService;

    @GetMapping
    public ModelAndView listClasses() {
        ModelAndView modelAndView = new ModelAndView("/classes/list");
        modelAndView.addObject("classes", classesService.findAll());
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createPage() {
        ModelAndView modelAndView = new ModelAndView("/classes/create");
        modelAndView.addObject("class", new Classes());
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Classes classes,
                         RedirectAttributes redirect) {
        classes.setQuantity(0);
        classesService.save(classes);
        redirect.addFlashAttribute("message", "Create successfully!");
        return "redirect:/classes";
    }

    @GetMapping("/update/{id}")
    public ModelAndView updatePage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/classes/update");
        Optional<Classes> classesOptional = classesService.findOne(id);
        if(classesOptional.isPresent()) {
            modelAndView.addObject("class", classesOptional.get());
        } else {
            modelAndView.setViewName("/classes/list");
            modelAndView.addObject("classes", classesService.findAll());
        }
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute Classes classes,
                         @PathVariable Long id,
                         RedirectAttributes redirect) {
        Optional<Classes> classesOptional = classesService.findOne(id);
        if(classesOptional.isPresent()) {
            classes.setQuantity(classesOptional.get().getQuantity());
            classes.setId(id);
            classesService.save(classes);
        }
        redirect.addFlashAttribute("message", "Update successfully!");
        return "redirect:/classes";
    }

    @GetMapping("/sort_asc")
    public ModelAndView listClassesByQuantityAsc() {
        ModelAndView modelAndView = new ModelAndView("/classes/list");
        modelAndView.addObject("classes", classesService.sortByQuantityAsc());
        return modelAndView;
    }

    @GetMapping("/sort_desc")
    public ModelAndView listClassesByQuantityDesc() {
        ModelAndView modelAndView = new ModelAndView("/classes/list");
        modelAndView.addObject("classes", classesService.sortByQuantityDesc());
        return modelAndView;
    }

    @GetMapping("/avg")
    public ModelAndView listClassesByAvgPoint() {
        ModelAndView modelAndView = new ModelAndView("/classes/list");
        modelAndView.addObject("classesDTO", classesService.findAllByAvgPoint());
        return modelAndView;
    }
}
