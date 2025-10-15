package org.example.dto;

import org.example.model.*;

import java.time.LocalDate;

public class Mapper {

    public Mapper() {
    }

    public HumanBeing humanFromDTO(HumanBeingDTO dto) {
        HumanBeing humanBeing = new HumanBeing();
        humanBeing.setName(dto.getName());
        humanBeing.setCoordinates(dto.getCoordinates());
        humanBeing.setCreationDate(LocalDate.now());
        humanBeing.setRealHero(dto.getRealHero());
        humanBeing.setHasToothpick(dto.getHasToothpick());
        humanBeing.setImpactSpeed(dto.getImpactSpeed());
        humanBeing.setMinutesOfWaiting(dto.getMinutesOfWaiting());
        humanBeing.setWeaponType(dto.getWeaponType());
        humanBeing.setMood(dto.getMood());
        humanBeing.setCar(dto.getCar());
        humanBeing.setTeamNumber(dto.getTeamNumber());

        return humanBeing;
    }
}
