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
    /*@SequenceGenerator(name="schedule_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_sequence")*/
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    /*
    Statements that create association tables between:

    -Schedule and Employee
    -Schedule and Pet
    -Schedule and Customer
     */
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

    /*@ManyToMany
    @JoinTable(
       name = "schedule_customer",
       inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private List<Customer> customers;*/


    /*
    Creates a collection (association) table between:

    -Schedule and EmployeeSkill

    Schedule: Controls the association
     */
    @ElementCollection
    @JoinTable(name = "schedule_skill")
    private Set<EmployeeSkill> skills;


}
