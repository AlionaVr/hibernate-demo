package org.hibernatedemo.repository;

import org.hibernatedemo.entity.Person;

import java.util.List;

public interface PersonsRepository {
    List<Person> getPersonsByCityFiltered(String city);
    List<Person> getPersonByCompositeKey(String name, String surname, int age);
}
