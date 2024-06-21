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

 
    public PetDTO convertEntityToDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        if (pet.getCustomer() != null) {
            Long customerId = pet.getCustomer().getId();
            BeanUtils.copyProperties(pet, petDTO, "customer", "petType");
            petDTO.setOwnerId(customerId);
        }

        if (pet.getPetType() != null) {
            petDTO.setType(pet.getPetType());
        }

        return petDTO;
    }

  
    public Pet convertDTOToEntity(PetDTO petDTO){
        Pet pet = new Pet();
        Long customerId = petDTO.getOwnerId();

        BeanUtils.copyProperties(petDTO, pet, "ownerId", "type");

        if (customerId != null) {
            Customer customer = customerService.getCustomerByID(customerId);
            pet.setCustomer(customer);
        }

        if (petDTO.getType() != null) {
            pet.setPetType(petDTO.getType());
        }

        return pet;
    }
}
