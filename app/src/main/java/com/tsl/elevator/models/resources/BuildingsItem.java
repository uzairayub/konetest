package com.tsl.elevator.models.resources;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class BuildingsItem{

	@SerializedName("name")
	private String name;

	@SerializedName("groups")
	private List<GroupsItem> groups;

	@SerializedName("id")
	private String id;

	@SerializedName("desc")
	private String desc;

	public String getName(){
		return name;
	}

	public List<GroupsItem> getGroups(){
		return groups;
	}

	public String getId(){
		return id;
	}

	public String getDesc(){
		return desc;
	}
}