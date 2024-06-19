package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.entities.users.Customer;
import com.udacity.jdnd.course3.critter.entities.users.Employee;
import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Schedule {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToMany
    @JoinTable(
            name = "schedule_employee",
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> employees;

    @ManyToMany
    @JoinTable(
            name = "schedule_pet",
            inverseJoinColumns = @JoinColumn(name = "pet_id"))
    private List<Pet> pets;

    @ElementCollection
    @JoinTable(name = "schedule_skill")
    private Set<EmployeeSkill> skills;


}
