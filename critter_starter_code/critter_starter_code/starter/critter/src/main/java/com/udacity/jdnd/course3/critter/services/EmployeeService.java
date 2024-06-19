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

        Employee newEmployee = employeeRepository.save(employee);

        return newEmployee;
    }

    public List<Employee> getAllEmployees(){

        List<Employee> employees = employeeRepository.getAllEmployees();

        return employees;
    }

    public Employee getEmployeeByID(Long employeeID){

        Employee employee = employeeRepository.getEmployeeByID(employeeID);

        return employee;
    }

    public void deleteEmployeeByID(Long employeeID){

        Employee employee = employeeRepository.getEmployeeByID(employeeID);

        employeeRepository.delete(employee);
    }

    public List<Employee> getAvailableEmployeesForService(LocalDate date, Set<EmployeeSkill> skills){

        //Variable storing the day of the week
        DayOfWeek dayOfWeek = date.getDayOfWeek();


        List<Employee> availableEmployeesByDOW = employeeRepository.getAvailableEmployeesByDOW(dayOfWeek);

        List<Employee> filteredEmployeesBySkills = availableEmployeesByDOW.stream()
                .filter(e -> e.getEmployeeSkills().containsAll(skills))
                .collect(toList());

        logger.info("Filtered Employees By Skills: " + filteredEmployeesBySkills);


        return filteredEmployeesBySkills;
    }

    public List<Employee> getEmployeesByLastNamePrefix(String lastNamePrefix){

        List<Employee> allEmployees = employeeRepository.getAllEmployees();

        logger.info("All Employees: " + allEmployees);

        List<Employee> filteredEmployeesByLastNamePrefix = allEmployees
                .stream()
                .filter(e -> e.getLastName().toLowerCase().startsWith(lastNamePrefix.toLowerCase()))
                .collect(toList());

        logger.info("Filtered Employees: " + filteredEmployeesByLastNamePrefix);

        return filteredEmployeesByLastNamePrefix;

    }

}
