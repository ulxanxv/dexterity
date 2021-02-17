package ru.dexterity.dao.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String shortDescription;

    @Column(nullable = false)
    private String longDescription;

    @Column(nullable = false)
    private int difficult;

}