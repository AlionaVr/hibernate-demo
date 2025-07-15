package org.hibernatedemo.service;

import lombok.RequiredArgsConstructor;
import org.hibernatedemo.entity.Person;
import org.hibernatedemo.entity.PersonCompositeKey;
import org.hibernatedemo.exception.PersonNotFoundException;
import org.hibernatedemo.repository.PersonsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonsService {
    private final PersonsRepository personRepository;

    public List<Person> getPersonsByCity(String city) {
        return personRepository.findByCityIgnoreCase(city);
    }

    public List<Person> getPersonsByAgeLessThan(int age) {
        return personRepository.findById_AgeLessThanOrderById_AgeAsc(age);
    }

    public Person getPersonsByNameAndSurname(String name, String surname) {
        return personRepository.findById_NameAndId_Surname(name, surname)
                .orElseThrow(() -> new RuntimeException("No person found with name: " + name + " and surname: " + surname));
    }

    @Transactional
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    @Transactional
    public void deleteById(PersonCompositeKey id) {
        personRepository.deleteById(id);
    }

    @Transactional
    public Person updatePerson(PersonCompositeKey id, Person person) {
        Person existingPerson = getPersonById(id);

        existingPerson.setCity(person.getCity());
        existingPerson.setPhoneNumber(person.getPhoneNumber());
        return personRepository.save(existingPerson);
    }

    public Person getPersonById(PersonCompositeKey id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person not found with id: " + id));
    }

    public Page<Person> getAllPersons(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page must be non-negative and size must be positive");
        }
        Pageable pageable = PageRequest.of(page, size);
        return personRepository.findAll(pageable);
    }
}