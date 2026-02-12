package ejb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class CarPatch implements Serializable {

    @XmlJavaTypeAdapter(value = ejb.dto.JsonNullableStringAdapter.class)
    private JsonNullable<String> name = JsonNullable.undefined();
    @XmlJavaTypeAdapter(value = ejb.dto.JsonNullableBooleanAdapter.class)
    private JsonNullable<Boolean> cool = JsonNullable.undefined();

}
