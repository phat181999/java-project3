package com.udacity.jdnd.course3.critter.dto_converters;

import com.udacity.jdnd.course3.critter.dtos.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.entities.users.Employee;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import com.udacity.jdnd.course3.critter.repositories.ScheduleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ScheduleDTOConverter {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PetRepository petRepository;



    public ScheduleDTO convertEntityToDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        List<Long> listOfEmployeeIDs = schedule.getEmployees()
                .stream()
                .map(Employee::getId)
                .collect(toList());

        List<Long> listOfPetIDs = schedule.getPets()
                .stream()
                .map(Pet::getId)
                .collect(toList());

        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(schedule.getSkills());
        scheduleDTO.setEmployeeIds(listOfEmployeeIDs);
        scheduleDTO.setPetIds(listOfPetIDs);

        return scheduleDTO;
    }

    public Schedule convertDTOToEntity(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();

        BeanUtils.copyProperties(scheduleDTO, schedule);

        if (scheduleDTO.getDate() != null) {
            schedule.setDate(scheduleDTO.getDate());
        }
        if (scheduleDTO.getActivities() != null) {
            schedule.setSkills(scheduleDTO.getActivities());
        }
        
        if (scheduleDTO.getEmployeeIds() != null && !scheduleDTO.getEmployeeIds().isEmpty()) {
            List<Employee> employees = employeeRepository.findAllById(scheduleDTO.getEmployeeIds());
            schedule.setEmployees(employees);
        }
        
        if (scheduleDTO.getPetIds() != null && !scheduleDTO.getPetIds().isEmpty()) {
            List<Pet> pets = petRepository.findAllById(scheduleDTO.getPetIds());
            schedule.setPets(pets);
        }

        return schedule;
    }

}
