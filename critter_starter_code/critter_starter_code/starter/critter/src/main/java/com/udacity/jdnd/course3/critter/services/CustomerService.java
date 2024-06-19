package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.users.Customer;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    public List<Customer> getAllCustomers(){
        return customerRepository.getAllCustomers();
    }

    //Fetches a customer by customer ID
    public Customer getCustomerByID(Long customerID){
        Customer customer = customerRepository.getCustomerByID(customerID);

        return customer;
    }

    //Returns a customer based on the pet ID
    public Customer getCustomerByPetID(Long petID){
        Customer customer = customerRepository.getCustomerByPetID(petID);

        return customer;
    }

    public Customer saveCustomer(Customer customer){

        Customer newCustomer = customerRepository.save(customer);

        return newCustomer;
    }

    public void deleteCustomerByID(Long customerID){

        Customer customer = customerRepository.getCustomerByID(customerID);

        customerRepository.delete(customer);
    }

    /**
     * Helper method that adds a pet to a customer's list of pets
     *
     * Used for working with Customer entity instances
     *
     * @param pet, The pet to add
     * @param customer, The pet's owner
     */
    public void addPetToCustomer(Pet pet, Customer customer){

        List<Pet> listOfCustomerPets = customer.getPets();

        /*
        Logic for setting up & populating the list of the
        specified customer's pets
         */
        if(listOfCustomerPets == null){
            listOfCustomerPets = new ArrayList<>();
        }

        listOfCustomerPets.add(pet);

        //Propagates the pet list changes to the Customer instance
        customer.setPets(listOfCustomerPets);
        customerRepository.save(customer);

    }

    /**
     * Helper method that creates a list of pet IDs
     * based on the pets a customer owns
     *
     * Invoked with CustomerDTO instances
     *
     * @param customer, The specific customer to evaluate
     * @return petIDs, The list of pet IDs connected to the customer
     */
    public List<Long> generatePetIDListForCustomerDTO(Customer customer){
        List<Long> petIDs;

        List<Pet> listOfPetsByCustomer = customer.getPets();

        //Assembles a list of pet IDs
        if(listOfPetsByCustomer != null){
            petIDs = listOfPetsByCustomer
                    .stream()
                    .map(Pet::getId)
                    .collect(toList());
        }

        else {
            petIDs = new ArrayList<>();
        }

        return petIDs;
    }

}
