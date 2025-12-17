package ejb;

import ejb.dto.CreationDateCount;
import ejb.dto.HumanBeingDTO;
import ejb.dto.HumanBeingPatch;
import ejb.exception.BadRequestException;
import ejb.exception.ObjectNotFoundException;
import ejb.model.HumanBeing;

import jakarta.ejb.Remote;
import java.util.List;
import java.util.Set;

@Remote
public interface HumanService {

    HumanBeing getById(Integer id);
    HumanBeing create(HumanBeingDTO dto);
    boolean delete(Integer id);
    HumanBeing update(Integer id, HumanBeingPatch patch) throws BadRequestException, ObjectNotFoundException;
    List<HumanBeing> getWithGreaterMinutes(int minutes);
    Set<Double> getUniqueMinutes();
    List<CreationDateCount> groupingByCreationDate();
    List<HumanBeing> getAll(Integer pageNumber, Integer pageSize, String filter, String sortBy, String sortOrder)
            throws BadRequestException;

}
