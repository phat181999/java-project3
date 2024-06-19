package com.udacity.jdnd.course3.critter.repositories;

import com.udacity.jdnd.course3.critter.entities.users.Employee;
import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    //Returns a list of all employees
    @Query("Select e from Employee e")
    List<Employee> getAllEmployees();

    //Gets an employee by their ID
    @Query("Select e from Employee e where e.id = :employeeID")
    Employee getEmployeeByID(@Param("employeeID") Long employeeID);

    //Finds employees available on a certain day of the week
    @Query(
        "SELECT e from Employee e" +
        " WHERE :dayOfWeek MEMBER OF e.employeeDaysOfWeekAvailable"
    )
    List<Employee> getAvailableEmployeesByDOW(@Param("dayOfWeek") DayOfWeek dayOfWeek);

    //Search for employees whose last name starts with a certain letter
    /*@Query("Select e from Employee e " +
            "WHERE e.lastName like :lastName%")
    List<Employee> getEmployeeByLastNameStartsWith(@Param("firstLetter") String firstLetter);*/

}
