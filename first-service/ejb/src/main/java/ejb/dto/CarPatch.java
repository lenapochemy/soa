package ejb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class CarPatch implements Serializable {

    private JsonNullable<String> name = JsonNullable.undefined();
    private JsonNullable<Boolean> cool = JsonNullable.undefined();

}
