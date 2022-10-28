package com.tsl.elevator.managers;

import com.tsl.elevator.models.actions.ActionsData;
import com.tsl.elevator.models.config.ConfigData;
import com.tsl.elevator.models.config.DestinationsItem;

public class CommonCommandsDataManager {

    private static CommonCommandsDataManager singleton = null;

    private ConfigData configData;
    private ActionsData actionsData;
    private String buildingId;
    private String recentConfigRequestId = "";
    private String recentActionsRequestId = "";
    private DestinationsItem SourceFloor, destinationFloor;

    private CommonCommandsDataManager() {
    }

    public static CommonCommandsDataManager getInstance() {
        if (singleton == null) {
            singleton = new CommonCommandsDataManager();
        }
        return singleton;
    }

    public String getRecentConfigRequestId() {
        return recentConfigRequestId;
    }

    public void setRecentConfigRequestId(String recentConfigRequestId) {
        this.recentConfigRequestId = recentConfigRequestId;
    }

    public String getRecentActionsRequestId() {
        return recentActionsRequestId;
    }

    public void setRecentActionsRequestId(String recentActionsRequestId) {
        this.recentActionsRequestId = recentActionsRequestId;
    }

    public ConfigData getConfigData() {
        return configData;
    }

    public void setConfigData(ConfigData configData) {
        this.configData = configData;
    }

    public ActionsData getActionsData() {
        return actionsData;
    }

    public void setActionsData(ActionsData actionsData) {
        this.actionsData = actionsData;
    }

    public DestinationsItem getSourceFloor() {
        return SourceFloor;
    }

    public void setSourceFloor(DestinationsItem sourceFloor) {
        SourceFloor = sourceFloor;
    }

    public DestinationsItem getDestinationFloor() {
        return destinationFloor;
    }

    public void setDestinationFloor(DestinationsItem destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }
}
