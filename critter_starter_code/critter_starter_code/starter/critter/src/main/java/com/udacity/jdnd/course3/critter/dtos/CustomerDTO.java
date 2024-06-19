package com.udacity.jdnd.course3.critter.dtos;

import lombok.Data;

import java.util.List;


@Data
public class CustomerDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String notes;
    private List<Long> petIds;

}
