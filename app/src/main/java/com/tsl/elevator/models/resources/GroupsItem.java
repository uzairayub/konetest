package com.tsl.elevator.models.resources;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GroupsItem{

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("kens")
	private List<Object> kens;

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

	public List<Object> getKens(){
		return kens;
	}

	public String getDesc(){
		return desc;
	}

	public List<String> getProducts(){
		return products;
	}
}