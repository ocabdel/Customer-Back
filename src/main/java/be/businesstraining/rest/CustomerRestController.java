package be.businesstraining.rest;

import be.businesstraining.domain.Customer;
import be.businesstraining.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/customers")
@CrossOrigin(origins = "*") // Quels sont les clients autorisés pour communiquer avec le Server 'cross domain => cors'
// requête spécial envoyée par le navigateur du service pour savoir s'il a accès (* -> pour tout le monde')
// sinon il faut utiliser l'url suivant 'http://localhost:4200' uniquement pour le client Angular
public class CustomerRestController {

    private CustomerRepository repository;

    @Autowired
    public CustomerRestController(CustomerRepository repository) {

        this.repository = repository;
    }

    @GetMapping
    public Iterable<Customer> allTheCustomers() {
        return repository.findAll();
    }

    @GetMapping("/{id}") // Chemin effectif /customers/2
    public ResponseEntity<Customer> findById(@PathVariable(name = "id") Long id) {
        Optional<Customer> result = repository.findById(id);
        return (result.isPresent())?
                new ResponseEntity<>(result.get(), HttpStatus.OK):
                new ResponseEntity<>(HttpStatus.NOT_FOUND);  // retourne le code 404 page not found!

        //return repository.findById(id).orElse(null);
    }

    // REST API Design best practices
    @GetMapping(params = "namePart") // Chemin effectif /customers?namePart=chris
    public List<Customer> findByNameContaining(
            @PathParam(value = "namePart") String namePart) {

        return repository.findAllByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCase(namePart, namePart);
    }

}
