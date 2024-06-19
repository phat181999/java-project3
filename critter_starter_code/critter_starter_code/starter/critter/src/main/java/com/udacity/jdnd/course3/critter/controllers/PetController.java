package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.dto_converters.PetDTOConverter;
import com.udacity.jdnd.course3.critter.dtos.PetDTO;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.users.Customer;
import com.udacity.jdnd.course3.critter.enums.PetType;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pets")
public class PetController {

    Logger logger = LoggerFactory.getLogger(PetController.class);

    @Autowired
    private PetService petService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PetDTOConverter petDTOConverter;

    /**
     * Method for saving a pet
     *
     * Either adding a new pet or updating an existing one
     *
     * -------------------------------------------------------------------------------
     * Steps:
     *
     * 1. The provided PetDTO argument is converted to a Pet entity instance
     *
     * 2. The pet is saved in the database via the savePet() method in PetService
     *
     * 3. The saved Pet entity instance is converted into a DTO
     *
     * @param petDTO, The pet to save
     *
     * @return savedPetDTO, The saved pet instance as a DTO
     */
    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {

        Pet pet = petDTOConverter.convertDTOToPet(petDTO);

        Pet savedPet = petService.savePet(pet);

        PetDTO savedPetDTO = petDTOConverter.convertPetToDTO(savedPet);
        savedPetDTO.setType(savedPetDTO.getType());

        return savedPetDTO;
    }

    @GetMapping("/{petID}")
    public PetDTO getPet(@PathVariable long petID) {
        Pet pet = petService.getPetByID(petID);

        PetDTO petDTO = petDTOConverter.convertPetToDTO(pet);

        return petDTO;
    }

    @GetMapping
    public List<PetDTO> getAllPets(){
        List<Pet> listOfAllPets = petService.getAllPets();

        List<PetDTO> listOfAllPetsDTO = listOfAllPets.stream()
                .map(petDTOConverter::convertPetToDTO)
                .collect(toList());

        return listOfAllPetsDTO;
    }

    @GetMapping("/owner/{customerID}")
    public List<PetDTO> getPetsByOwner(@PathVariable Long customerID) {
        List<Pet> listOfPetsByCustomerID = petService.getAllPetsByCustomerID(customerID);

        List<PetDTO> listOfPetsByCustomerID_DTO = listOfPetsByCustomerID.stream()
                .map(petDTOConverter::convertPetToDTO)
                .collect(toList());

        return listOfPetsByCustomerID_DTO;
    }

    /*@PutMapping("/{petID}")
    public PetDTO updatePet(@RequestBody Pet petToUpdate, @PathVariable Long petID){

        Pet pet = petService.getPetByID(petID);

        PetType petType = petToUpdate.getPetType();
        logger.info("Pet Type: " + petType);
        logger.info("Pet to Update: " + petToUpdate);

        String petName = petToUpdate.getName();
        LocalDate birthDate = petToUpdate.getBirthDate();
        String petNotes = petToUpdate.getNotes();
        Customer petOwner = petToUpdate.getCustomer();

        pet.setName(petName);
        pet.setBirthDate(birthDate);
        pet.setNotes(petNotes);
        pet.setPetType(petType);

        petService.savePet(pet);

    }*/

    @DeleteMapping("/{petID}")
    /*@OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value={CascadeType.ALL})*/
    public void deletePetByID(@PathVariable Long petID){

        Pet pet = petService.getPetByID(petID);

        petService.deletePetByID(petID);


    }


}
