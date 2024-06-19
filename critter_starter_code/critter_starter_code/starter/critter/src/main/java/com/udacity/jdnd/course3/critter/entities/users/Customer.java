package com.udacity.jdnd.course3.critter.entities.users;

import com.udacity.jdnd.course3.critter.entities.Pet;
import lombok.Data;
import org.hibernate.annotations.Nationalized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Customer {

    @Id
    /*@SequenceGenerator(name="customer_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_sequence")*/
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    private String firstName;

    @Nationalized
    private String lastName;

    private String phoneNumber;
    private String notes;

    //Defines a one to many relationship between Customer and Pet
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Pet> pets;

    private static final Logger logger = LoggerFactory.getLogger(Customer.class);


}
