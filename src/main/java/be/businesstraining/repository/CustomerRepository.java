package be.businesstraining.repository;

import java.util.List;

import be.businesstraining.domain.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    // Method filter for the criteria searched from the Front End
    // public List<Customer> findAllByLastNameContainingIgnoreCase(String s);
    public List<Customer> findAllByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCase(String s1, String s2);
}