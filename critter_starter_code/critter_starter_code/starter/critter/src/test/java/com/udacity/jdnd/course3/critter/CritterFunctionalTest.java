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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        
        CustomerDTO newCustomer = customerController.saveCustomer(customerDTO);
        
        CustomerDTO retrievedCustomer = customerController.getCustomer(newCustomer.getId());
    
        assertEquals(newCustomer.getFirstName(), customerDTO.getFirstName());
        assertEquals(newCustomer.getLastName(), customerDTO.getLastName());
        assertEquals(newCustomer.getId(), retrievedCustomer.getId());
        assertTrue(retrievedCustomer.getId() > 0);
    
        assertEquals(newCustomer.getPhoneNumber(), customerDTO.getPhoneNumber()); 
    
        CustomerDTO nonExistentCustomer = customerController.getCustomer(newCustomer.getId() + 1);
        assertNull(nonExistentCustomer);
    
        CustomerDTO nullCustomerDTO = new CustomerDTO();
        assertThrows(Exception.class, () -> customerController.saveCustomer(nullCustomerDTO));
    
        List<CustomerDTO> customerList = customerController.getAllCustomers();
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

        List<EmployeeDTO> employeeList = employeeController.getAllEmployees();

        logger.info("Employee ID: " + newEmployee.getId());
    }

    @Test
    public void testAddPetsToCustomer() {
        CustomerDTO customerDTO = createCustomerDTO();
        CustomerDTO newCustomer = customerController.saveCustomer(customerDTO);

        PetDTO petDTO = createPetDTO();
        petDTO.setOwnerId(newCustomer.getId());
        PetDTO newPet = petController.savePet(petDTO);

        PetDTO retrievedPet = petController.getPet(newPet.getId());
        assertEquals(retrievedPet.getId(), newPet.getId());
        assertEquals(retrievedPet.getOwnerId(), newCustomer.getId());

        List<PetDTO> petsByOwner = petController.getPetsByOwner(newCustomer.getId());
        assertEquals(1, petsByOwner.size());
        assertEquals(newPet.getId(), petsByOwner.get(0).getId());
        assertEquals(newPet.getName(), petsByOwner.get(0).getName());

        CustomerDTO retrievedCustomer = customerController.getCustomer(newCustomer.getId());
        assertTrue(retrievedCustomer.getPetIds() != null && !retrievedCustomer.getPetIds().isEmpty());
        assertEquals(1, retrievedCustomer.getPetIds().size());
        assertEquals(newPet.getId(), retrievedCustomer.getPetIds().get(0));

        List<PetDTO> petsForNonExistentOwner = petController.getPetsByOwner(newCustomer.getId() + 1);
        assertTrue(petsForNonExistentOwner.isEmpty());

        CustomerDTO customerWithNoPetsDTO = createCustomerDTO();
        CustomerDTO customerWithNoPets = customerController.saveCustomer(customerWithNoPetsDTO);
        List<PetDTO> petsForCustomerWithNoPets = petController.getPetsByOwner(customerWithNoPets.getId());
            assertTrue(petsForCustomerWithNoPets.isEmpty());
    }

    @Test
    public void testFindPetsByOwner() {
    CustomerDTO customerDTO = createCustomerDTO();
    CustomerDTO newCustomer = customerController.saveCustomer(customerDTO);

    PetDTO petDTO = createPetDTO();
    petDTO.setOwnerId(newCustomer.getId());
    PetDTO newPet = petController.savePet(petDTO);

    PetDTO anotherPetDTO = createPetDTO();
    anotherPetDTO.setOwnerId(newCustomer.getId());
    anotherPetDTO.setType(PetType.DOG);
    anotherPetDTO.setName("DogName");
    PetDTO anotherPet = petController.savePet(anotherPetDTO);

    List<PetDTO> pets = petController.getPetsByOwner(newCustomer.getId());
    assertEquals(2, pets.size());

    assertEquals(newPet.getId(), pets.get(0).getId());
    assertEquals(newPet.getOwnerId(), newCustomer.getId());

    assertEquals(anotherPet.getId(), pets.get(1).getId());
    assertEquals(anotherPet.getOwnerId(), newCustomer.getId());

    List<PetDTO> petsForNonExistentOwner = petController.getPetsByOwner(newCustomer.getId() + 1);
    assertTrue(petsForNonExistentOwner.isEmpty());

    CustomerDTO anotherCustomerDTO = createCustomerDTO();
    CustomerDTO anotherCustomer = customerController.saveCustomer(anotherCustomerDTO);
    PetDTO thirdPetDTO = createPetDTO();
    thirdPetDTO.setOwnerId(anotherCustomer.getId());
    PetDTO thirdPet = petController.savePet(thirdPetDTO);

    List<PetDTO> petsForAnotherCustomer = petController.getPetsByOwner(newCustomer.getId());
}

    @Test
    public void testFindOwnerByPet() {
        // Create a new customer and save them
        CustomerDTO customerDTO = createCustomerDTO();
        CustomerDTO newCustomer = customerController.saveCustomer(customerDTO);

        // Create a new pet associated with the customer and save it
        PetDTO petDTO = createPetDTO();
        petDTO.setOwnerId(newCustomer.getId());
        PetDTO newPet = petController.savePet(petDTO);

        // Retrieve the owner of the pet
        CustomerDTO owner = customerController.getOwnerByPet(newPet.getId());

        // Assert that the retrieved owner matches the expected customer
        assertEquals(owner.getId(), newCustomer.getId());
        assertEquals(owner.getPetIds().get(0), newPet.getId());

        // Create another customer and associate a different pet with them
        CustomerDTO anotherCustomerDTO = createCustomerDTO();
        CustomerDTO anotherCustomer = customerController.saveCustomer(anotherCustomerDTO);

        PetDTO anotherPetDTO = createPetDTO();
        anotherPetDTO.setOwnerId(anotherCustomer.getId());
        PetDTO anotherPet = petController.savePet(anotherPetDTO);

        // Retrieve the owner of the second pet
        CustomerDTO anotherOwner = customerController.getOwnerByPet(anotherPet.getId());

        // Assert that the retrieved owner matches the expected another customer
        assertEquals(anotherOwner.getId(), anotherCustomer.getId());
        assertEquals(anotherOwner.getPetIds().get(0), anotherPet.getId());

        // Verify that attempting to retrieve the owner for a non-existent pet returns null
        CustomerDTO nonExistentOwner = customerController.getOwnerByPet(newPet.getId() + 1);
        assertNull(nonExistentOwner);
    }


    @Test
    public void testChangeEmployeeAvailability() {
        // Create a new employee and save them
        EmployeeDTO employeeDTO = createEmployeeDTO();
        EmployeeDTO emp1 = employeeController.saveEmployee(employeeDTO);
        assertNull(emp1.getDaysAvailable());

        // Test scenario: Set availability for an employee
        Set<DayOfWeek> availability = Sets.newHashSet(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY);
        employeeController.setAvailability(availability, emp1.getId());

        // Retrieve the employee after setting availability
        EmployeeDTO emp2 = employeeController.getEmployee(emp1.getId());
        assertEquals(availability, emp2.getDaysAvailable());

        // Test scenario: Change availability for the same employee
        Set<DayOfWeek> newAvailability = Sets.newHashSet(DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
        employeeController.setAvailability(newAvailability, emp1.getId());

        // Retrieve the employee after changing availability
        EmployeeDTO emp3 = employeeController.getEmployee(emp1.getId());
        assertEquals(newAvailability, emp3.getDaysAvailable());

        // Test scenario: Set availability for another employee and ensure it does not affect the first employee
        EmployeeDTO employeeDTO2 = createEmployeeDTO();
        EmployeeDTO emp4 = employeeController.saveEmployee(employeeDTO2);

        Set<DayOfWeek> availability2 = Sets.newHashSet(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY);
        employeeController.setAvailability(availability2, emp4.getId());

        // Retrieve the first employee and assert their availability is still as expected
        EmployeeDTO emp5 = employeeController.getEmployee(emp1.getId());
        assertEquals(newAvailability, emp5.getDaysAvailable());
    }


    @Test
    public void testFindEmployeesByServiceAndTime() {
        // Create and save employees with different availability and skills
        EmployeeDTO emp1 = createEmployeeDTO();
        EmployeeDTO emp2 = createEmployeeDTO();
        EmployeeDTO emp3 = createEmployeeDTO();
    
        emp1.setDaysAvailable(Sets.newHashSet(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));
        emp2.setDaysAvailable(Sets.newHashSet(DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
        emp3.setDaysAvailable(Sets.newHashSet(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY));
    
        emp1.setSkills(Sets.newHashSet(EmployeeSkill.FEEDING, EmployeeSkill.PETTING));
        emp2.setSkills(Sets.newHashSet(EmployeeSkill.PETTING, EmployeeSkill.WALKING));
        emp3.setSkills(Sets.newHashSet(EmployeeSkill.WALKING, EmployeeSkill.SHAVING));
    
        EmployeeDTO emp1n = employeeController.saveEmployee(emp1);
        EmployeeDTO emp2n = employeeController.saveEmployee(emp2);
        EmployeeDTO emp3n = employeeController.saveEmployee(emp3);
    
        // Test scenario: Request that matches employee 1 or employee 2 based on date and skills
        EmployeeRequestDTO er1 = new EmployeeRequestDTO();
        er1.setDate(LocalDate.of(2019, 12, 25)); // Wednesday
        er1.setSkills(Sets.newHashSet(EmployeeSkill.PETTING));
    
        Set<Long> eIds1 = employeeController.findEmployeesForService(er1)
                .stream()
                .map(EmployeeDTO::getId)
                .collect(Collectors.toSet());
        Set<Long> eIds1expected = Sets.newHashSet(emp1n.getId(), emp2n.getId());
        assertEquals(eIds1, eIds1expected);
    
        // Test scenario: Request that matches only employee 3 based on date and skills
        EmployeeRequestDTO er2 = new EmployeeRequestDTO();
        er2.setDate(LocalDate.of(2019, 12, 27)); // Friday
        er2.setSkills(Sets.newHashSet(EmployeeSkill.WALKING, EmployeeSkill.SHAVING));
    
        Set<Long> eIds2 = employeeController.findEmployeesForService(er2)
                .stream()
                .map(EmployeeDTO::getId)
                .collect(Collectors.toSet());
        Set<Long> eIds2expected = Sets.newHashSet(emp3n.getId());
        assertEquals(eIds2, eIds2expected);
    
        // Test scenario: Request that does not match any employee due to skills mismatch
        EmployeeRequestDTO er3 = new EmployeeRequestDTO();
        er3.setDate(LocalDate.of(2019, 12, 26)); // Thursday
        er3.setSkills(Sets.newHashSet(EmployeeSkill.FEEDING));
    
        Set<Long> eIds3 = employeeController.findEmployeesForService(er3)
                .stream()
                .map(EmployeeDTO::getId)
                .collect(Collectors.toSet());
        assertTrue(eIds3.isEmpty());
    
        // Test scenario: Request that does not match any employee due to availability mismatch
        EmployeeRequestDTO er4 = new EmployeeRequestDTO();
        er4.setDate(LocalDate.of(2019, 12, 29)); // Sunday
        er4.setSkills(Sets.newHashSet(EmployeeSkill.PETTING));
    
        Set<Long> eIds4 = employeeController.findEmployeesForService(er4)
                .stream()
                .map(EmployeeDTO::getId)
                .collect(Collectors.toSet());
        assertTrue(eIds4.isEmpty());
    }

    @Test
    public void testFilterEmployeesByLastNamePrefix(){

        //Sample EmployeeDTO instances
        EmployeeDTO employee1DTO = createEmployeeDTO();
        EmployeeDTO employee2DTO = createEmployeeDTO();
        EmployeeDTO employee3DTO = createEmployeeDTO();
        EmployeeDTO employee4DTO = createEmployeeDTO();
        EmployeeDTO employee5DTO = createEmployeeDTO();


        List<EmployeeDTO> testEmployeeDTOs = new ArrayList<>();
        List<Integer> numberOfMatchesList = new ArrayList<>();

        List<EmployeeDTO> listOfEmployeeDTOs = Arrays.asList(employee1DTO, employee2DTO, employee3DTO, employee4DTO, employee5DTO);

        testEmployeeDTOs.addAll(listOfEmployeeDTOs);

        employee3DTO.setLastName("Crawford");
        employee5DTO.setLastName("Chase");

        List<String> lastNamePrefixes = Arrays.asList("C", "c", "Cr", "cR", "cr", "CR");

        for(String lastNamePrefix: lastNamePrefixes){
            int numberOfMatches = (int) testEmployeeDTOs
                    .stream()
                    .filter(e -> e.getLastName().toLowerCase()
                            .startsWith(lastNamePrefix
                                    .toLowerCase())).count();

            numberOfMatchesList.add(numberOfMatches);
        }

        //Assertions that check the first two prefixes (i.e. "C" or "c")
        assertEquals(numberOfMatchesList.get(0), 2);
        assertEquals(numberOfMatchesList.get(1), 2);

        //Assertions that check the remaining prefixes ("Cr")
        assertEquals(numberOfMatchesList.get(2), 1);
        assertEquals(numberOfMatchesList.get(3), 1);
        assertEquals(numberOfMatchesList.get(4), 1);
        assertEquals(numberOfMatchesList.get(5), 1);

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
