package com.tsl.elevator.models.config;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ConfigData{

	@SerializedName("version_minor")
	private int versionMinor;

	@SerializedName("destinations")
	private List<DestinationsItem> destinations;

	@SerializedName("groups")
	private List<GroupsItem> groups;

	@SerializedName("version_major")
	private int versionMajor;

	public int getVersionMinor(){
		return versionMinor;
	}

	public List<DestinationsItem> getDestinations(){
		return destinations;
	}

	public List<GroupsItem> getGroups(){
		return groups;
	}

	public int getVersionMajor(){
		return versionMajor;
	}
}