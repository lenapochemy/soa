package ejb;

import ejb.dto.*;
import ejb.model.*;
import ejb.exception.BadRequestException;
import ejb.exception.ObjectNotFoundException;
import jakarta.ejb.EJB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;

import jakarta.ejb.Stateless;
import org.jboss.ejb3.annotation.Pool;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
@Pool("my-pool")
public class HumanServiceBean implements HumanService {

    @PersistenceContext(unitName = "HumanPU")
    private EntityManager em;

    @EJB
    CarDAO carDAO;
    @EJB
    CoordinatesDAO coordinatesDAO;

    private final Mapper mapper = new Mapper();

    @Override
    @Transactional(value = Transactional.TxType.REQUIRED)
    public HumanBeing create(HumanBeingDTO humanBeingDTO) {
        Car car = humanBeingDTO.getCar();
        if (car != null) {
            car.setId(0);
            carDAO.create(car);
        }
        Coordinates coordinates = humanBeingDTO.getCoordinates();
        if (coordinates != null) {
            Coordinates oldCoord = coordinatesDAO.find(coordinates.getId());
            if (oldCoord == null) {
                coordinatesDAO.create(coordinates);
            } else {
                coordinates.setId(0);
                if (!oldCoord.getX().equals(coordinates.getX()) || !oldCoord.getY().equals(coordinates.getY())) {
                    coordinatesDAO.create(coordinates);
                }
            }
        }
        HumanBeing human = mapper.humanFromDTO(humanBeingDTO);
        em.persist(human);
        return human;
    }

    @Override
    public HumanBeing getById(Integer id) {
        return em.find(HumanBeing.class, id);
    }

    @Override
    @Transactional
    public HumanBeing update(Integer id, HumanBeingPatch dto) throws BadRequestException, ObjectNotFoundException {
        HumanBeing oldHuman = getById(id);
        if (oldHuman == null) {
            throw new ObjectNotFoundException("Объект не найден");
        }
        if (dto.getName().isPresent()) {
            String name = dto.getName().orElse(null);
            if (name == null || name.isEmpty()) {
                throw new BadRequestException("Неправильное значение name");
            }
            oldHuman.setName(name);
        }
        if (dto.getRealHero().isPresent()) {
            oldHuman.setRealHero(dto.getRealHero().orElse(null));
        }
        if (dto.getHasToothpick().isPresent()) {
            oldHuman.setHasToothpick(dto.getHasToothpick().orElse(null));
        }
        if (dto.getImpactSpeed().isPresent()) {
            Float impactSpeed = dto.getImpactSpeed().orElse(null);
            if (impactSpeed != null) {
                if (impactSpeed > 981) throw new BadRequestException("Неправильное значение impactSpeed");
            }
            oldHuman.setImpactSpeed(impactSpeed);
        }
        if (dto.getMinutesOfWaiting().isPresent()) {
            oldHuman.setMinutesOfWaiting(dto.getMinutesOfWaiting().orElse(null));
        }
        if (dto.getWeaponType().isPresent()) {
            oldHuman.setWeaponType(dto.getWeaponType().orElse(null));
        }
        if (dto.getMood().isPresent()) {
            Mood mood = dto.getMood().orElse(null);
            if (mood == null) throw new BadRequestException("Неправильное значение mood");
        }
        if (dto.getTeamNumber().isPresent()) {
            oldHuman.setTeamNumber(dto.getTeamNumber().orElse(null));
        }

        if (dto.getCar().isPresent()) {
            CarPatch carPatch = dto.getCar().orElse(null);
            if (carPatch != null) {
                Car oldCar = oldHuman.getCar();
                if (oldCar != null) {
                    carFromPatch(carPatch, oldCar);
                    carDAO.update(oldCar);
                } else {
                    Car car = new Car();
                    carFromPatch(carPatch, car);
                    Car newCar = carDAO.create(car);
                    oldHuman.setCar(newCar);
                }
            }
        }
        if (dto.getCoordinates().isPresent()) {
            CoordinatesPatch coordinatesPatch = dto.getCoordinates().orElse(null);
            if (coordinatesPatch != null) {
                Coordinates oldCoordinates = oldHuman.getCoordinates();
                coordinatesFromPatch(coordinatesPatch, oldCoordinates);
                coordinatesDAO.update(oldCoordinates);
            } else throw new BadRequestException("Неправильное значение coordinates");
        }
        try {
            return em.merge(oldHuman);
        } catch (ConstraintViolationException e) {
            throw new ConstraintViolationException(e.getConstraintViolations());
        }
    }

