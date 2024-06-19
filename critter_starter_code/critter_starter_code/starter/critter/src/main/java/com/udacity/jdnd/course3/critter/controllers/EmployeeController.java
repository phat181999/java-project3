package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.dto_converters.EmployeeDTOConverter;
import com.udacity.jdnd.course3.critter.dtos.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dtos.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entities.users.Customer;
import com.udacity.jdnd.course3.critter.entities.users.Employee;
import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

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
        Employee employee = employeeDTOConverter.convertDTOToEmployee(employeeDTO);
        Employee savedEmployee = employeeService.saveEmployee(employee);

        EmployeeDTO savedEmployeeDTO = employeeDTOConverter.convertEmployeeToDTO(savedEmployee);

        return savedEmployeeDTO;

    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> listOfEmployees = employeeService.getAllEmployees();

        List<EmployeeDTO> listOfEmployeesDTO;
        listOfEmployeesDTO = listOfEmployees.stream()
                .map(employeeDTOConverter::convertEmployeeToDTO)
                .collect(toList());

        return listOfEmployeesDTO;
    }

    @GetMapping("/{employeeID}")
    public EmployeeDTO getEmployee(@PathVariable long employeeID) {
        Employee employee = employeeService.getEmployeeByID(employeeID);

        EmployeeDTO employeeDTO = employeeDTOConverter.convertEmployeeToDTO(employee);

        return employeeDTO;
    }

    @PatchMapping("/{employeeID}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeID) {
        Employee employee = employeeService.getEmployeeByID(employeeID);

        employee.setEmployeeDaysOfWeekAvailable(daysAvailable);

        employeeService.saveEmployee(employee);
    }

    @GetMapping("/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> listOfAvailableEmployees = employeeService.getAvailableEmployeesForService(employeeDTO.getDate(), employeeDTO.getSkills());

        List<EmployeeDTO> listOfAvailableEmployeesDTO = listOfAvailableEmployees
                .stream()
                .map(employeeDTOConverter::convertEmployeeToDTO)
                .collect(toList());

        return listOfAvailableEmployeesDTO;
    }

    @DeleteMapping("/{employeeID}")
    /*@OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value={CascadeType.ALL})*/
    public void deleteEmployeeByID(@PathVariable Long employeeID){
        employeeService.deleteEmployeeByID(employeeID);
    }

    //Extra endpoints
    @GetMapping("/lastname")
    public List<EmployeeDTO> findEmployeesByLastNamePrefix(@RequestParam String startswith){
        List<Employee> employeesByLastNamePrefix = employeeService.getEmployeesByLastNamePrefix(startswith);

        logger.info("Employees By Last Name Prefix: " + employeesByLastNamePrefix);

        List<EmployeeDTO> employeesByLastNamePrefix_DTO = employeesByLastNamePrefix
                .stream()
                .map(employeeDTOConverter::convertEmployeeToDTO)
                .collect(toList());

        return employeesByLastNamePrefix_DTO;
    }
}
