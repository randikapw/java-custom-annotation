package jsonserializer.sample;

import jsonserializer.ObjectToJsonConverter;
import jsonserializer.exceptions.JsonSerializationException;

//This code is based on the article https://www.baeldung.com/java-custom-annotation 
public class Run {
	public static void main(String[] args) throws JsonSerializationException {
		Person p = new Person();
		p.setName("Randika");
		p.setAge("33");
		p.setAddress("Dahanekgedara");	
		
		ObjectToJsonConverter converter = new ObjectToJsonConverter();
		String json = converter.convertToJson(p);
		System.out.println(json);
	}

}
