package com.udacity.jdnd.course3.critter.entities.users;

import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
@Data
public class Employee {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    private String firstName;

    @Nationalized
    private String lastName;


    @ElementCollection
    @JoinTable(name = "employee_skill")
    private Set<EmployeeSkill> employeeSkills;

    @ElementCollection
    @JoinTable(name = "employee_dow_available")
    @Column(name = "daysOfWeekAvailable")
    private Set<DayOfWeek> employeeDaysOfWeekAvailable;
}
