package org.example.dto;

import org.example.model.*;

import java.time.LocalDateTime;

public class Mapper {

    public Mapper(){}



    public HumanBeing humanFromDTO(HumanBeingDTO dto) {
        HumanBeing humanBeing = new HumanBeing();
        humanBeing.setName(dto.getName());
        humanBeing.setCoordinates(dto.getCoordinates());
        humanBeing.setCreationDate(LocalDateTime.now());
        humanBeing.setRealHero(dto.getRealHero());
        humanBeing.setHasToothpick(dto.getHasToothpick());
        humanBeing.setImpactSpeed(dto.getImpactSpeed());
        humanBeing.setMinutesOfWaiting(dto.getMinutesOfWaiting());
        humanBeing.setWeaponType(dto.getWeaponType());
        humanBeing.setMood(dto.getMood());
        humanBeing.setCar(dto.getCar());

        return humanBeing;
    }
}
