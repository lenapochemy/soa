package web;

import jakarta.ejb.EJB;
import ejb.HumanService;
import ejb.dto.HumanBeingDTO;
import ejb.dto.HumanBeingPatch;
import ejb.exception.ObjectNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ejb.exception.BadRequestException;
import ejb.dto.CreationDateCount;
import ejb.model.HumanBeing;

import java.util.List;
import java.util.Set;

@Path("/humans")
public class HumanResource {

    @EJB
    private HumanService humanService;

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello() {
        return "Hello, lena";
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHuman(@PathParam("id") Integer id) {
        HumanBeing humanBeing = humanService.getById(id);
        if (humanBeing == null) {
            return toResponse(404, "Обьект " + id + " не найден.");
        } else {
            return Response.ok(humanBeing).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createHuman(HumanBeingDTO humanDTO) {
        try {
            HumanBeing savedHuman = humanService.create(humanDTO);
            if (savedHuman != null) {
                return Response.status(Response.Status.CREATED).entity(savedHuman).build();
            } else {
                return toResponse(404, "Ресурс не найден");
            }
        } catch (ConstraintViolationException e) {
            return toResponse(400, "Неправильный формат данных");
        }
    }


    @DELETE
    @Path("/{id}")
    public Response deleteHuman(@PathParam("id") Integer id) {
        if (humanService.delete(id)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return toResponse(404, "Ресурс не найден");
        }
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateHuman(HumanBeingPatch humanBeingPatch, @PathParam("id") Integer id) {
        try {
            HumanBeing humanBeing = humanService.update(id, humanBeingPatch);
            return Response.ok(humanBeing).build();
        } catch (BadRequestException e) {
            return toResponse(400, "Неправильный формат данных: " + e.getMessage());
        } catch (ObjectNotFoundException e){
            return toResponse(404, "Обьект " + id + " не найден.");
        }
    }


    @GET
    @Path("/minutes-of-waiting/{minutes}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWithGreaterMinutesOfWaiting(@PathParam("minutes") int minutes) {
        List<HumanBeing> humanBeingList = humanService.getWithGreaterMinutes(minutes);
        if (humanBeingList.isEmpty()) {
            return toResponse(422, "У всех объектов время ожидания меньше");
        } else {
            return Response.ok(humanBeingList).build();
        }
    }


    @GET
    @Path("/minutes-of-waiting/unique")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUniqueMinutesOfWaiting() {
        Set<Double> uniqueMinutes = humanService.getUniqueMinutes();
        return Response.ok(uniqueMinutes).build();
    }


    @GET
    @Path("/creation-date")
    @Produces(MediaType.APPLICATION_JSON)
    public Response groupingByCreationDate() {
        List<CreationDateCount> creationDate = humanService.groupingByCreationDate();
        return Response.ok(creationDate).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("page") Integer pageNumber,
                           @QueryParam("page-size") Integer pageSize,
                           @QueryParam("filter") String filter,
                           @QueryParam("sort-by") String sortBy,
                           @QueryParam("sort-order") String sortOrder
    ) {
        try {
            List<HumanBeing> humans = humanService.getAll(pageNumber, pageSize, filter, sortBy, sortOrder);
            if (humans.isEmpty()) {
                return toResponse(422, "Нет обьектов, удовлетворящию условиям");
            }
            return Response.ok(humans).build();
        } catch (BadRequestException e) {
            return toResponse(400, "Неправильный формат данных: " + e.getMessage());
        }
    }

    private Response toResponse(int code, String message) {
        HumanResponse response = new HumanResponse(code, message);
        return Response.status(code).entity(response).build();
    }

}
