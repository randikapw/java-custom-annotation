package jsonserializer.sample;

import jsonserializer.annotations.Init;
import jsonserializer.annotations.JsonElement;
import jsonserializer.annotations.JsonSerializable;

@JsonSerializable
public class Person {
	@JsonElement
	private String name;
	
	@JsonElement(key="myAge")
	private String age;
	
	private String address;//non serializable attibure.
	
	@Init
	private void initNames() {
		this.name = this.name.substring(0,1).toUpperCase() + this.name.substring(1);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
