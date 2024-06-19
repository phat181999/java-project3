package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.dto_converters.CustomerDTOConverter;
import com.udacity.jdnd.course3.critter.dtos.CustomerDTO;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.users.Customer;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.PetService;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetService petService;

    @Autowired
    private CustomerDTOConverter customerDTOConverter;

    @PostMapping
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = customerDTOConverter.toEntity(customerDTO);
        Customer savedCustomer = customerService.saveCustomer(customer);

        CustomerDTO savedCustomerDTO = customerDTOConverter.toDTO(savedCustomer);

        return savedCustomerDTO;
    }

    @GetMapping
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> listOfCustomers = customerService.getAllCustomers();

        List<CustomerDTO> listOfCustomersDTO;

        listOfCustomersDTO = listOfCustomers.stream()
                .map(customerDTOConverter::toDTO)
                .collect(toList());

        return listOfCustomersDTO;

    }

    @GetMapping("/{customerID}")
    public CustomerDTO getCustomer(@PathVariable long customerID) {
        Customer customer = customerService.getCustomerByID(customerID);
        CustomerDTO customerDTO = customerDTOConverter.toDTO(customer);

        return customerDTO;

    }

    @GetMapping("/pet/{petID}")
    public CustomerDTO getOwnerByPet(@PathVariable long petID){
        Customer customerByPetID = customerService.getCustomerByPetID(petID);

        CustomerDTO customerByPetID_DTO = customerDTOConverter.toDTO(customerByPetID);

        return customerByPetID_DTO;
    }


    //Extra endpoints
    @PutMapping("/{customerID}")
    public void updateCustomer(@RequestBody Customer customerToUpdate, @PathVariable Long customerID){

        Customer customer = customerService.getCustomerByID(customerID);

        customer.setFirstName(customerToUpdate.getFirstName());
        customer.setLastName(customerToUpdate.getLastName());
        customer.setPhoneNumber(customerToUpdate.getPhoneNumber());
        customer.setNotes(customerToUpdate.getNotes());
        //customer.setPets(customerToUpdate.getPets());

        customerService.saveCustomer(customer);

    }

    @DeleteMapping("/{customerID}")
    /*@OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value={CascadeType.ALL})*/
    public void deleteCustomerByID(@PathVariable Long customerID){

        Customer customer = customerRepository.getCustomerByID(customerID);

        customerService.deleteCustomerByID(customerID);
    }


}
