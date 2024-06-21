package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.dto_converters.EmployeeDTOConverter;
import com.udacity.jdnd.course3.critter.dtos.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dtos.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entities.users.Employee;
import com.udacity.jdnd.course3.critter.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeDTOConverter employeeDTOConverter;

    @PostMapping
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        if (employeeDTO== null ) {
            throw new IllegalArgumentException("Employee name cannot be null or empty");
        }
        
        Employee employee = employeeDTOConverter.convertDtoEntity(employeeDTO);
        Employee savedEmployee = employeeService.saveEmployee(employee);

        EmployeeDTO savedEmployeeDTO = employeeDTOConverter.covertEnityDto(savedEmployee);

        return savedEmployeeDTO;

    }

    @GetMapping
    public List<EmployeeDTO> getEmployees() {
        List<Employee> listOfEmployees = employeeService.getEmployees();
        if (listOfEmployees == null ) {
            throw new IllegalArgumentException("Employee is an empty");
        }
        
        List<EmployeeDTO> listOfEmployeesDTO;
        listOfEmployeesDTO = listOfEmployees.stream()
                .map(employeeDTOConverter::covertEnityDto)
                .collect(toList());

        return listOfEmployeesDTO;
    }

    @GetMapping("/{employeeID}")
    public EmployeeDTO getEmployee(@PathVariable long employeeID) {
       
        Employee employee = employeeService.getEmployeeByID(employeeID);
        if (employee == null) {
            throw new IllegalArgumentException("Employee with ID " + employeeID + " not found");
        }
        EmployeeDTO employeeDTO = employeeDTOConverter.covertEnityDto(employee);

        return employeeDTO;
    }

    @PatchMapping("/{employeeID}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeID) {
        Employee employee = employeeService.getEmployeeByID(employeeID);
        if (employee == null) {
            throw new IllegalArgumentException("Employee with ID " + employeeID + " not found");
        }
        employee.setEmployeeDaysOfWeekAvailable(daysAvailable);

        employeeService.saveEmployee(employee);
    }

    @GetMapping("/availability")
    public List<EmployeeDTO> getAvailabilityEmployee(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> listOfAvailableEmployees = employeeService.getAvailableEmployeesForService(employeeDTO.getDate(), employeeDTO.getSkills());
        if (listOfAvailableEmployees == null) {
            throw new IllegalArgumentException("Employee with ID " + listOfAvailableEmployees + " not found");
        }
        List<EmployeeDTO> listOfAvailableEmployeesDTO = listOfAvailableEmployees
                .stream()
                .map(employeeDTOConverter::covertEnityDto)
                .collect(toList());

        return listOfAvailableEmployeesDTO;
    }

    @DeleteMapping("/{employeeID}")
    public void deleteEmployeeByID(@PathVariable Long employeeID){
        if (employeeID == null) {
            throw new IllegalArgumentException("Employee with ID not found");
        }
        employeeService.deleteEmployeeByID(employeeID);
    }
}
