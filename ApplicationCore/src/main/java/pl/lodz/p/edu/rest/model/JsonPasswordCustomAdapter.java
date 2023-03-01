package pl.lodz.p.edu.rest.model;

import jakarta.json.JsonValue;
import jakarta.json.bind.adapter.JsonbAdapter;

public class JsonPasswordCustomAdapter implements JsonbAdapter<String, JsonValue> {

    @Override
    public JsonValue adaptToJson(String password) {
        return null;
    }

    @Override
    public String adaptFromJson(JsonValue json) {
        return json.toString();
    }
}
