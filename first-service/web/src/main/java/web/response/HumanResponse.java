package web.response;

import ejb.dto.CreationDateCount;
import ejb.model.HumanBeing;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@XmlRootElement
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class HumanResponse {

    private String status;
    private String message;
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    private LocalDateTime time;
    private HumanBeing humanBeing;
    private List<CreationDateCount> creationDateCountList;
    private Set<Double> uniqueMinutes;
    private List<HumanBeing> humanBeingList;

    public HumanResponse(String status, String message) {
        this.time = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }

    public HumanResponse(HumanBeing humanBeing) {
        this.time = LocalDateTime.now();
        this.humanBeing = humanBeing;
    }

    public HumanResponse(List<CreationDateCount> creationDateCountList) {
        this.time = LocalDateTime.now();
        this.creationDateCountList = creationDateCountList;
    }

    public HumanResponse(Set<Double> uniqueMinutes) {
        this.time = LocalDateTime.now();
        this.uniqueMinutes = uniqueMinutes;
    }

    public static HumanResponse humanListResponse(List<HumanBeing> humanBeingList) {
        HumanResponse response = new HumanResponse();
        response.time = LocalDateTime.now();
        response.humanBeingList = humanBeingList;
        return response;
    }

}
