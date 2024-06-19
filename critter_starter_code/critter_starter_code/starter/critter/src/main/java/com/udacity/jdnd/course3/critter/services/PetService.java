package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.users.Customer;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    public List<Pet> getAllPets(){
        List<Pet> pets = petRepository.getAllPets();

        return pets;
    }

    public Pet getPetByID(Long petID){
        Pet pet = petRepository.getPetByID(petID);

        return pet;
    }

    public List<Pet> getAllPetsByCustomerID(Long customerID){
        List<Pet> petsByCustomerID = petRepository.getAllPetsByCustomerID(customerID);

        return petsByCustomerID;
    }

    /**
     * Method that:
     *
     * -Saves pet information via savedPet
     *
     * -Fetches the customer based on the customer ID supplied
     * in the corresponding PetController request body
     *
     * -Adds the saved pet to the customer's pet list
     *
     * -Returns the saved pet instance
     *
     * @param pet, The pet to save
     * @return savedPet
     */
    public Pet savePet(Pet pet){

        Pet savedPet = petRepository.save(pet);
        Customer customer = savedPet.getCustomer();

        customerService.addPetToCustomer(savedPet, customer);

        return savedPet;
    }

    public void deletePetByID(Long petID){

        Pet pet = petRepository.getPetByID(petID);

        petRepository.delete(pet);
    }

}
