package org.example.secondservice.controllers;

import org.example.secondservice.TeamResponse;
import org.example.secondservice.exceptions.HumanNotFoundException;
import org.example.secondservice.exceptions.ServerErrorException;
import org.example.secondservice.exceptions.TeamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.example.secondservice.services.ClientService;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true", methods = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.OPTIONS})
@RequestMapping("/heroes/team")
public class TeamController {

    final ClientService clientService;

    @Autowired
    public TeamController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/{team-id}/car/add")
    public ResponseEntity<?> addCars(@PathVariable("team-id") Integer teamId) {
        try {
            if (clientService.addCarToHumans(teamId)) {
                return toResponse(200, "У героев команды " + teamId + ", у которых не было машины, теперь есть красная Lada Kalina.");
            } else {
                return toResponse(422, "У всех членов команды " + teamId + " уже есть автомобиль.");
            }
        } catch (ServerErrorException e) {
            return toResponse(502, "Первый сервис упал");
        } catch (TeamException e) {
            return toResponse(404, "В команде " + teamId + " нет героев.");
        }
    }


    @DeleteMapping("/{team-id}/remove/{hero-id}")
    public ResponseEntity<?> deleteHumanFromTeam(@PathVariable("team-id") Integer teamId,
                                                 @PathVariable("hero-id") Integer humanId) {
        try {
            clientService.deleteHumanFromTeam(humanId, teamId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (HumanNotFoundException e) {
            return toResponse(404, "Герой " + humanId + " не найден.");
        } catch (TeamException e) {
            return toResponse(409, "Герой " + humanId + " не состоит в команде " + teamId);
        } catch (ServerErrorException e) {
            return toResponse(502, "Первый сервис упал");
        }
    }

    @GetMapping("/hello")
    public String testSsl() {
        return "hello, lena";
    }


    private ResponseEntity<?> toResponse(int code, String message) {
        TeamResponse response = new TeamResponse(code, message);
        return ResponseEntity.status(HttpStatus.valueOf(code)).body(response);
    }


}
