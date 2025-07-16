package org.hibernatedemo.controller;

import lombok.RequiredArgsConstructor;
import org.hibernatedemo.entity.Person;
import org.hibernatedemo.entity.PersonCompositeKey;
import org.hibernatedemo.service.PersonsService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
@Validated
public class PersonsController {
    private final PersonsService personsService;

    @GetMapping("/by-city")
    public ResponseEntity<List<Person>> getPersonsByCity(
            @RequestParam @NotBlank(message = "City cannot be blank") String city) {
        List<Person> persons = personsService.getPersonsByCity(city);
        return ResponseEntity.ok(persons);
    }

    @GetMapping("/by-age")
    public ResponseEntity<List<Person>> getPersonsByAgeLessThan(
            @RequestParam @Min(value = 1, message = "Age must be greater than 0") int age) {
        List<Person> persons = personsService.getPersonsByAgeLessThan(age);
        return ResponseEntity.ok(persons);
    }

    @GetMapping("/by-name-surname")
    public ResponseEntity<Person> getPersonsByNameAndSurname(
            @RequestParam @NotBlank(message = "Name cannot be blank") String name,
            @RequestParam @NotBlank(message = "Surname cannot be blank") String surname) {
        Person person = personsService.getPersonsByNameAndSurname(name, surname);
        return ResponseEntity.ok(person);
    }

    @PostMapping("/add")
    public ResponseEntity<Person> createPerson(@RequestBody @Valid Person person) {
        return ResponseEntity.ok(personsService.savePerson(person));
    }

    @DeleteMapping("/delete/{name}/{surname}/{age}")
    public void deletePersonById(
            @PathVariable @NotBlank(message = "Name cannot be blank") String name,
            @PathVariable @NotBlank(message = "Surname cannot be blank") String surname,
            @PathVariable @Min(value = 1, message = "Age must be greater than 0") int age) {
        PersonCompositeKey id = new PersonCompositeKey(name, surname, age);
        personsService.deleteById(id);
    }

    @PutMapping("/update/{name}/{surname}/{age}")
    public ResponseEntity<Person> updatePersonById(
            @PathVariable @NotBlank(message = "Name cannot be blank") String name,
            @PathVariable @NotBlank(message = "Surname cannot be blank") String surname,
            @PathVariable @Min(value = 1, message = "Age must be greater than 0") int age,
            @RequestBody @Valid Person person) {
        PersonCompositeKey id = new PersonCompositeKey(name, surname, age);
        Person updatedPerson = personsService.updatePerson(id, person);
        return ResponseEntity.ok(updatedPerson);
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Person> persons = personsService.getAllPersons(page, size);
        return ResponseEntity.ok(persons.getContent());
    }

}