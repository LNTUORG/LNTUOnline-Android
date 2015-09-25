package org.lntu.online.util.gson;

import com.google.gson.*;
import org.joda.time.DateTime;

import java.lang.reflect.Type;
import java.util.Date;

public class DateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(new DateTime(src).toString());
    }

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new DateTime(json.getAsString()).toDate();
    }

}
