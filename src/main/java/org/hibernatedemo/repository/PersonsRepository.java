package org.hibernatedemo.repository;

import org.hibernatedemo.entity.Person;
import org.hibernatedemo.entity.PersonCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonsRepository extends JpaRepository<Person, PersonCompositeKey> {
    List<Person> findByCityIgnoreCase(String city);

    List<Person> findById_AgeLessThanOrderById_AgeAsc(int age);

    Optional<Person> findById_NameAndId_Surname(String name, String surname);
}
