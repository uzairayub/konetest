package com.tsl.elevator.models.config;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SidesItem{

	@SerializedName("lift_side")
	private int liftSide;

	@SerializedName("group_side")
	private int groupSide;

	@SerializedName("decks")
	private List<Integer> decks;

	public int getLiftSide(){
		return liftSide;
	}

	public int getGroupSide(){
		return groupSide;
	}

	public List<Integer> getDecks(){
		return decks;
	}
}