package com.udacity.jdnd.course3.critter;

import com.google.common.collect.Sets;
import com.udacity.jdnd.course3.critter.controllers.CustomerController;
import com.udacity.jdnd.course3.critter.controllers.EmployeeController;
import com.udacity.jdnd.course3.critter.controllers.PetController;
import com.udacity.jdnd.course3.critter.dtos.*;
import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.enums.PetType;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest(classes = CritterApplication.class)
public class CritterFunctionalTest {

    @Autowired
    private PetController petController;


    @Autowired
    private CustomerController customerController;

    @Autowired
    private EmployeeController employeeController;

    private final Logger logger = LoggerFactory.getLogger(CritterFunctionalTest.class);

    @Test
    public void testCreateCustomer() {
        CustomerDTO customerDTO = createCustomerDTO();
        
        CustomerDTO newCustomer = customerController.createNewUser(customerDTO);
        
        CustomerDTO retrievedCustomer = customerController.getCustomer(newCustomer.getId());
    
        assertEquals(newCustomer.getFirstName(), customerDTO.getFirstName());
        assertEquals(newCustomer.getLastName(), customerDTO.getLastName());
        assertEquals(newCustomer.getId(), retrievedCustomer.getId());
        assertTrue(retrievedCustomer.getId() > 0);
    
        assertEquals(newCustomer.getPhoneNumber(), customerDTO.getPhoneNumber()); 
    
        CustomerDTO nonExistentCustomer = customerController.getCustomer(newCustomer.getId() + 1);
        assertNull(nonExistentCustomer);
    
        CustomerDTO nullCustomerDTO = new CustomerDTO();
        assertThrows(Exception.class, () -> customerController.createNewUser(nullCustomerDTO));
    
        logger.info("Customer ID: " + newCustomer.getId());
    }
    

    @Test
    public void testCreateEmployee() {
        EmployeeDTO employeeDTO = createEmployeeDTO();

        EmployeeDTO newEmployee = employeeController.saveEmployee(employeeDTO);

        EmployeeDTO retrievedEmployee = employeeController.getEmployee(newEmployee.getId());

        assertEquals(employeeDTO.getSkills(), newEmployee.getSkills());
        assertEquals(employeeDTO.getFirstName(), newEmployee.getFirstName());
        assertEquals(employeeDTO.getLastName(), newEmployee.getLastName());
        assertEquals(newEmployee.getId(), retrievedEmployee.getId());
        assertTrue(retrievedEmployee.getId() > 0);

        assertTrue(!retrievedEmployee.getSkills().isEmpty()); 

        EmployeeDTO nonExistentEmployee = employeeController.getEmployee(newEmployee.getId() + 1);
        assertNull(nonExistentEmployee);

        EmployeeDTO nullEmployeeDTO = new EmployeeDTO();
        assertThrows(Exception.class, () -> employeeController.saveEmployee(nullEmployeeDTO));


        logger.info("Employee ID: " + newEmployee.getId());
    }

    @Test
    public void testAddPetsToCustomer() {
        CustomerDTO customerDTO = createCustomerDTO();
        CustomerDTO newCustomer = customerController.createNewUser(customerDTO);

        PetDTO petDTO = createPetDTO();
        petDTO.setOwnerId(newCustomer.getId());
        PetDTO newPet = petController.createNewPet(petDTO);

        PetDTO retrievedPet = petController.getPet(newPet.getId());
        assertEquals(retrievedPet.getId(), newPet.getId());
        assertEquals(retrievedPet.getOwnerId(), newCustomer.getId());

        List<PetDTO> petsByOwner = petController.getPetsByCustomer(newCustomer.getId());
        assertEquals(1, petsByOwner.size());
        assertEquals(newPet.getId(), petsByOwner.get(0).getId());
        assertEquals(newPet.getName(), petsByOwner.get(0).getName());

        CustomerDTO retrievedCustomer = customerController.getCustomer(newCustomer.getId());
        assertTrue(retrievedCustomer.getPetIds() != null && !retrievedCustomer.getPetIds().isEmpty());
        assertEquals(1, retrievedCustomer.getPetIds().size());
        assertEquals(newPet.getId(), retrievedCustomer.getPetIds().get(0));

        List<PetDTO> petsForNonExistentOwner = petController.getPetsByCustomer(newCustomer.getId() + 1);
        assertTrue(petsForNonExistentOwner.isEmpty());

        CustomerDTO customerWithNoPetsDTO = createCustomerDTO();
        CustomerDTO customerWithNoPets = customerController.createNewUser(customerWithNoPetsDTO);
        List<PetDTO> petsForCustomerWithNoPets = petController.getPetsByCustomer(customerWithNoPets.getId());
            assertTrue(petsForCustomerWithNoPets.isEmpty());
    }

    @Test
    public void testFindPetsByOwner() {
        CustomerDTO customerDTO = createCustomerDTO();
        CustomerDTO newCustomer = customerController.createNewUser(customerDTO);

        PetDTO petDTO = createPetDTO();
        petDTO.setOwnerId(newCustomer.getId());
        PetDTO newPet = petController.createNewPet(petDTO);

        PetDTO anotherPetDTO = createPetDTO();
        anotherPetDTO.setOwnerId(newCustomer.getId());
        anotherPetDTO.setType(PetType.DOG);
        anotherPetDTO.setName("DogName");
        PetDTO anotherPet = petController.createNewPet(anotherPetDTO);

        List<PetDTO> pets = petController.getPetsByCustomer(newCustomer.getId());
        assertEquals(2, pets.size());

        assertEquals(newPet.getId(), pets.get(0).getId());
        assertEquals(newPet.getOwnerId(), newCustomer.getId());

        assertEquals(anotherPet.getId(), pets.get(1).getId());
        assertEquals(anotherPet.getOwnerId(), newCustomer.getId());

        List<PetDTO> petsForNonExistentOwner = petController.getPetsByCustomer(newCustomer.getId() + 1);
        assertTrue(petsForNonExistentOwner.isEmpty());

        CustomerDTO anotherCustomerDTO = createCustomerDTO();
        CustomerDTO anotherCustomer = customerController.createNewUser(anotherCustomerDTO);
        PetDTO thirdPetDTO = createPetDTO();
        thirdPetDTO.setOwnerId(anotherCustomer.getId());

    }


    private static EmployeeDTO createEmployeeDTO() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("Test");
        employeeDTO.setLastName("Employee");
        employeeDTO.setSkills(Sets.newHashSet(EmployeeSkill.FEEDING, EmployeeSkill.PETTING));
        return employeeDTO;
    }
    private static CustomerDTO createCustomerDTO() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Test");
        customerDTO.setLastName("Employee");
        customerDTO.setPhoneNumber("123-456-789");
        return customerDTO;
    }

    private static PetDTO createPetDTO() {
        PetDTO petDTO = new PetDTO();
        petDTO.setName("TestPet");
        petDTO.setType(PetType.CAT);

        return petDTO;
    }
}
