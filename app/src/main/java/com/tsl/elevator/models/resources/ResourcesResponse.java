package com.tsl.elevator.models.resources;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResourcesResponse{

	@SerializedName("equipments")
	private List<EquipmentsItem> equipments;

	@SerializedName("buildings")
	private List<BuildingsItem> buildings;

	public List<EquipmentsItem> getEquipments(){
		return equipments;
	}

	public List<BuildingsItem> getBuildings(){
		return buildings;
	}
}