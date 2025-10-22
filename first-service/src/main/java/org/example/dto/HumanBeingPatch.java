package org.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.example.model.Mood;
import org.example.model.WeaponType;
import org.openapitools.jackson.nullable.JsonNullable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class HumanBeingPatch {

    private JsonNullable<String> name = JsonNullable.undefined();
    private JsonNullable<CoordinatesPatch> coordinates = JsonNullable.undefined();
    private JsonNullable<Boolean> realHero = JsonNullable.undefined();
    private JsonNullable<Boolean> hasToothpick = JsonNullable.undefined();
    private JsonNullable<Float> impactSpeed = JsonNullable.undefined();
    private JsonNullable<Double> minutesOfWaiting = JsonNullable.undefined();
    private JsonNullable<WeaponType> weaponType = JsonNullable.undefined();
    private JsonNullable<Mood> mood = JsonNullable.undefined();
    private JsonNullable<CarPatch> car = JsonNullable.undefined();
    private JsonNullable<Integer> teamNumber = JsonNullable.undefined();
}
