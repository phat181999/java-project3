package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.dto_converters.PetDTOConverter;
import com.udacity.jdnd.course3.critter.dtos.PetDTO;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.services.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/pets")
public class PetController {

    Logger logger = LoggerFactory.getLogger(PetController.class);

    @Autowired
    private PetService petService;

    @Autowired
    private PetDTOConverter petDTOConverter;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        if (petDTO.getName() == null || petDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Pet name cannot be null or empty");
        }
        if (petDTO.getType() == null ) {
            throw new IllegalArgumentException("Pet type cannot be null or empty");
        }
        Pet pet = petDTOConverter.convertDTOToEntity(petDTO);

        Pet savedPet = petService.savePet(pet);

        PetDTO savedPetDTO = petDTOConverter.convertEntityToDTO(savedPet);
        savedPetDTO.setType(savedPetDTO.getType());

        return savedPetDTO;
    }

    @GetMapping("/{petID}")
    public PetDTO getPet(@PathVariable long petID) {
        Pet pet = petService.getPetByID(petID);
        if (pet == null) {
            throw new IllegalArgumentException("Pet with ID " + petID + " not found");
        }
        PetDTO petDTO = petDTOConverter.convertEntityToDTO(pet);

        return petDTO;
    }

    @GetMapping
    public List<PetDTO> getAllPets(){
        List<Pet> listOfAllPets = petService.getAllPets();
        if (listOfAllPets == null || listOfAllPets.isEmpty()) {
            throw new IllegalArgumentException("No pets found");
        }
    
        List<PetDTO> listOfAllPetsDTO = listOfAllPets.stream()
                .map(petDTOConverter::convertEntityToDTO)
                .collect(toList());

        return listOfAllPetsDTO;
    }

    @GetMapping("/owner/{customerID}")
    public List<PetDTO> getPetsByOwner(@PathVariable Long customerID) {
        List<Pet> listOfPetsByCustomerID = petService.getAllPetsByCustomerID(customerID);
        if (listOfPetsByCustomerID == null || listOfPetsByCustomerID.isEmpty()) {
            throw new IllegalArgumentException("No pets found for customer with ID " + customerID);
        }
        List<PetDTO> listOfPetsByCustomerID_DTO = listOfPetsByCustomerID.stream()
                .map(petDTOConverter::convertEntityToDTO)
                .collect(toList());

        return listOfPetsByCustomerID_DTO;
    }


    @DeleteMapping("/{petID}")
    public void deletePetByID(@PathVariable Long petID){

        Pet pet = petService.getPetByID(petID);
        if (pet == null) {
            throw new IllegalArgumentException("Pet with ID " + petID + " not found");
        }
        petService.deletePetByID(petID);


    }


}
