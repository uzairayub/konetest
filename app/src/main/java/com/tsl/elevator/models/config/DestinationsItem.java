package com.tsl.elevator.models.config;

import com.google.gson.annotations.SerializedName;

public class DestinationsItem {

    @SerializedName("exit")
    private boolean exit;

    @SerializedName("group_floor_id")
    private int groupFloorId;

    @SerializedName("group_side")
    private int groupSide;

    @SerializedName("short_name")
    private String shortName;

    @SerializedName("area_id")
    private int areaId;

    public boolean isExit() {
        return exit;
    }

    public int getGroupFloorId() {
        return groupFloorId;
    }

    public int getGroupSide() {
        return groupSide;
    }

    public String getShortName() {
        return shortName;
    }

    public int getAreaId() {
        return areaId;
    }

    @Override
    public String toString() {
        return shortName + " - " + areaId;
    }
}