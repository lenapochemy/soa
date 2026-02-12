package web.response;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.ws.WebFault;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@WebFault(name = "HumanServiceFault")
@Setter
@Getter
@NoArgsConstructor
public class HumanServiceFault extends Exception {

    @XmlElement(required = true)
    private String status;
    @XmlElement(required = true)
    private String message;


    public HumanServiceFault(String status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }


}
