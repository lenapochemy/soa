package org.example.dto;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.*;

@XmlRootElement
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HumanBeingDTO {

    private String name;
    private Coordinates coordinates;
    private Boolean realHero;
    private Boolean hasToothpick;
    private Float impactSpeed;
    private Double minutesOfWaiting;
    private WeaponType weaponType;
    private Mood mood;
    private Car car;
    private Integer teamNumber;

}
