package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Embeddable
@XmlRootElement
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String name;
    private Boolean cool;

//    @OneToOne
//    private HumanBeing humanBeing;


    @Override
    public String toString(){
        return "Car: id = " + id + ", name = " + name + ", cool = " + cool;
    }
}
