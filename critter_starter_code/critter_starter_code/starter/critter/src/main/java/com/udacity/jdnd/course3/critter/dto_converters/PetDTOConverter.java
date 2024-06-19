package com.udacity.jdnd.course3.critter.dto_converters;

import com.udacity.jdnd.course3.critter.dtos.PetDTO;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.users.Customer;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PetDTOConverter {

    @Autowired
    private CustomerService customerService;

    /**
     * -Converts a Pet object to a PetDTO
     * -Links a customer with the specified PetDTO instance
     *
     * @param pet
     * @return petDTO, the pet object in DTO form
     */
    public PetDTO convertPetToDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        Long customerID = pet.getCustomer().getId();

        BeanUtils.copyProperties(pet, petDTO, "customer", "petType");

        //Adds the customer to the PetDTO instance
        Customer customer = customerService.getCustomerByID(customerID);
        petDTO.setOwnerId(customer.getId());
        petDTO.setType(pet.getPetType());

        return petDTO;
    }

    /**
     * -Converts a PetDTO object to a Pet
     * -Links a customer with the specified Pet instance
     *
     * @param petDTO
     * @return pet, The object in Pet form
     */
    public Pet convertDTOToPet(PetDTO petDTO){
        Pet pet = new Pet();
        Long customerID = petDTO.getOwnerId();

        BeanUtils.copyProperties(petDTO, pet, "ownerId", "type");

        //Adds the customer to the Pet instance
        Customer customer = customerService.getCustomerByID(customerID);
        pet.setCustomer(customer);
        pet.setPetType(petDTO.getType());

        return pet;
    }
}
