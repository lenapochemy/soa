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

import java.time.LocalDate;

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
    @OneToOne
    private Coordinates coordinates;
    @NotNull
    private LocalDate creationDate;
    private Boolean realHero;
    private Boolean hasToothpick;
    @Max(value = 981)
    private Float impactSpeed;
    private Double minutesOfWaiting;
    @Enumerated
    private WeaponType weaponType;
    @NotNull
    @Enumerated
    private Mood mood;
    @NotNull
    @OneToOne
    private Car car;

}
