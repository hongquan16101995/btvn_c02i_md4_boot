package com.example.springboot.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String cardPhoto;
    private Integer age;
    private String gender;
    private Double averagePoint;

    @Transient
    private MultipartFile image;

    @ManyToOne
    private Classes classes;
}
