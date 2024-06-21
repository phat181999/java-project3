package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.dto_converters.CustomerDTOConverter;
import com.udacity.jdnd.course3.critter.dtos.CustomerDTO;
import com.udacity.jdnd.course3.critter.entities.users.Customer;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerDTOConverter customerDTOConverter;

    @PostMapping
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = customerDTOConverter.convertDTOToCustomer(customerDTO);
        if (customer == null) {
            throw new IllegalArgumentException("Customer must not be null");
        }
        Customer savedCustomer = customerService.saveCustomer(customer);
        if (savedCustomer == null) {
            throw new IllegalArgumentException("Customer must not be null");
        }
        CustomerDTO savedCustomerDTO = customerDTOConverter.convertCustomerToDTO(savedCustomer);

        return savedCustomerDTO;
    }

    @GetMapping
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> listOfCustomers = customerService.getAllCustomers();

        List<CustomerDTO> listOfCustomersDTO;
        if (listOfCustomers == null || listOfCustomers.isEmpty()) {
            return new ArrayList<>();
        }
    
        listOfCustomersDTO = listOfCustomers.stream()
                .map(customerDTOConverter::convertCustomerToDTO)
                .collect(toList());

        return listOfCustomersDTO;

    }

    @GetMapping("/{customerID}")
    public CustomerDTO getCustomer(@PathVariable long customerID) {
        Customer customer = customerService.getCustomerByID(customerID);
        if (customer == null) {
            throw new IllegalArgumentException("Customer with ID " + customerID + " not found");
        }
    
        CustomerDTO customerDTO = customerDTOConverter.convertCustomerToDTO(customer);

        return customerDTO;

    }

    @GetMapping("/pet/{petID}")
    public CustomerDTO getOwnerByPet(@PathVariable long petID){
        Customer customerByPetID = customerService.getCustomerByPetID(petID);
        if (customerByPetID == null) {
            throw new IllegalArgumentException("Customer with ID " + customerByPetID + " not found");
        }
        CustomerDTO customerByPetID_DTO = customerDTOConverter.convertCustomerToDTO(customerByPetID);

        return customerByPetID_DTO;
    }


    @PutMapping("/{customerID}")
    public void updateCustomer(@RequestBody Customer customerToUpdate, @PathVariable Long customerID){
        if (customerToUpdate.getFirstName() == null || customerToUpdate.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("Customer first name cannot be null or empty");
        }
        if (customerToUpdate.getLastName() == null || customerToUpdate.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Customer last name cannot be null or empty");
        }
    
        Customer existingCustomer = customerService.getCustomerByID(customerID);
    
        if (existingCustomer == null) {
            throw new IllegalArgumentException("Customer with ID " + customerID + " not found");
        }
        Customer customer = customerService.getCustomerByID(customerID);

        customer.setFirstName(customerToUpdate.getFirstName());
        customer.setLastName(customerToUpdate.getLastName());
        customer.setPhoneNumber(customerToUpdate.getPhoneNumber());
        customer.setNotes(customerToUpdate.getNotes());

        customerService.saveCustomer(customer);

    }

    @DeleteMapping("/{customerID}")
    public void deleteCustomerByID(@PathVariable Long customerID){

        Customer customer = customerRepository.getCustomerByID(customerID);
        if (customer == null) {
            throw new IllegalArgumentException("Customer with ID " + customerID + " not found");
        }
        customerService.deleteCustomerByID(customerID);
    }


}
