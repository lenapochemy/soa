package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Embeddable
@XmlRootElement
@Entity
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private Long x;
    @NotNull
    @Min(value = -312)
    private Integer y;
//    @OneToMany(mappedBy = "coordinates", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<HumanBeing> humans;


    @Override
    public String toString(){
        return "Coordinates: id = " + id + ", x = " + x + ", y = " + y;
    }
}
