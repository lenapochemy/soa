package org.example.secondservice.services;

import org.example.secondservice.exceptions.ClientErrorException;
import org.example.secondservice.exceptions.TeamException;
import org.example.secondservice.exceptions.HumanNotFoundException;
import org.example.secondservice.exceptions.ServerErrorException;
import org.example.secondservice.model.Car;
import org.example.secondservice.model.HumanBeing;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class ClientService {
    private final RestClient restClient;


    private final String baseHumansUrl = "http://localhost:8080/human-service/api/humans";

    public ClientService(RestClient restClient) {
        this.restClient = restClient;
    }

    private List<HumanBeing> getHumansFromTeam(Integer teamNumber) throws ClientErrorException, ServerErrorException {
        try {
            return restClient.get()
                    .uri((baseHumansUrl + "?filter=teamNumber=" + teamNumber))
                    .retrieve()
                    .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                        System.out.println("Error on getting humans " + response.getStatusCode() + response.getBody());
                        throw new ServerErrorException();
                    }))
                    .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                        System.out.println("Error on getting humans " + response.getStatusCode() + response.getBody());
                        throw new ClientErrorException();
                    }))
                    .body(new ParameterizedTypeReference<List<HumanBeing>>() {
                    });
        } catch (ClientErrorException e) {
            return null;
        }
    }

    public boolean addCarToHumans(Integer teamNumber) throws ServerErrorException, TeamException {
        List<HumanBeing> humans = getHumansFromTeam(teamNumber);
        int needsCar = 0;
        if (humans != null) {
            for (HumanBeing h : humans) {
                System.out.println("\u001B[35m" + h + "\u001B[0m");
                Car car = h.getCar();
                if (car == null) {
                    needsCar++;
                    try {
                        addRedLada(h);
                    } catch (ClientErrorException ignored) {
                    }
                }
            }
        } else throw new TeamException();
        return needsCar != 0;
    }


    private void addRedLada(HumanBeing human) throws ClientErrorException, ServerErrorException {
        String carName = "Red Lada Kalina";
        Car car = new Car(0, carName, true);
        human.setCar(car);
        System.out.println("\u001B[35m" + human + "\u001B[0m");

        ResponseEntity<HumanBeing> res = restClient.patch()
                .uri(baseHumansUrl + "/" + human.getId())
                .body(human)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    System.out.println("Error on adding car " + response.getStatusCode() + response.getBody());
                    throw new ClientErrorException();
                }))
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    System.out.println("Error on adding car " + response.getStatusCode() + response.getBody());
                    throw new ServerErrorException();
                }))
                .toEntity(HumanBeing.class);
    }


    private HumanBeing getHuman(Integer id) throws ClientErrorException, ServerErrorException {
        try {
            return restClient.get()
                    .uri((baseHumansUrl + "/" + id))
                    .retrieve()
                    .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                        System.out.println("Error on getting humans " + response.getStatusCode() + response.getBody());
                        throw new ServerErrorException();
                    }))
                    .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                        System.out.println("Error on getting humans " + response.getStatusCode() + response.getBody());
                        throw new ClientErrorException();
                    }))
                    .body(HumanBeing.class);
        } catch (ClientErrorException e) {
            return null;
        }
    }

    public void deleteHumanFromTeam(int humanId, int teamId) throws HumanNotFoundException, TeamException, ServerErrorException {
        HumanBeing humanBeing = getHuman(humanId);
        if (humanBeing == null) {
            throw new HumanNotFoundException();
        }
        if (humanBeing.getTeamNumber() != teamId) {
            throw new TeamException();
        }
        humanBeing.setTeamNumber(null);
        ResponseEntity<HumanBeing> res = restClient.patch()
                .uri(baseHumansUrl + "/" + humanId)
                .body(humanBeing)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    System.out.println("Error on adding car " + response.getStatusCode() + response.getBody());
                    throw new ClientErrorException();
                }))
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    System.out.println("Error on adding car " + response.getStatusCode() + response.getBody());
                    throw new ServerErrorException();
                }))
                .toEntity(HumanBeing.class);
    }

}