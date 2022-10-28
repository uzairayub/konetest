package com.tsl.elevator.models.config;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class LiftsItem{

	@SerializedName("floors")
	private List<FloorsItem> floors;

	@SerializedName("lift_name")
	private String liftName;

	@SerializedName("decks")
	private List<DecksItem> decks;

	@SerializedName("lift_id")
	private int liftId;

	public List<FloorsItem> getFloors(){
		return floors;
	}

	public String getLiftName(){
		return liftName;
	}

	public List<DecksItem> getDecks(){
		return decks;
	}

	public int getLiftId(){
		return liftId;
	}
}