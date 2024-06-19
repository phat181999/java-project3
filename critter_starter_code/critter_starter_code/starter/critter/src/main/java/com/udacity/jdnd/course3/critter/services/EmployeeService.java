package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.users.Customer;
import com.udacity.jdnd.course3.critter.entities.users.Employee;
import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class EmployeeService {

    private final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee){

        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }

        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees(){

        List<Employee> employees = employeeRepository.getAllEmployees();
        if (employees == null || employees.isEmpty()) {
            throw new RuntimeException("No employees found");
        }

        return employees;
    }

    public Employee getEmployeeByID(Long employeeID){

        if (employeeID == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }

        Employee employee = employeeRepository.getEmployeeByID(employeeID);
        if (employee == null) {
            throw new RuntimeException("Employee not found with ID: " + employeeID);
        }

        return employee;
    }

    public void deleteEmployeeByID(Long employeeID){

        if (employeeID == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }

        Employee employee = employeeRepository.getEmployeeByID(employeeID);
        if (employee == null) {
            throw new RuntimeException("Employee not found with ID: " + employeeID);
        }

        employeeRepository.delete(employee);
    }

    public List<Employee> getAvailableEmployeesForService(LocalDate date, Set<EmployeeSkill> skills){

        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (skills == null || skills.isEmpty()) {
            throw new IllegalArgumentException("Skills set cannot be null or empty");
        }

        DayOfWeek dayOfWeek = date.getDayOfWeek();
        List<Employee> availableEmployeesByDOW = employeeRepository.getAvailableEmployeesByDOW(dayOfWeek);
        if (availableEmployeesByDOW == null || availableEmployeesByDOW.isEmpty()) {
            throw new RuntimeException("No available employees found for day of the week: " + dayOfWeek);
        }

        List<Employee> filteredEmployeesBySkills = availableEmployeesByDOW.stream()
                .filter(e -> e.getEmployeeSkills().containsAll(skills))
                .collect(toList());

        if (filteredEmployeesBySkills.isEmpty()) {
            throw new RuntimeException("No employees found with the required skills: " + skills);
        }

        return filteredEmployeesBySkills;
    }

    public List<Employee> getEmployeesByLastNamePrefix(String lastNamePrefix){

        if (lastNamePrefix == null || lastNamePrefix.isEmpty()) {
            throw new IllegalArgumentException("Last name prefix cannot be null or empty");
        }

        List<Employee> allEmployees = employeeRepository.getAllEmployees();
        if (allEmployees == null || allEmployees.isEmpty()) {
            throw new RuntimeException("No employees found");
        }

        List<Employee> filteredEmployeesByLastNamePrefix = allEmployees.stream()
                .filter(e -> e.getLastName().toLowerCase().startsWith(lastNamePrefix.toLowerCase()))
                .collect(toList());

        if (filteredEmployeesByLastNamePrefix.isEmpty()) {
            throw new RuntimeException("No employees found with last name prefix: " + lastNamePrefix);
        }

        return filteredEmployeesByLastNamePrefix;

    }

}
