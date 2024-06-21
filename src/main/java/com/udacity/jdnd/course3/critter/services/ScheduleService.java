package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<Schedule> getSchedules(){
        List<Schedule> schedules = scheduleRepository.getSchedules();
        if (schedules == null || schedules.isEmpty()) {
            return List.of(); 
        }
        return schedules;
    }

    public Schedule getScheduleByID(Long scheduleID){
        if (scheduleID == null || scheduleID <= 0) {
            throw new IllegalArgumentException("Schedule ID cannot be null");
        }
        Schedule schedule = scheduleRepository.getScheduleByID(scheduleID);

        return schedule;
    }

    public List<Schedule> getAllSchedulesByPetID(Long petID){
        if (petID == null || petID <= 0) {
            throw new IllegalArgumentException("Pet ID cannot be null");
        }
        List<Schedule> schedulesByPetID = scheduleRepository.getAllSchedulesByPetID(petID);

        return schedulesByPetID;
    }

    public List<Schedule> getAllSchedulesByEmployeeID(Long employeeID){
        if (employeeID == null || employeeID <= 0) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        List<Schedule> schedulesByEmployeeID = scheduleRepository.getAllSchedulesByEmployeeID(employeeID);

        return schedulesByEmployeeID;
    }

    public List<Schedule> getAllSchedulesByCustomerID(Long customerID){
        if (customerID == null || customerID <= 0) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }
        List<Schedule> schedulesByCustomerID = scheduleRepository.getAllSchedulesByCustomerID(customerID);

        return schedulesByCustomerID;
    }

    public Schedule saveSchedule(Schedule schedule){
        if (schedule == null) {
            throw new IllegalArgumentException("Schedule ID cannot be null");
        }
        Schedule newSchedule = scheduleRepository.save(schedule);

        return newSchedule;
    }
}
