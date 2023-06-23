package com.example.springboot.dto;

import com.example.springboot.model.Classes;

public class ClassesDTO {
    private Classes classes;
    private Double avg;

    public ClassesDTO(Classes classes, Double avg) {
        this.classes = classes;
        this.avg = avg;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }
}
