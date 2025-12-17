package ejb;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import ejb.model.Car;


@Stateless
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
