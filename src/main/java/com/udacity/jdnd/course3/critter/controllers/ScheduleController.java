package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.dto_converters.ScheduleDTOConverter;
import com.udacity.jdnd.course3.critter.dtos.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleDTOConverter scheduleDTOConverter;

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createNewSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        if (scheduleDTO == null) {
            throw new IllegalArgumentException("ScheduleDTO must not be null");
        }
    
        Schedule schedule = scheduleDTOConverter.convertDtoEntity(scheduleDTO);
    
        Schedule savedSchedule = scheduleService.saveSchedule(schedule);
    
        ScheduleDTO savedScheduleDTO = scheduleDTOConverter.covertEnityDto(savedSchedule);
    
        return savedScheduleDTO;

    }

    @GetMapping
    public List<ScheduleDTO> getSchedules() {
        List<Schedule> listOfSchedules = scheduleService.getSchedules();

        if (listOfSchedules == null) {
            throw new IllegalStateException("Received null list of schedules from service");
        }

        if (listOfSchedules.isEmpty()) {
            throw new IllegalArgumentException("No schedules found");
        }

        List<ScheduleDTO> listOfSchedulesDTO = listOfSchedules.stream()
                .map(scheduleDTOConverter::covertEnityDto)
                .collect(Collectors.toList());

        return listOfSchedulesDTO;
    }

    @GetMapping("/{scheduleID}")
    public ScheduleDTO getScheduleByID(@PathVariable long scheduleID){
        if (scheduleID <= 0) {
            throw new IllegalArgumentException("Invalid schedule ID: " + scheduleID);
        }

        Schedule schedule = scheduleService.getScheduleByID(scheduleID);

        if (schedule == null) {
            throw new NoSuchElementException("Schedule not found for ID: " + scheduleID);
        }

        ScheduleDTO scheduleDTO = scheduleDTOConverter.covertEnityDto(schedule);

        return scheduleDTO;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getSchedulesByPet(@PathVariable long petId) {
        List<Schedule> listOfSchedulesByPetID = scheduleService.getAllSchedulesByPetID(petId);

        List<ScheduleDTO> listOfSchedulesByPetID_DTO = listOfSchedulesByPetID
                .stream()
                .map(scheduleDTOConverter::covertEnityDto)
                .collect(toList());

        return listOfSchedulesByPetID_DTO;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getSchedulesByEmployee(@PathVariable long employeeId) {
        if (employeeId <= 0) {
            throw new IllegalArgumentException("Invalid employee ID: " + employeeId);
        }

        List<Schedule> listOfSchedulesByEmployeeID = scheduleService.getAllSchedulesByEmployeeID(employeeId);

        if (listOfSchedulesByEmployeeID == null) {
            throw new IllegalStateException("Received null list of schedules for employee ID: " + employeeId);
        }

        if (listOfSchedulesByEmployeeID.isEmpty()) {
            throw new IllegalArgumentException("No schedules found for employee ID: " + employeeId);
        }

        List<ScheduleDTO> listOfSchedulesByEmployeeID_DTO = listOfSchedulesByEmployeeID.stream()
                .map(scheduleDTOConverter::covertEnityDto)
                .collect(Collectors.toList());

        return listOfSchedulesByEmployeeID_DTO;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getSchedulesByCustomer(@PathVariable long customerId) {
        if (customerId <= 0) {
            throw new IllegalArgumentException("Invalid customer ID: " + customerId);
        }

        List<Schedule> listOfSchedulesByCustomerID = scheduleService.getAllSchedulesByCustomerID(customerId);

        if (listOfSchedulesByCustomerID == null) {
            throw new IllegalStateException("Received null list of schedules for customer ID: " + customerId);
        }

        if (listOfSchedulesByCustomerID.isEmpty()) {
            throw new IllegalArgumentException("No schedules found for customer ID: " + customerId);
        }

        List<ScheduleDTO> listOfSchedulesByCustomerID_DTO = listOfSchedulesByCustomerID.stream()
                .map(scheduleDTOConverter::covertEnityDto)
                .collect(Collectors.toList());

        return listOfSchedulesByCustomerID_DTO;
    }
}
