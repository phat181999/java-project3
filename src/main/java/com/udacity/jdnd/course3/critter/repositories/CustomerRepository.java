package com.udacity.jdnd.course3.critter.repositories;

import com.udacity.jdnd.course3.critter.entities.users.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //Returns a list of all customers
    @Query("Select c from Customer c")
    List<Customer> getAllCustomers();

    //Gets a customer based on their ID
    @Query("Select c from Customer c where c.id = :customerID")
    Customer getCustomerByID(@Param("customerID") Long customerID);

    //Finds the customer associated with a specific pet ID
    @Query(
        "SELECT c from Customer c" +
        " INNER JOIN Pet p on c.id = p.customer.id" +
        " WHERE p.id = :petID"
    )
    Customer getCustomerByPetID(@Param("petID") Long petID);

}
