package org.example;

import jakarta.inject.Inject;
import jakarta.json.bind.JsonbException;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import org.example.dao.HumanDAO;
import org.example.dto.CreationDateCount;
import org.example.dto.HumanBeingDTO;
import org.example.model.Car;
import org.example.model.HumanBeing;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Path("/humans")
public class HumanResource {

    @Inject
    HumanDAO humanDAO;

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
        HumanBeing humanBeing = humanDAO.find(id);
        if (humanBeing == null) {
            HumanResponse response = new HumanResponse(404, "Обьект " + id + " не найден.");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        } else {
            return Response.ok(humanBeing).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createHuman(HumanBeingDTO humanDTO) {
        if(humanDTO.getCar() == null || humanDTO.getCoordinates() == null){
            HumanResponse response = new HumanResponse(400, "Неправильный формат данных");
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }

        try {
            HumanBeing savedHuman = humanDAO.create(humanDTO);
            if (savedHuman != null) {
                return Response.status(Response.Status.CREATED).entity(savedHuman).build();
            } else{
                HumanResponse response = new HumanResponse(404, "Ресурс не найден");
                return Response.status(Response.Status.NOT_FOUND).entity(response).build();
            }

        } catch (ConstraintViolationException e){
            HumanResponse response = new HumanResponse(400, "Неправильный формат данных");
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }
    }


    @DELETE
    @Path("/{id}")
    public Response deleteHuman(@PathParam("id") Integer id){
        if(humanDAO.delete(id)){
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            HumanResponse response = new HumanResponse(404, "Ресурс не найден");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateHuman(HumanBeingDTO humanDTO, @PathParam("id") Integer id){
        try {
            HumanBeing oldHuman = humanDAO.find(id);
            if (oldHuman == null) {
                HumanResponse response = new HumanResponse(404, "Обьект " + id + " не найден.");
                return Response.status(Response.Status.NOT_FOUND).entity(response).build();
            } else {
                HumanBeing humanBeing = humanDAO.update(oldHuman, humanDTO);
                return Response.ok(humanBeing).build();
            }
        } catch (ConstraintViolationException e){ //TODO
            HumanResponse response = new HumanResponse(400, "Неправильный формат данных");
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }
    }


    @GET
    @Path("/minutes-of-waiting/{minutes}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWithGreaterMinutesOfWaiting(@PathParam("minutes") int minutes){
        List<HumanBeing> humanBeingList = humanDAO.findWithGreaterMinutesOfWaiting(minutes);
        if(humanBeingList.isEmpty()){
            HumanResponse response = new HumanResponse(422, "У всех объектов время ожидания меньше");
            return Response.status(422).entity(response).build();
        } else {
            return Response.ok(humanBeingList).build();
        }
    }


    @GET
    @Path("/minutes-of-waiting/unique")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUniqueMinutesOfWaiting(){
        Set<Double> uniqueMinutes = humanDAO.findUniqueMinutesOfWaiting();
        return Response.ok(uniqueMinutes).build();
    }


    @GET
    @Path("/creation-date")
    @Produces(MediaType.APPLICATION_JSON)
    public Response groupingByCreationDate(){
        List<CreationDateCount> creationDate = humanDAO.groupingByCreationDate();
        return Response.ok(creationDate).build();
    }


}
