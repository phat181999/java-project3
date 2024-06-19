package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.dto_converters.ScheduleDTOConverter;
import com.udacity.jdnd.course3.critter.dtos.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.repositories.ScheduleRepository;
import com.udacity.jdnd.course3.critter.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleDTOConverter scheduleDTOConverter;

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleDTOConverter.convertDTOToSchedule(scheduleDTO);

        Schedule savedSchedule = scheduleService.saveSchedule(schedule);

        ScheduleDTO savedScheduleDTO = scheduleDTOConverter.convertScheduleToDTO(savedSchedule);

        return savedScheduleDTO;

    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> listOfSchedules = scheduleService.getAllSchedules();

        List<ScheduleDTO> listOfSchedulesDTO = listOfSchedules
                .stream()
                .map(scheduleDTOConverter::convertScheduleToDTO)
                .collect(toList());

        return listOfSchedulesDTO;
    }

    @GetMapping("/{scheduleID}")
    public ScheduleDTO getScheduleByID(@PathVariable long scheduleID){
        Schedule schedule = scheduleService.getScheduleByID(scheduleID);

        ScheduleDTO scheduleDTO = scheduleDTOConverter.convertScheduleToDTO(schedule);

        return scheduleDTO;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getSchedulesForPet(@PathVariable long petId) {
        List<Schedule> listOfSchedulesByPetID = scheduleService.getAllSchedulesByPetID(petId);

        List<ScheduleDTO> listOfSchedulesByPetID_DTO = listOfSchedulesByPetID
                .stream()
                .map(scheduleDTOConverter::convertScheduleToDTO)
                .collect(toList());

        return listOfSchedulesByPetID_DTO;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getSchedulesForEmployee(@PathVariable long employeeId) {
        List<Schedule> listOfSchedulesByEmployeeID = scheduleService.getAllSchedulesByEmployeeID(employeeId);

        List<ScheduleDTO> listOfSchedulesByEmployeeID_DTO = listOfSchedulesByEmployeeID
                .stream()
                .map(scheduleDTOConverter::convertScheduleToDTO)
                .collect(toList());

        return listOfSchedulesByEmployeeID_DTO;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getSchedulesForCustomer(@PathVariable long customerId) {
        List<Schedule> listOfSchedulesByCustomerID = scheduleService.getAllSchedulesByCustomerID(customerId);

        List<ScheduleDTO> listOfSchedulesByCustomerID_DTO = listOfSchedulesByCustomerID
                .stream()
                .map(scheduleDTOConverter::convertScheduleToDTO)
                .collect(toList());

        return listOfSchedulesByCustomerID_DTO;
    }
}
