package com.employee.management.system.entity;


import jakarta.persistence.*;
import lombok.*;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="projects")
public class Project {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pId;

    private String pName;
    private String pDesc;
    private String startDate;
    private String endDate;
    private Double budget;

}
