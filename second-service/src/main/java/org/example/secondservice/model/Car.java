package org.example.secondservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Car {

    private Integer id;
    private String name;
    private Boolean cool;

    public Car(Integer id, String name, Boolean cool) {
        this.id = id;
        this.name = name;
        this.cool = cool;
    }

}
