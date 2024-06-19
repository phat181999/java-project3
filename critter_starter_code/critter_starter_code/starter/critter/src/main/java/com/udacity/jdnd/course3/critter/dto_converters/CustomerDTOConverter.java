package com.udacity.jdnd.course3.critter.dto_converters;

import com.udacity.jdnd.course3.critter.dtos.CustomerDTO;
import com.udacity.jdnd.course3.critter.entities.users.Customer;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerDTOConverter {

    @Autowired
    private PetService petService;

    @Autowired
    private CustomerService customerService;

    /**
     * Method that:
     *
     * -Converts a Customer object to a CustomerDTO
     * -Links pets to a specific CustomerDTO instance
     *
     * @param customer
     * @return customerDTO, the customer object in DTO form
     */
    public CustomerDTO convertCustomerToDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();

        BeanUtils.copyProperties(customer, customerDTO);

        /*
        Invokes the helper method below and adds pets to the CustomerDTO object
         */
        List<Long> petIDs = customerService.generatePetIDListForCustomerDTO(customer);
        customerDTO.setPetIds(petIDs);

        return customerDTO;
    }

    /**
     * Converts a CustomerDTO object to a Customer
     *
     * @param customerDTO
     * @return customer, the Customer object
     */
    public Customer convertDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();

        BeanUtils.copyProperties(customerDTO, customer);

        return customer;

    }

}
