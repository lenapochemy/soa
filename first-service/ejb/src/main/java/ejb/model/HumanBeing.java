package ejb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class HumanBeing implements Serializable {
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
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlSchemaType(name = "date")
    private LocalDate creationDate;
    private Boolean realHero;
    @Min(value = 1)
    private Integer teamNumber;
    private Boolean hasToothpick;
    @Max(value = 981)
    private Float impactSpeed;
    private Double minutesOfWaiting;
    @Enumerated
    private WeaponType weaponType;
    @NotNull
    @Enumerated
    private Mood mood;
    @OneToOne(optional = true)
    private Car car;

}
