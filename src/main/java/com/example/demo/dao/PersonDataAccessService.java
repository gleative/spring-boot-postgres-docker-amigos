package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class PersonDataAccessService implements PersonDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired // Dependency injection
    public PersonDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertPerson(UUID id, Person person) {
        final String sql = "INSERT INTO person (id, name) VALUES (?, ?)";

        return jdbcTemplate.update(sql, id, person.getName());
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        final String sql = "SELECT * FROM person WHERE id = ?";

        // When we get for specific value, we must include a argument which is a Object and MUST be a array, and we must pass inn the id which comes from the client
        // Så hvis du har flere arguments legger du det bare innenfor {} som du gjorde med id
        Person person = jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultSet, i) ->
                new Person(
                        UUID.fromString(resultSet.getString("id")),
                        resultSet.getString("name")
                ));

        // We have ofNullable SINCE it CAN be null
        return Optional.ofNullable(person);
    }

    // Rowmapper: Tar verdiene vi får fra database, og mapper det over til java objecter
    // Rowmapper er også lambda
    @Override
    public List<Person> selectAllPeople() {
        final String sql = "SELECT id, name FROM person";

        // Here we query the database, and expect to get back a result. We map the result by using RowMapper (lambda)
        // By defining the column we are getting from and type we can easily create the object
        List<Person> persons = jdbcTemplate.query(sql, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            return new Person(id, name);
        });

        return persons;
    }

    @Override
    public int deletePersonById(UUID id) {
        final String sql = "DELETE FROM person WHERE id = ?";

        // Slike jeg tror ? fungerer, er at du må legge inn verdien i jdbcTemplate sin metode, slik at den vil assigne. HUSK REKKEFØLGE VIKTIG
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int updatePersonData(UUID id, Person person) {
        final String sql = "UPDATE person SET name = ? WHERE id = ?";

        return jdbcTemplate.update(sql, person.getName(), id);
    }
}
