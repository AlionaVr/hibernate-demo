package org.hibernatedemo.service;

import lombok.RequiredArgsConstructor;
import org.hibernatedemo.entity.Person;
import org.hibernatedemo.repository.PersonsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonsService {
    private final PersonsRepository personRepository;

    public List<Person> getPersonsByCity(String city) {
        return personRepository.getPersonsByCityFiltered(city);
    }
}