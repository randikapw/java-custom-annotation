package jsonserializer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import jsonserializer.annotations.Init;
import jsonserializer.annotations.JsonElement;
import jsonserializer.annotations.JsonSerializable;
import jsonserializer.exceptions.JsonSerializationException;

public class ObjectToJsonConverter {

    public String convertToJson(Object object) throws JsonSerializationException {
        try {
            checkJsonSerializable(object);
            initializeObject(object);
            return getJsonString(object);
        } catch (Exception e) {
            throw new JsonSerializationException(e.getMessage());
        }
    }
    
	private String getJsonString(Object object) throws Exception {  
	    Class<?> clazz = object.getClass();
	    Map<String, String> jsonElementsMap = new HashMap<>();
	    for (Field field : clazz.getDeclaredFields()) {
	        field.setAccessible(true);
	        if (field.isAnnotationPresent(JsonElement.class)) {
	            jsonElementsMap.put(getKey(field), (String) field.get(object));
	        }
	    }       
	      
	    String jsonString = jsonElementsMap.entrySet()
	        .stream()
	        .map(entry -> "\"" + entry.getKey() + "\":\""
	          + entry.getValue() + "\"")
	        .collect(Collectors.joining(","));
	    return "{" + jsonString + "}";
	}
	
	private String getKey(Field field) {
		String key = field.getAnnotation(JsonElement.class).key();
		if(key.isEmpty()) {			
			return field.getName();
		}
		else {
			return key;
		}
	}

	private void initializeObject(Object object) throws Exception {
	    Class<?> clazz = object.getClass();
	    for (Method method : clazz.getDeclaredMethods()) {
	        if (method.isAnnotationPresent(Init.class)) {
	            method.setAccessible(true);
	            method.invoke(object);
	        }
	    }
	 }
	
	private void checkJsonSerializable(Object object) throws JsonSerializationException {

	    if (Objects.isNull(object)) {
	        throw new JsonSerializationException("The object to serialize is null");
	    }
	         
	    Class<?> clazz = object.getClass();
	    if (!clazz.isAnnotationPresent(JsonSerializable.class)) {
	        throw new JsonSerializationException("The class "
	          + clazz.getSimpleName() 
	          + " is not annotated with JsonSerializable");
	    }
	}
}
