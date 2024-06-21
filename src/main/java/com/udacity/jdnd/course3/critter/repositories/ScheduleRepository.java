package com.udacity.jdnd.course3.critter.repositories;

import com.udacity.jdnd.course3.critter.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("Select s from Schedule s")
    List<Schedule> getAllSchedules();

    //Fetches a schedule by its id
    @Query("Select s from Schedule s where s.id = :scheduleID")
    Schedule getScheduleByID(@Param("scheduleID") Long scheduleID);

    @Query(
        "SELECT s from Schedule s" +
        " INNER JOIN s.pets sp" +
        " WHERE sp.id = :petID"
    )
    List<Schedule> getAllSchedulesByPetID(@Param("petID") Long petID);

    @Query(
        "SELECT s from Schedule s" +
        " INNER JOIN s.employees se" +
        " WHERE se.id = :employeeID"
    )
    List<Schedule> getAllSchedulesByEmployeeID(@Param("employeeID") Long employeeID);

    @Query(
        "SELECT s from Schedule s" +
        " INNER JOIN s.pets sp" +
        " INNER JOIN sp.customer c" +
        " WHERE c.id = :customerID"
    )
    List<Schedule> getAllSchedulesByCustomerID(@Param("customerID") Long customerID);



}
