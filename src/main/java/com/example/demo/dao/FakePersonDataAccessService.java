package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// This class is not a real DB, Check PersonDataAccessService - IT IS POSTGRES

// Can also make it Component, but defining as Repository makes it clear that this is a repository
// Vi må gjøre dette for å fortelle Spring boot å lage en beam slik at vi kan bruke dependency injection.
// Fordi vanlig java vil ikke klare å forstå hva PersonDao er når vi skal bruke denne servicen i PersonService
@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao {

    private static List<Person> DB = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add((new Person(id, person.getName())));
        return 1;
    }

    // Gets the person with the specific ID from DB
    @Override
    public Optional<Person> selectPersonById(UUID id) {
        // Gets access to the DB by stream and we find the person with the ID that we selected (Er som arrow function og veldig LIK JS sin filter)
        return DB.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Person> selectAllPeople() {
        return DB;
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> personMaybe = selectPersonById(id);

        // Delete person if personMaybe has a value!
        if(personMaybe.isEmpty())
            return 0;
        else {
            DB.remove(personMaybe.get());
            return 1;
        }

    }

    @Override
    public int updatePersonData(UUID id, Person person) {
        // selectPersonById is func we defined further up
        return selectPersonById(id).map( p -> {
            int indexOfPersonToUpdate = DB.indexOf(p);
            // If index of person is 0 or bigger we know we found a person
            if (indexOfPersonToUpdate >= 0) {
                DB.set(indexOfPersonToUpdate, new Person(id, person.getName()));
                return 1;
            }
            return 0;
        }).orElse(0);
        // Must have orElse since we MUST return a int!
    }
}
