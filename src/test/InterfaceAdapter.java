package test;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class InterfaceAdapter  implements JsonSerializer, JsonDeserializer {
	private static final String CLASSNAME = "CLASSNAME";
    private static final String DATA = "DATA";
	@Override
	public Object deserialize(JsonElement jsonElement, Type type,
	        JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			
				System.out.println("cuao");
		        JsonObject jsonObject = jsonElement.getAsJsonObject();
		        System.out.println("jsonObject--> " + jsonObject);
		        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
		       
		        System.out.println("prima di classname--> "+prim);
		        String className = prim.getAsString();
		        System.out.println("dopo classmame--> "+ className);
		        System.out.println(jsonObject.get(DATA));
		        Class klass = getObjectClass(className);
		            return jsonDeserializationContext.deserialize(jsonObject.get(DATA), klass);
	}
	
	public JsonElement serialize(Object jsonElement, Type type, JsonSerializationContext jsonSerializationContext) {
		System.out.println("lavora ");
		JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CLASSNAME, jsonElement.getClass().getName());
        jsonObject.add(DATA, jsonSerializationContext.serialize(jsonElement));
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

