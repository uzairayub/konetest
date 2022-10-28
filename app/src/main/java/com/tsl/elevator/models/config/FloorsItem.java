package com.tsl.elevator.models.config;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FloorsItem{

	@SerializedName("lift_floor_id")
	private int liftFloorId;

	@SerializedName("group_floor_id")
	private int groupFloorId;

	@SerializedName("sides")
	private List<SidesItem> sides;

	@SerializedName("height")
	private int height;

	public int getLiftFloorId(){
		return liftFloorId;
	}

	public int getGroupFloorId(){
		return groupFloorId;
	}

	public List<SidesItem> getSides(){
		return sides;
	}

	public int getHeight(){
		return height;
	}
}