    private void carFromPatch(CarPatch carPatch, Car car) throws BadRequestException {
        if (carPatch.getName().isPresent()) {
            String carPatchName = carPatch.getName().orElse(null);
            if (carPatchName != null) {
                car.setName(carPatchName);
            } else throw new BadRequestException("Неправильное значение car.name");
        }
        if (carPatch.getCool().isPresent()) {
            car.setCool(carPatch.getCool().orElse(null));
        }
    }

    private void coordinatesFromPatch(CoordinatesPatch coordinatesPatch, Coordinates coordinates) throws BadRequestException {
        if (coordinatesPatch.getX().isPresent()) {
            Long xPatch = coordinatesPatch.getX().orElse(null);
            if (xPatch != null) {
                coordinates.setX(xPatch);
            } else throw new BadRequestException("Неправильное значение coordinates.x");
        }
        if (coordinatesPatch.getY().isPresent()) {
            Integer yPatch = coordinatesPatch.getY().orElse(null);
            if (yPatch != null && yPatch > -313) {
                coordinates.setY(yPatch);
            } else throw new BadRequestException("Неправильное значение coordinates.y");
        }
    }

    @Override
    @Transactional
    public boolean delete(Integer id) {
        HumanBeing humanBeing = em.find(HumanBeing.class, id);
        if (humanBeing != null) {
            em.remove(humanBeing);
            return true;
        }
        return false;
    }

    private List<HumanBeing> findAll() {
        return em.createQuery("SELECT h FROM HumanBeing h", HumanBeing.class).getResultList();
    }

    @Override
    public List<HumanBeing> getWithGreaterMinutes(int minutes) {
        return em.createQuery("SELECT h FROM HumanBeing h WHERE h.minutesOfWaiting > " + minutes, HumanBeing.class).getResultList();
    }


    @Override
    public Set<Double> getUniqueMinutes() {
        List<HumanBeing> humans = findAll();
        return humans.stream()
                .map(HumanBeing::getMinutesOfWaiting)
                .collect(Collectors.toSet());

    }

