package controller;

import java.lang.reflect.Type;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class TypeAdapterController  implements JsonSerializer, JsonDeserializer {
	private static final String OBJECTCLASSNAME = "OBJECTCLASSNAME";
    private static final String OBJECTDATA = "OBJECTDATA";
    
	@Override
	public Object deserialize(JsonElement jsonElement, Type type,
		JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
		JsonObject jsonObject = jsonElement.getAsJsonObject();		      
		JsonPrimitive jsonPrimitive = (JsonPrimitive) jsonObject.get(OBJECTCLASSNAME);
		String className = jsonPrimitive.getAsString();
		Class objectClass = getObjectClass(className);
		return jsonDeserializationContext.deserialize(jsonObject.get(OBJECTDATA), objectClass);
	}
	
	public JsonElement serialize(Object jsonElement, Type type, JsonSerializationContext jsonSerializationContext) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(OBJECTCLASSNAME, jsonElement.getClass().getName());
		jsonObject.add(OBJECTDATA, jsonSerializationContext.serialize(jsonElement));
		return jsonObject;
	}
	
	public Class getObjectClass(String className) {
        try {
            return Class.forName(className);
            } catch (ClassNotFoundException e) {
                //e.printStackTrace();
                throw new JsonParseException(e.getMessage());
            }
    } 
}


