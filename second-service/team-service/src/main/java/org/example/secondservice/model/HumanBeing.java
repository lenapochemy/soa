package org.example.secondservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class HumanBeing {
    private int id;
    private String name;
    private Coordinates coordinates;
    private LocalDate creationDate;
    private Boolean realHero;
    private Integer teamNumber;
    private Boolean hasToothpick;
    private Float impactSpeed;
    private Double minutesOfWaiting;
    private WeaponType weaponType;
    private Mood mood;
    private Car car;


//    public HumanBeing() {
//    }
//
//    public HumanBeing(int id, String name, Coordinates coordinates, LocalDate creationDate, Boolean realHero, Integer teamNumber,
//                      Boolean hasToothpick, Float impactSpeed, Double minutesOfWaiting, WeaponType weaponType, Mood mood, Car car) {
//        this.id = id;
//        this.name = name;
//        this.coordinates = coordinates;
//        this.creationDate = creationDate;
//        this.realHero = realHero;
//        this.teamNumber = teamNumber;
//        this.hasToothpick = hasToothpick;
//        this.impactSpeed = impactSpeed;
//        this.minutesOfWaiting = minutesOfWaiting;
//        this.weaponType = weaponType;
//        this.mood = mood;
//        this.car = car;
//    }

}
