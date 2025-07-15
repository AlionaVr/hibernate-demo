package org.hibernatedemo.repository;

import org.hibernatedemo.entity.Person;
import org.hibernatedemo.entity.PersonCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonsRepository extends JpaRepository<Person, PersonCompositeKey> {

    @Query("SELECT p FROM Person p WHERE LOWER(p.city) = LOWER(:city)")
    List<Person> findByCityIgnoreCase(@Param("city") String city);

    @Query("SELECT p FROM Person p WHERE p.id.age < :age ORDER BY p.id.age ASC")
    List<Person> findById_AgeLessThanOrderById_AgeAsc(@Param("age") int age);

    @Query("SELECT p FROM Person p WHERE p.id.name = :name AND p.id.surname = :surname")
    Optional<Person> findById_NameAndId_Surname(@Param("name") String name, @Param("surname") String surname);
}
