package com.example.demo.api;

import com.example.demo.model.Person;
import com.example.demo.model.response.PersonResponse;
import com.example.demo.service.PersonService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

// Spring boot usually labels api as 'controllers'

@RequestMapping("api/v1/person") // THIS - is the url needed to send the request to this controller! Eks: localhost:8080/api/v1/person
// Represents a RESTController, which makes the user able to send request to backend with POST,PUT,DELETE
@RestController
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // Defines this as POST (NOTE! Use postman to test!) når du ikke har en client(react, android ...) du kan teste på
    @PostMapping
    // RequestBody says that we WANT to take the RequestBody(from client) and turn that to a Person object
    public PersonResponse addPerson(@Valid @NonNull @RequestBody Person person) {
        personService.addPerson(person);

        PersonResponse personResponse = new PersonResponse();

        BeanUtils.copyProperties(person, personResponse);

        return personResponse;
    }

    // GET
    @GetMapping
    public List<Person> getAllPeople() {
        return personService.getAllPeople();
    }

    // GET
    @GetMapping(path = "{id}") // This allows after url defined in line 13 to have api/v1/person/{ID_HERE}
    // PathVariable annontation makes so that the path defined in GetMapping is pointing to that specific one!
    public Person getPersonById(@PathVariable("id") UUID id) {
        return personService.getPersonById(id)
                .orElse(null); // Here we can have exception like 404 user not found | We get this if the id dont exist
    }

    // DELETE
    @DeleteMapping(path = "{id}")
    public void deletePersonById(@PathVariable("id") UUID id) {
        personService.deletePerson(id);
    }

    // UPDATE
    @PutMapping(path = "{id}")
    public void updatePerson(@PathVariable("id") UUID id, @Valid @NonNull @RequestBody Person personToUpdate) {
        personService.updatePerson(id, personToUpdate);
    }
}
