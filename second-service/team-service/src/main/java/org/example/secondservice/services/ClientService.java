package org.example.secondservice.services;

import org.example.secondservice.exceptions.ClientErrorException;
import org.example.secondservice.exceptions.TeamException;
import org.example.secondservice.exceptions.HumanNotFoundException;
import org.example.secondservice.exceptions.ServerErrorException;
import org.example.secondservice.model.Car;
import org.example.secondservice.model.HumanBeing;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ClientService {
    private final RestTemplate restTemplate;


    private final String baseHumansUrl = "http://localhost:8080/human-service/api/humans";

    public ClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private List<HumanBeing> getHumansFromTeam(Integer teamNumber) throws ClientErrorException, ServerErrorException {

        String url = baseHumansUrl + "?filter=teamNumber=" + teamNumber;
        HumanBeing[] humans = restTemplate.getForObject(url, HumanBeing[].class);
        return Arrays.asList(humans);

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

        String url = baseHumansUrl + "/" + human.getId();

        restTemplate.patchForObject(url, human, HumanBeing.class);
    }


    private HumanBeing getHuman(Integer id) throws ClientErrorException, ServerErrorException {
        try {

            String url = baseHumansUrl + "/" + id;
            HumanBeing human = restTemplate.getForObject(url, HumanBeing.class);
            return human;

        } catch (ClientErrorException e) {
            return null;
        }
    }

    public void deleteHumanFromTeam(int humanId, int teamId) throws HumanNotFoundException, TeamException, ServerErrorException {
        HumanBeing humanBeing = getHuman(humanId);
        if (humanBeing == null) {
            throw new HumanNotFoundException();
        }
        if (humanBeing.getTeamNumber() == null || humanBeing.getTeamNumber() != teamId) {
            throw new TeamException();
        }
        humanBeing.setTeamNumber(null);

        String url = baseHumansUrl + "/" + humanId;
        restTemplate.patchForObject(url, humanBeing, HumanBeing.class);

    }
}

