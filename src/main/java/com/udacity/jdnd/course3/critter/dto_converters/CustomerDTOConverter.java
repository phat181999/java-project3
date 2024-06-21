package com.udacity.jdnd.course3.critter.dto_converters;

import com.udacity.jdnd.course3.critter.dtos.CustomerDTO;
import com.udacity.jdnd.course3.critter.entities.users.Customer;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerDTOConverter {


    @Autowired
    private CustomerService customerService;

    public CustomerDTO convertCustomerToDTO(Customer customer){
        if (customer == null) {
            throw new IllegalArgumentException("Customer must not be null");
        }
    
        CustomerDTO customerDTO = new CustomerDTO();
    
        BeanUtils.copyProperties(customer, customerDTO);
    
        List<Long> petIDs = null;
        try {
            petIDs = customerService.generatePetIDListForCustomerDTO(customer);
        } catch (Exception e) {
            throw new RuntimeException("Error generating pet IDs for customer: " + customer.getId(), e);
        }
        customerDTO.setPetIds(petIDs);
    
        return customerDTO;
    }

    public Customer convertDTOToCustomer(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            throw new IllegalArgumentException("CustomerDTO must not be null");
        }
    
        Customer customer = new Customer();
    
        BeanUtils.copyProperties(customerDTO, customer);
    
        return customer;

    }

}
