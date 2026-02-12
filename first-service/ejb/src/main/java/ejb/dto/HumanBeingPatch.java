package ejb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Getter;
import lombok.Setter;
import ejb.model.Mood;
import ejb.model.WeaponType;
import org.openapitools.jackson.nullable.JsonNullable;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class HumanBeingPatch implements Serializable {

    @XmlJavaTypeAdapter(value = JsonNullableStringAdapter.class)
    private JsonNullable<String> name = JsonNullable.undefined();
    @XmlJavaTypeAdapter(value = JsonNullableCoordinatesPatchAdapter.class)
    private JsonNullable<CoordinatesPatch> coordinates = JsonNullable.undefined();
    @XmlJavaTypeAdapter(value = JsonNullableBooleanAdapter.class)
    private JsonNullable<Boolean> realHero = JsonNullable.undefined();
    @XmlJavaTypeAdapter(value = JsonNullableBooleanAdapter.class)
    private JsonNullable<Boolean> hasToothpick = JsonNullable.undefined();
    @XmlJavaTypeAdapter(value = JsonNullableFloatAdapter.class)
    private JsonNullable<Float> impactSpeed = JsonNullable.undefined();
    @XmlJavaTypeAdapter(value = JsonNullableDoubleAdapter.class)
    private JsonNullable<Double> minutesOfWaiting = JsonNullable.undefined();
    @XmlJavaTypeAdapter(value = JsonNullableWeaponTypeAdapter.class)
    private JsonNullable<WeaponType> weaponType = JsonNullable.undefined();
    @XmlJavaTypeAdapter(value = JsonNullableMoodAdapter.class)
    private JsonNullable<Mood> mood = JsonNullable.undefined();
    @XmlJavaTypeAdapter(value = JsonNullableCarPatchAdapter.class)
    private JsonNullable<CarPatch> car = JsonNullable.undefined();
    @XmlJavaTypeAdapter(value = JsonNullableIntegerAdapter.class)
    private JsonNullable<Integer> teamNumber = JsonNullable.undefined();
}
