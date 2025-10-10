package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@XmlRootElement
public class HumanBeing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
//    @Embedded
    @OneToOne
    private Coordinates coordinates;
    @NotNull
    private LocalDateTime creationDate;
    private Boolean realHero;
    private Boolean hasToothpick;
    @Max(value = 981)
    private Float impactSpeed;
    private Double minutesOfWaiting;
    private WeaponType weaponType;
    @NotNull
    private Mood mood;
    @NotNull
//    @Embedded
    @OneToOne
    private Car car;

    @Override
    public String toString(){
        return "Human: id = " + id + ", creationDate = " + creationDate +  "name = " + this.name + ", coordinates: id = " + coordinates.getId() + ", x = " + coordinates.getX() +
                ", y = " + this.coordinates.getY() + ", realHero = " + realHero + ", hasToothPick = " + hasToothpick +
                ", impactSpeed = " + impactSpeed + ", minutesOfWaiting = " + minutesOfWaiting + ", weaponType = " +
                weaponType + ", mood = " + mood + ", car: id = " + car.getId() + ", name = " + car.getName() + ", cool = " + car.getCool();
    }

}
