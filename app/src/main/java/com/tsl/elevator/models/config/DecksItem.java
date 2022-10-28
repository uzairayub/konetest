package com.tsl.elevator.models.config;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DecksItem{

	@SerializedName("doors")
	private List<Integer> doors;

	@SerializedName("deck")
	private int deck;

	@SerializedName("area_id")
	private int areaId;

	@SerializedName("terminals")
	private List<Object> terminals;

	public List<Integer> getDoors(){
		return doors;
	}

	public int getDeck(){
		return deck;
	}

	public int getAreaId(){
		return areaId;
	}

	public List<Object> getTerminals(){
		return terminals;
	}
}