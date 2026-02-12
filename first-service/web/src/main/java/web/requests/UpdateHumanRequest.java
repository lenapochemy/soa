package web.requests;

import ejb.dto.HumanBeingPatch;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@XmlRootElement
@Getter
@Setter
@XmlAccessorType(XmlAccessType.PROPERTY)
public class UpdateHumanRequest {

    private Integer id;

    private HumanBeingPatch humanBeingPatch;

}
