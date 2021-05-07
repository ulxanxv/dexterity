package ru.dexterity.dao.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "task_rating")
public class TaskRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "credential_id")
    private Credential credential;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    private String solution;

    private int brevity;
    private double rapidity;
    private double resourceConsumption;
    private double totalScore;

}