    @Override
    public List<CreationDateCount> groupingByCreationDate() {
        List<HumanBeing> humans = findAll();
        Map<LocalDate, Long> dates = humans.stream()
                .map(HumanBeing::getCreationDate)
                .collect(Collectors.groupingBy(creationDate -> creationDate, Collectors.counting()));
        return dates.entrySet().stream()
                .map(e -> new CreationDateCount(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<HumanBeing> getAll(Integer pageNumber, Integer pageSize,
                                                    String filter, String sortBy, String sortOrder) throws BadRequestException {

        StringBuilder query = new StringBuilder("SELECT h FROM HumanBeing h");

        if (filter != null && !filter.isEmpty()) {
            boolean first = true;
            String[] conditions = filter.split(",");
            for (String condition : conditions) {
                String[] cond; // {"name", "lena"}
                if (condition.contains("=")) {
                    cond = condition.split("=");
//                    System.out.println(Arrays.toString(cond));
                    if (cond.length != 2) {
                        throw new BadRequestException("Фильтр должен быть в формате \"поле оператор значение\"");
                    }
                    if (isField(cond[0])) {
                        String queryPart = appendForFilter(cond, '=');
                        if (first) {
                            query.append(" WHERE").append(queryPart);
                            first = false;
                        } else {
                            query.append(" AND").append(queryPart);
                        }
//                        System.out.println("\u001B[35m" + query + "\u001B[0m");
                    } else throw new BadRequestException("Поле " + cond[0] + " не существует");
                } else if (condition.contains(">")) {
                    cond = condition.split(">");
                    if (cond.length != 2) {
                        throw new BadRequestException("Фильтр должен быть в формате \"поле оператор значение\"");
                    }
                    if (isField(cond[0])) {
                        String queryPart = appendForFilter(cond, '>');
                        if (first) {
                            query.append(" WHERE").append(queryPart);
                            first = false;
                        } else {
                            query.append(" AND").append(queryPart);
                        }
//                        System.out.println("\u001B[35m" + query + "\u001B[0m");
                    } else throw new BadRequestException("Поле " + cond[0] + " не существует");
                } else if (condition.contains("<")) {
                    cond = condition.split("<");
                    if (cond.length != 2) {
                        throw new BadRequestException("Фильтр должен быть в формате \"поле оператор значение\"");
                    }
                    if (isField(cond[0])) {
                        String queryPart = appendForFilter(cond, '<');
                        if (first) {
                            query.append(" WHERE").append(queryPart);
                            first = false;
                        } else {
                            query.append(" AND").append(queryPart);
                        }
//                        System.out.println("\u001B[35m" + query + "\u001B[0m");
                    } else throw new BadRequestException("Поле " + cond[0] + " не существует");
                } else throw new BadRequestException("Фильтр должен быть в формате \"поле оператор значение\"");
            }
        }

        if (sortBy != null && !sortBy.isEmpty()) {
            if (!isField(sortBy)) throw new BadRequestException("Поле " + sortBy + " для сортировки не существует ");
        } else sortBy = "id";
        query.append(" ORDER BY h.").append(sortBy);
        if (sortOrder != null && !sortOrder.isEmpty()) {
            if (!sortOrder.equals("asc") && !sortOrder.equals("desc"))
                throw new BadRequestException("Тип сортировки должен быть asc или desc");
        } else sortOrder = "asc";
        query.append(" ").append(sortOrder.toUpperCase());

        List<HumanBeing> humans;
        if (pageNumber == null && pageSize == null) {
            humans = em.createQuery(query.toString(), HumanBeing.class)
                    .getResultList();
        } else if (pageNumber != null && pageSize != null) {
            if (pageSize < 1) throw new BadRequestException("Размер страницы должен быть положительным");
            if (pageNumber < 1) throw new BadRequestException("Номер страницы должен быть положительным");
            humans = em.createQuery(query.toString(), HumanBeing.class)
                    .setFirstResult(pageSize * (pageNumber - 1))
                    .setMaxResults(pageSize)
                    .getResultList();
        } else if (pageNumber == null) {
            if (pageSize < 1) throw new BadRequestException("Размер страницы должен быть положительным");
            humans = em.createQuery(query.toString(), HumanBeing.class)
                    .setMaxResults(pageSize)
                    .getResultList();
        } else {
            pageSize = 10;
            humans = em.createQuery(query.toString(), HumanBeing.class)
                    .setFirstResult(pageSize * (pageNumber - 1))
                    .setMaxResults(pageSize)
                    .getResultList();
        }
        return humans;
    }

    private String appendForFilter(String[] filter, char operator) throws BadRequestException {
        if (filter[0].equals("creationDate")) {
            return " h." + filter[0] + " " + operator + " cast(" + parseValue(filter[1], filter[0]) + " as date)";
        }
        return " h." + filter[0] + " " + operator + " " + parseValue(filter[1], filter[0]);
    }

    private final List<String> fields = Arrays.asList("id", "name", "coordinates.id", "coordinates.x", "coordinates.y",
            "creationDate", "realHero", "teamNumber", "hasToothpick", "impactSpeed", "minutesOfWaiting", "weaponType", "mood",
            "car.id", "car.name", "car.cool");

    private boolean isField(String field) {
        field = field.trim();
        return fields.contains(field);
    }

    private Object parseValue(String value, String field) throws BadRequestException {
        if (value == null || value.isEmpty()) {
            throw new BadRequestException("Значение поля не может быть пустым");
        }
        try {
            return switch (field) {
                case "mood" -> Mood.valueOf(value);
                case "weaponType" -> WeaponType.valueOf(value);
                case "realHero", "hasToothpick", "car.cool" -> value;
                case "id", "coordinates.id", "coordinates.y", "car.id", "teamNumber" -> Integer.parseInt(value);
                case "creationDate" -> "'" + LocalDate.parse(value) + "'";
                case "name", "car.name" -> "'" + value + "'";
                case "coordinates.x" -> Long.parseLong(value);
                case "impactSpeed" -> Float.valueOf(value);
                case "minutesOfWaiting" -> Double.valueOf(value);
                default -> throw new BadRequestException("Некорректный тип обьекта в фильтре");
            };
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Некорректный тип значения для поля" + field + "в фильтре");
        }
    }
}

