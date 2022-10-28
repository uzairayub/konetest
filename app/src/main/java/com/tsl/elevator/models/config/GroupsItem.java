package com.tsl.elevator.models.config;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GroupsItem{

	@SerializedName("group_id")
	private int groupId;

	@SerializedName("lifts")
	private List<LiftsItem> lifts;

	@SerializedName("terminals")
	private List<Integer> terminals;

	public int getGroupId(){
		return groupId;
	}

	public List<LiftsItem> getLifts(){
		return lifts;
	}

	public List<Integer> getTerminals(){
		return terminals;
	}
}