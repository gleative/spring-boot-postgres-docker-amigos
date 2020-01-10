package com.example.demo.service;

import com.example.demo.dao.PersonDao;
import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// This will need the Service keyword,
// so the object created by dependency injection in the constructor can be used by this Service!
@Service
public class PersonService {

    // Reference to the interface
    private final PersonDao personDao;

    /**
     * @Qualifier("fakeDao")
     *  - Siden vi kan instansiere interface flere ganger, vil gi denne et spesifikk navn. Denne peker til "FakePersonDataAccessService" merk @Repository har samme navn
     *  - Dette gjør at vi enkelt kan bytte på interfaces, eks du vil ha mongoDb, da bytter du qualifier navn, slik at den peker til riktig repository
     *  - DETTE ER KALLER VI FOR "WIRING"
     *
     * @Autowired (Injecting!)
     *  - By defining Autowired we are now "INJECTING" the interface into this service
     */
    @Autowired
    public PersonService(@Qualifier("postgres") PersonDao personDao) {
        this.personDao = personDao;
    }

    public int addPerson(Person person) {
        return personDao.insertPerson(person);
    }

    public List<Person> getAllPeople() {
        return personDao.selectAllPeople();
    }

    public Optional<Person> getPersonById(UUID id) {
        return personDao.selectPersonById(id);
    }

    public int deletePerson(UUID id) {
        return personDao.deletePersonById(id);
    }

    public int updatePerson(UUID id, Person updatedPerson) {
        return personDao.updatePersonData(id, updatedPerson);
    }
}
