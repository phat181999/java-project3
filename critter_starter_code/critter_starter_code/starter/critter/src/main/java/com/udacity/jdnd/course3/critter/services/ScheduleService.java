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

    public List<Schedule> getAllSchedules(){
        List<Schedule> schedules = scheduleRepository.getAllSchedules();

        return schedules;
    }

    public Schedule getScheduleByID(Long scheduleID){
        Schedule schedule = scheduleRepository.getScheduleByID(scheduleID);

        return schedule;
    }

    public List<Schedule> getAllSchedulesByPetID(Long petID){
        List<Schedule> schedulesByPetID = scheduleRepository.getAllSchedulesByPetID(petID);

        return schedulesByPetID;
    }

    public List<Schedule> getAllSchedulesByEmployeeID(Long employeeID){
        List<Schedule> schedulesByEmployeeID = scheduleRepository.getAllSchedulesByEmployeeID(employeeID);

        return schedulesByEmployeeID;
    }

    public List<Schedule> getAllSchedulesByCustomerID(Long customerID){
        List<Schedule> schedulesByCustomerID = scheduleRepository.getAllSchedulesByCustomerID(customerID);

        return schedulesByCustomerID;
    }

    public Schedule saveSchedule(Schedule schedule){
        Schedule newSchedule = scheduleRepository.save(schedule);

        return newSchedule;
    }
}
