package com.example.demo.dao;

import com.example.demo.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Definerer hva som er lov til å gjør med Person i database
public interface PersonDao {

    // function that we expect to return int
    int insertPerson(UUID id, Person person);

    default int insertPerson(Person person) {
        UUID id = UUID.randomUUID();
        return insertPerson(id, person);
    }

    Optional<Person> selectPersonById(UUID id);

    List<Person> selectAllPeople();

    int deletePersonById(UUID id);

    int updatePersonData(UUID id, Person person);
}
