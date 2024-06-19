package com.udacity.jdnd.course3.critter.entities;

import com.fasterxml.jackson.annotation.JsonValue;
import com.udacity.jdnd.course3.critter.entities.users.Customer;
import com.udacity.jdnd.course3.critter.enums.PetType;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Pet {

    @Id
    /*@SequenceGenerator(name="pet_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pet_sequence")*/
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    private String name;

    private PetType petType;

    private LocalDate birthDate;

    private String notes;

    /*
    Declares the Pet end of the "one to many" relationship with Customer

    Adds customer_id as a foreign key in Pet
    which references the id in Customer
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
