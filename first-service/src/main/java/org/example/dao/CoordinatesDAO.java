package org.example.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.model.Coordinates;


@ApplicationScoped
public class CoordinatesDAO {

    @PersistenceContext(unitName = "HumanPU")
    private EntityManager em;

    @Transactional(value = Transactional.TxType.REQUIRED)
    public Coordinates create(Coordinates coordinates) {
        em.persist(coordinates);
        return coordinates;
    }

    public Coordinates find(int id) {
        return em.find(Coordinates.class, id);
    }

    @Transactional
    public Coordinates update(Coordinates coordinates) {
        return em.merge(coordinates);
    }

}
