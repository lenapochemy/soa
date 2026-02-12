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
public class CoordinatesPatch implements Serializable {

    @XmlJavaTypeAdapter(value = ejb.dto.JsonNullableLongAdapter.class)
    private JsonNullable<Long> x = JsonNullable.undefined();
    @XmlJavaTypeAdapter(value = ejb.dto.JsonNullableIntegerAdapter.class)
    private JsonNullable<Integer> y = JsonNullable.undefined();
}
