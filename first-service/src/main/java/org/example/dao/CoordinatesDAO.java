package org.example.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.model.Coordinates;

import java.util.List;

@ApplicationScoped
public class CoordinatesDAO {

    @PersistenceContext(unitName = "HumanPU")
    private EntityManager em;

    @Transactional(value = Transactional.TxType.REQUIRED)
    public Coordinates create(Coordinates coordinates) {
        em.persist(coordinates);
        return coordinates;
    }

    @Transactional
    public Coordinates update(Coordinates coordinates) {
        return em.merge(coordinates);
    }

}
