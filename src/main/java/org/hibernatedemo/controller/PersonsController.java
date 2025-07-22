package org.hibernatedemo.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernatedemo.entity.Person;
import org.hibernatedemo.entity.PersonCompositeKey;
import org.hibernatedemo.exception.ApiResponse;
import org.hibernatedemo.service.PersonsService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
@Validated
public class PersonsController {
    private final PersonsService personsService;

    @GetMapping("/by-city")
    public ResponseEntity<ApiResponse<List<Person>>> getPersonsByCity(
            @RequestParam @NotBlank(message = "City cannot be blank") String city) {
        List<Person> persons = personsService.getPersonsByCity(city);
        return ResponseEntity.ok(new ApiResponse<>("success", persons));
    }

    @GetMapping("/by-age")
    public ResponseEntity<ApiResponse<List<Person>>> getPersonsByAgeLessThan(
            @RequestParam @Min(value = 1, message = "Age must be greater than 0") int age) {
        List<Person> persons = personsService.getPersonsByAgeLessThan(age);
        return ResponseEntity.ok(new ApiResponse<>("success", persons));
    }

    @GetMapping("/by-name-surname")
    public ResponseEntity<ApiResponse<Person>> getPersonsByNameAndSurname(
            @RequestParam @NotBlank(message = "Name cannot be blank") String name,
            @RequestParam @NotBlank(message = "Surname cannot be blank") String surname) {
        Person person = personsService.getPersonsByNameAndSurname(name, surname);
        return ResponseEntity.ok(new ApiResponse<>("success", person));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Person>> createPerson(@RequestBody @Valid Person person) {
        return ResponseEntity.ok(new ApiResponse<>("success", personsService.savePerson(person)));
    }

    @DeleteMapping("/delete/{name}/{surname}/{age}")
    public ResponseEntity<ApiResponse<String>> deletePersonById(
            @PathVariable @NotBlank(message = "Name cannot be blank") String name,
            @PathVariable @NotBlank(message = "Surname cannot be blank") String surname,
            @PathVariable @Min(value = 1, message = "Age must be greater than 0") int age) {
        PersonCompositeKey id = new PersonCompositeKey(name, surname, age);
        personsService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>("success", "Person deleted"));
    }

    @PutMapping("/update/{name}/{surname}/{age}")
    public ResponseEntity<ApiResponse<Person>> updatePersonById(
            @PathVariable @NotBlank(message = "Name cannot be blank") String name,
            @PathVariable @NotBlank(message = "Surname cannot be blank") String surname,
            @PathVariable @Min(value = 1, message = "Age must be greater than 0") int age,
            @RequestBody @Valid Person person) {
        PersonCompositeKey id = new PersonCompositeKey(name, surname, age);
        Person updatedPerson = personsService.updatePerson(id, person);
        return ResponseEntity.ok(new ApiResponse<>("success", updatedPerson));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Person>>> getAllPersons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Person> persons = personsService.getAllPersons(page, size);
        return ResponseEntity.ok(new ApiResponse<>("success", persons.getContent()));
    }
}