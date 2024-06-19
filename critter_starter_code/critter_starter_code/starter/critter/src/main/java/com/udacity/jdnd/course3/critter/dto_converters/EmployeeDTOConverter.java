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

   
    public EmployeeDTO convertEntityToDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();

        BeanUtils.copyProperties(employee, employeeDTO, "employeeSkills", "employeeDaysOfWeekAvailable");

        if (employee.getEmployeeSkills() != null) {
            employeeDTO.setSkills(employee.getEmployeeSkills());
        }

        if (employee.getEmployeeDaysOfWeekAvailable() != null) {
            employeeDTO.setDaysAvailable(employee.getEmployeeDaysOfWeekAvailable());
        }

        return employeeDTO;
    }

 
    public Employee convertDTOToEntity(EmployeeDTO employeeDTO){
        Employee employee = new Employee();

        BeanUtils.copyProperties(employeeDTO, employee, "skills", "daysAvailable");

        if (employeeDTO.getSkills() != null) {
            employee.setEmployeeSkills(employeeDTO.getSkills());
        }

        if (employeeDTO.getDaysAvailable() != null) {
            employee.setEmployeeDaysOfWeekAvailable(employeeDTO.getDaysAvailable());
        }

        return employee;

    }
}
