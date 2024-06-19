package com.udacity.jdnd.course3.critter.dtos;

import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.Set;

/**
 * Represents the form that employee request and response data takes. Does not map
 * to the database directly.
 */
@Data
public class EmployeeDTO {
    private long id;
    private String firstName;
    private String lastName;
    private Set<EmployeeSkill> skills;
    private Set<DayOfWeek> daysAvailable;
}
