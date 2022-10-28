package com.tsl.elevator.models.config;

import com.google.gson.annotations.SerializedName;

public class ConfigResponse {

    @SerializedName("data")
    private ConfigData configData;

    @SerializedName("requestId")
    private String requestId;

    @SerializedName("groupId")
    private String groupId;

    @SerializedName("callType")
    private String callType;

    @SerializedName("buildingId")
    private String buildingId;

    public ConfigData getConfigData() {
        return configData;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getCallType() {
        return callType;
    }

    public String getBuildingId() {
        return buildingId;
    }
}