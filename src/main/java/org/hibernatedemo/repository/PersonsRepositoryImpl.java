package org.hibernatedemo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernatedemo.entity.Person;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PersonsRepositoryImpl implements PersonsRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<Person> getPersonsByCityFiltered(String city) {
        return entityManager.createQuery(
                        "SELECT p FROM Person p WHERE LOWER(p.city) = LOWER(:city)", Person.class)
                .setParameter("city", city)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> getPersonByCompositeKey(String name, String surname, int age) {
        return entityManager.createQuery(
                        "SELECT p FROM Person p WHERE p.id.age > :age ORDER BY p.id.age DESC", Person.class)
                .setParameter("age", age)
                .getResultList();
    }
}
