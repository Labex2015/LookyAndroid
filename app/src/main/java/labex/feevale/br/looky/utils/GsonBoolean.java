package labex.feevale.br.looky.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by PabloGilvan on 28/08/2014.
 */
public class GsonBoolean implements JsonSerializer<Boolean>, JsonDeserializer<Boolean> {
    @Override
    public JsonElement serialize(Boolean aBoolean, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(aBoolean ? 1 : 0);
    }

    @Override
    public Boolean deserialize(JsonElement json, Type type,
                               JsonDeserializationContext jsonDeserializationContext)throws JsonParseException {
        try {
            String value = json.getAsJsonPrimitive().getAsString();
            return value.toLowerCase().equals("true");
        } catch (ClassCastException e) {
            throw new JsonParseException("Cannot parse json date '" + json.toString() + "'", e);
        }
    }
}
