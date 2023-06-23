package com.example.springboot.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
public class Classes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(name = "number_student")
    private Integer quantity;
}
