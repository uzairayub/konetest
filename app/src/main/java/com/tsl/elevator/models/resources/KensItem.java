package com.tsl.elevator.models.resources;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class KensItem{

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("desc")
	private String desc;

	@SerializedName("products")
	private List<String> products;

	public String getName(){
		return name;
	}

	public String getId(){
		return id;
	}

	public String getDesc(){
		return desc;
	}

	public List<String> getProducts(){
		return products;
	}
}