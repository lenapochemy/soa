package web;

import ejb.dto.CreationDateCount;
import ejb.dto.HumanBeingDTO;
import ejb.dto.HumanBeingPatch;
import ejb.exception.ObjectNotFoundException;
import ejb.exception.BadRequestException;
import jakarta.ejb.EJB;
import ejb.HumanService;
import jakarta.ejb.EJBException;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.validation.ConstraintViolationException;
import ejb.model.HumanBeing;
import web.requests.GetWithFilterRequest;
import web.requests.UpdateHumanRequest;
import web.response.HumanResponse;
import web.response.HumanServiceFault;

import java.util.List;
import java.util.Set;

@WebService(endpointInterface = "web.HumanWebService")
public class HumanWebServiceImpl implements HumanWebService {

    @EJB
    private HumanService humanService;

    @Override
    public String sayHello() {
        return "Hello, lena";
    }


    @Override
    public HumanResponse getHuman(@WebParam(name = "id") Integer id) throws HumanServiceFault {
        HumanBeing humanBeing = humanService.getById(id);
        if (humanBeing == null) {
            throw new HumanServiceFault("NOT FOUND", "Обьект " + id + " не найден.");
        } else {
            return new HumanResponse(humanBeing);
        }
    }

    @Override
    public HumanResponse createHuman(@WebParam(name = "human") HumanBeingDTO humanDTO) throws HumanServiceFault {
        try {
            HumanBeing savedHuman = humanService.create(humanDTO);
            if (savedHuman != null) {
                return new HumanResponse(savedHuman);
            } else {
                throw new HumanServiceFault("NOT FOUND", "Ресурс не найден.");
            }
        } catch (ConstraintViolationException | EJBException e) {
            throw new HumanServiceFault("BAD REQUEST", "Неправильный формат данных.");
        }
    }


    @Override
    public HumanResponse deleteHuman(@WebParam(name = "id") Integer id) throws HumanServiceFault {
        if (humanService.delete(id)) {
            return new HumanResponse("NO_CONTENT", "Обьект удален.");
        } else {
            throw new HumanServiceFault("NOT FOUND", "Обьект " + id + " не найден.");
        }
    }

    @Override
    public HumanResponse updateHuman(@WebParam(name = "patch") UpdateHumanRequest updateHumanRequest) throws HumanServiceFault {
        int id = updateHumanRequest.getId();
        HumanBeingPatch humanBeingPatch = updateHumanRequest.getHumanBeingPatch();
        try {
            HumanBeing humanBeing = humanService.update(id, humanBeingPatch);
            return new HumanResponse(humanBeing);
        } catch (BadRequestException e) {
            throw new HumanServiceFault("BAD REQUEST", "Неправильный формат данных." + e.getMessage());
        } catch (ObjectNotFoundException e) {
            throw new HumanServiceFault("NOT FOUND", "Обьект " + id + " не найден.");
        }
    }


    @Override
    public HumanResponse getWithGreaterMinutesOfWaiting(@WebParam(name = "minutes") int minutes) throws HumanServiceFault {
        List<HumanBeing> humanBeingList = humanService.getWithGreaterMinutes(minutes);
        if (humanBeingList.isEmpty()) {
            throw new HumanServiceFault("UNPROCESSABLE", "У всех объектов время ожидания меньше.");
        } else {
            return HumanResponse.humanListResponse(humanBeingList);
        }
    }


    @Override
    public HumanResponse getUniqueMinutesOfWaiting() throws HumanServiceFault {
        Set<Double> uniqueMinutes = humanService.getUniqueMinutes();
        return new HumanResponse(uniqueMinutes);
    }


    @Override
    public HumanResponse groupingByCreationDate() throws HumanServiceFault {
        List<CreationDateCount> creationDate = humanService.groupingByCreationDate();
        return new HumanResponse(creationDate);
    }


    public HumanResponse getAll(
            @WebParam(name = "filter") GetWithFilterRequest request
    ) throws HumanServiceFault {
        try {
            List<HumanBeing> humans = humanService.getAll(request.getPageNumber(), request.getPageSize(),
                    request.getFilter(), request.getSortBy(), request.getSortOrder());
            if (humans.isEmpty()) {
                throw new HumanServiceFault("UNPROCESSABLE", "Нет обьектов, удовлетворящию условиям");
            }
            return HumanResponse.humanListResponse(humans);
        } catch (BadRequestException e) {
            throw new HumanServiceFault("BAD REQUEST", "Неправильный формат данных." + e.getMessage());
        }
    }

}
