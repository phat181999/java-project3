package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.users.Customer;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
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
    public List<Customer> getCustomers(){
        List<Customer> customers = customerRepository.getCustomers();
        if (customers == null || customers.isEmpty()) {
            throw new RuntimeException("No customers found");
        }
        return customers;
    }

    public Customer getCustomerByID(Long customerID){
        if (customerID == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }

        Customer customer = customerRepository.getCustomerByID(customerID);
        if (customer == null) {
            throw new RuntimeException("Customer not found with ID: " + customerID);
        }

        return customer;
    }

    public Customer getCustomerByPetID(Long petID){
        if (petID == null) {
            throw new IllegalArgumentException("Pet ID cannot be null");
        }

        Customer customer = customerRepository.getCustomerByPetID(petID);
        if (customer == null) {
            throw new RuntimeException("Customer not found for Pet ID: " + petID);
        }

        return customer;
    }

    public Customer createNewUser(Customer customer){

        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        return customerRepository.save(customer);
    }

    public void deleteCustomerByID(Long customerID){

        if (customerID == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }

        Customer customer = customerRepository.getCustomerByID(customerID);
        if (customer == null) {
            throw new RuntimeException("Customer not found with ID: " + customerID);
        }

        customerRepository.delete(customer);
    }

    public void addPetToCustomer(Pet pet, Customer customer){

        if (pet == null) {
            throw new IllegalArgumentException("Pet cannot be null");
        }
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        List<Pet> listOfCustomerPets = customer.getPets();

        if (listOfCustomerPets == null) {
            listOfCustomerPets = new ArrayList<>();
        }

        listOfCustomerPets.add(pet);

        customer.setPets(listOfCustomerPets);
        customerRepository.save(customer);

    }


    public List<Long> generatePetIDListForCustomerDTO(Customer customer){
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        List<Long> petIDs;
        List<Pet> listOfPetsByCustomer = customer.getPets();

        //Assembles a list of pet IDs
        if (listOfPetsByCustomer != null && !listOfPetsByCustomer.isEmpty()) {
            petIDs = listOfPetsByCustomer
                    .stream()
                    .map(Pet::getId)
                    .collect(toList());
        } else {
            petIDs = new ArrayList<>();
        }

        return petIDs;
    }

}
