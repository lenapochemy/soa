package org.example.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.example.model.Car;


import java.util.List;

@ApplicationScoped
public class CarDAO {

    @PersistenceContext(unitName = "HumanPU")
    private EntityManager em;


    @Transactional(value = Transactional.TxType.REQUIRED)
    public Car create(Car car) {
        em.persist(car);
        return car;
    }

    @Transactional
    public Car update(Car car) throws ConstraintViolationException {
        return em.merge(car);
    }
}
