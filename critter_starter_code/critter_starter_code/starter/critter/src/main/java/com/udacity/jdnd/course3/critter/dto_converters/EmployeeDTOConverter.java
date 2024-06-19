package com.udacity.jdnd.course3.critter.dto_converters;

import com.udacity.jdnd.course3.critter.dtos.EmployeeDTO;
import com.udacity.jdnd.course3.critter.entities.users.Employee;
import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.Set;

@Component
public class EmployeeDTOConverter {

    /**
     * Converts an Employee object to an EmployeeDTO
     *
     * @param employee
     * @return employeeDTO, the employee object in DTO form
     */
    public EmployeeDTO convertEmployeeToDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        Set<EmployeeSkill> employeeSkills = employee.getEmployeeSkills();
        Set<DayOfWeek> dowAvailable = employee.getEmployeeDaysOfWeekAvailable();

        /*
        Maps properties from Employee to EmployeeDTO

        The employeeSkills and employeeDaysOfWeekAvailable properties are ignored since
        EmployeeDTO does not contain variables with those names
         */
        BeanUtils.copyProperties(employee, employeeDTO, "employeeSkills", "employeeDaysOfWeekAvailable");

        employeeDTO.setSkills(employeeSkills);
        employeeDTO.setDaysAvailable(dowAvailable);

        return employeeDTO;
    }

    /**
     * Converts an EmployeeDTO object to an Employee
     *
     * @param employeeDTO
     * @return employee, The Employee object
     */
    public Employee convertDTOToEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();

        /*
        Maps properties from EmployeeDTO to Employee

        The skills and daysAvailable properties are ignored since
        Employee does not contain variables with those names
         */
        BeanUtils.copyProperties(employeeDTO, employee, "skills", "daysAvailable");

        employee.setEmployeeSkills(employeeDTO.getSkills());
        employee.setEmployeeDaysOfWeekAvailable(employeeDTO.getDaysAvailable());

        return employee;
    }
}
