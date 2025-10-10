package org.example.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.dto.CreationDateCount;
import org.example.dto.HumanBeingDTO;
import org.example.dto.Mapper;
import org.example.model.Car;
import org.example.model.Coordinates;
import org.example.model.HumanBeing;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class HumanDAO {

    @PersistenceContext(unitName = "HumanPU")
    private EntityManager em;

    @Inject
    CarDAO carDAO;
    @Inject
    CoordinatesDAO coordinatesDAO;

    private Mapper mapper = new Mapper();

    @Transactional(value = Transactional.TxType.REQUIRED)
    public HumanBeing create(HumanBeingDTO humanBeingDTO) {
        Car car = humanBeingDTO.getCar();
        carDAO.create(car);
        Coordinates coordinates = humanBeingDTO.getCoordinates();
        coordinatesDAO.create(coordinates);
        HumanBeing human = mapper.humanFromDTO(humanBeingDTO);
        em.persist(human);
        return human;
    }

    public HumanBeing find(int id) {
        return em.find(HumanBeing.class, id);
    }

    @Transactional
    public HumanBeing update(HumanBeing oldHuman, HumanBeingDTO dto) {
        if (dto.getName() != null) {
            oldHuman.setName(dto.getName());
        }
        if (dto.getRealHero() != null) {
            oldHuman.setRealHero(dto.getRealHero());
        }
        if (dto.getHasToothpick() != null) {
            oldHuman.setHasToothpick(dto.getHasToothpick());
        }
        if (dto.getImpactSpeed() != null) {
            oldHuman.setImpactSpeed(dto.getImpactSpeed());
        }
        if (dto.getMinutesOfWaiting() != null) {
            oldHuman.setMinutesOfWaiting(dto.getMinutesOfWaiting());
        }
        if (dto.getWeaponType() != null) {
            oldHuman.setWeaponType(dto.getWeaponType());
        }
        if (dto.getMood() != null) {
            oldHuman.setMood(dto.getMood());
        }

        Car car = dto.getCar();
        if (car != null) {
            Car oldCar = oldHuman.getCar();
            if (car.getName() != null) {
                oldCar.setName(car.getName());
            }
            if (car.getCool() != null) {
                oldCar.setCool(car.getCool());
            }
            carDAO.update(oldCar);
        }
        Coordinates coordinates = dto.getCoordinates();
        if (coordinates != null) {
            Coordinates oldCoordinates = oldHuman.getCoordinates();
            if (coordinates.getX() != null) {
                oldCoordinates.setX(coordinates.getX());
            }
            if (coordinates.getY() != null) {
                oldCoordinates.setY(coordinates.getY());
            }
            coordinatesDAO.update(oldCoordinates);
        }

//        HumanBeing human = mapper.humanFromDTO(dto);
//        human.setId(id);
        return em.merge(oldHuman);

    }

    @Transactional
    public boolean delete(int id) {
        HumanBeing humanBeing = em.find(HumanBeing.class, id);
        if (humanBeing != null) {
            em.remove(humanBeing);
            return true;
        }
        return false;
    }

    public List<HumanBeing> findAll() {
        return em.createQuery("SELECT h FROM HumanBeing h", HumanBeing.class).getResultList();
    }

    public List<HumanBeing> findWithGreaterMinutesOfWaiting(int minutes) {
        return em.createQuery("SELECT h FROM HumanBeing h WHERE h.minutesOfWaiting > " + minutes, HumanBeing.class).getResultList();
    }


    public Set<Double> findUniqueMinutesOfWaiting() {
        List<HumanBeing> humans = findAll();
        return humans.stream()
                .map(HumanBeing::getMinutesOfWaiting)
                .collect(Collectors.toSet());

    }

    public List<CreationDateCount> groupingByCreationDate() {
        List<HumanBeing> humans = findAll();
        Map<LocalDate, Long> dates = humans.stream()
                .map(human -> human.getCreationDate().toLocalDate())
                .collect(Collectors.groupingBy(creationDate -> creationDate, Collectors.counting()));
        return dates.entrySet().stream()
                .map(e -> new CreationDateCount(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }
}

