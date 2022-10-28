package com.tsl.elevator.models.actions;

import com.google.gson.annotations.SerializedName;

public class ActionsResponse {

    @SerializedName("data")
    private ActionsData actionsData;

    @SerializedName("requestId")
    private String requestId;

    @SerializedName("groupId")
    private String groupId;

    @SerializedName("callType")
    private String callType;

    @SerializedName("buildingId")
    private String buildingId;

    public ActionsData getActionsData() {
        return actionsData;
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