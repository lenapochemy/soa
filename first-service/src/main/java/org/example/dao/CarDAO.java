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

    public Car find(int id) {
        return em.find(Car.class, id);
    }

    @Transactional
    public Car update(Car car) throws ConstraintViolationException {
        return em.merge(car);
    }

    public void delete(int id) {
        Car car = em.find(Car.class, id);
        if (car != null) {
            em.remove(car);
        }
    }

    public List<Car> findAll() {
        return em.createQuery("SELECT h FROM Car h", Car.class).getResultList();
    }
}
