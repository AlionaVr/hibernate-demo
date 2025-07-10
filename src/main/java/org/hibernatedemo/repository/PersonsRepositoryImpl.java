package org.hibernatedemo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.hibernatedemo.entity.Person;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PersonsRepositoryImpl implements PersonsRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public List<Person> getPersonsByCityFiltered(String city) {
        TypedQuery<Person> query = entityManager.createQuery(
                "SELECT p FROM Person p", Person.class);
        List<Person> allPersons = query.getResultList();
        return allPersons.stream()
                .filter(person -> city.equalsIgnoreCase(person.getCity()))
                .toList();
    }

    @Transactional
    @Override
    public List<Person> getPersonByCompositeKey(String name, String surname, int age) {
        TypedQuery<Person> query = entityManager.createQuery(
                "SELECT p FROM Person p WHERE p.id.age > :age ORDER BY p.id.age DESC", Person.class);
        query.setParameter("age", age);
        return query.getResultList();
    }
}
