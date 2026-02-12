package ejb.dto;

import ejb.model.Mood;
import ejb.model.WeaponType;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.openapitools.jackson.nullable.JsonNullable;

public class JsonNullableAdapter<T> extends XmlAdapter<T, JsonNullable<T>> {

    @Override
    public JsonNullable<T> unmarshal(T v) throws Exception {
        return v == null ? JsonNullable.undefined() : JsonNullable.of(v);
    }

    @Override
    public T marshal(JsonNullable<T> v) throws Exception {
        return v == null || !v.isPresent() ? null : v.get();
    }
}

class JsonNullableStringAdapter extends JsonNullableAdapter<String> {
}

class JsonNullableBooleanAdapter extends JsonNullableAdapter<Boolean> {
}

class JsonNullableIntegerAdapter extends JsonNullableAdapter<Integer> {
}

class JsonNullableLongAdapter extends JsonNullableAdapter<Long> {
}

class JsonNullableDoubleAdapter extends JsonNullableAdapter<Double> {
}

class JsonNullableFloatAdapter extends JsonNullableAdapter<Float> {
}

class JsonNullableCoordinatesPatchAdapter extends JsonNullableAdapter<CoordinatesPatch> {
}

class JsonNullableCarPatchAdapter extends JsonNullableAdapter<CarPatch> {
}

class JsonNullableMoodAdapter extends JsonNullableAdapter<Mood> {
}

class JsonNullableWeaponTypeAdapter extends JsonNullableAdapter<WeaponType> {
}