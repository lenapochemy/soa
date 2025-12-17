package web;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@XmlRootElement
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HumanResponse {

    private int code;
    private String message;
    private LocalDateTime time;

    public HumanResponse(int code, String message) {
        this.time = LocalDateTime.now();
        this.code = code;
        this.message = message;
    }

}
