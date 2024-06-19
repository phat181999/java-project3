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
    private CustomerService customerService;

    public List<Pet> getAllPets() {
        return petRepository.getAllPets();
    }

    public Pet getPetByID(Long petID) {
        return petRepository.getPetByID(petID);
    }

    public List<Pet> getAllPetsByCustomerID(Long customerID) {
        if (customerID == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }
    
        List<Pet> pets = petRepository.getAllPetsByCustomerID(customerID);
        if (pets == null || pets.isEmpty()) {
            throw new RuntimeException("No pets found for Customer ID: " + customerID);
        }
    
        return pets;
    }

    public Pet savePet(Pet pet) {
        if (pet == null) {
            throw new IllegalArgumentException("Pet cannot be null");
        }
    
        Pet savedPet = petRepository.save(pet);
        Customer customer = savedPet.getCustomer();
        if (customer == null) {
            throw new RuntimeException("Customer not found for the pet");
        }
    
        customerService.addPetToCustomer(savedPet, customer);
    
        return savedPet;
    }

    public void deletePetByID(Long petID) {
        if (petID == null) {
            throw new IllegalArgumentException("Pet ID cannot be null");
        }
    
        Pet pet = petRepository.getPetByID(petID);
        if (pet == null) {
            throw new RuntimeException("Pet not found with ID: " + petID);
        }
    
        petRepository.delete(pet);
    }
}
