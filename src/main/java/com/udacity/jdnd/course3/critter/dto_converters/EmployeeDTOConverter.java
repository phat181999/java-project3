package com.udacity.jdnd.course3.critter.dto_converters;

import com.udacity.jdnd.course3.critter.dtos.EmployeeDTO;
import com.udacity.jdnd.course3.critter.entities.users.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;


@Component
public class EmployeeDTOConverter {

   
    public EmployeeDTO covertEnityDto(Employee employee){
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

 
    public Employee convertDtoEntity(EmployeeDTO employeeDTO){
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
