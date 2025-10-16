package org.example.secondservice;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TeamResponse {

    private int code;
    private String message;
    private LocalDateTime time;

    public TeamResponse(int code, String message) {
        this.time = LocalDateTime.now();
        this.code = code;
        this.message = message;
    }

}
