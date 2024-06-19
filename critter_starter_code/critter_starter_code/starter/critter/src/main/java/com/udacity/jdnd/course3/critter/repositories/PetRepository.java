package com.udacity.jdnd.course3.critter.repositories;

import com.udacity.jdnd.course3.critter.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    //Returns a list of all pets
    @Query("Select p from Pet p")
    List<Pet> getAllPets();

    //Gets a pet by its ID
    @Query("Select p from Pet p where p.id = :petID")
    Pet getPetByID(@Param("petID") Long petID);

    //Fetches the pets associated with a specific customer/owner
    @Query("Select p from Pet p where p.customer.id = :customerID")
    List<Pet> getAllPetsByCustomerID(@Param("customerID") Long customerID);


}
