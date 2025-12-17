package ejb.dto;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ejb.model.Car;
import ejb.model.Coordinates;
import ejb.model.Mood;
import ejb.model.WeaponType;

import java.io.Serializable;

@XmlRootElement
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HumanBeingDTO implements Serializable {

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
