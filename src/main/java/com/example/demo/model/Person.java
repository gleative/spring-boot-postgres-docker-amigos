package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class Person {
    private final UUID id;

    // This string can not be empty! We use Blank instead of NotNull since "" is a value, but NotBlank dont accept ""
    @NotBlank
    private final String name;

    // @JsonProperty is so Spring knows what the key names point too, when they receive the JSON object from a client
    public Person(@JsonProperty("id")UUID _id, @JsonProperty("name") String _name) {
        this.id = _id;
        this.name = _name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
