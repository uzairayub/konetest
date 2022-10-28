package com.tsl.elevator.models.actions;

import com.google.gson.annotations.SerializedName;

public class CallTypesItem {

    @SerializedName("action_id")
    private int actionId;

    @SerializedName("action_type")
    private String actionType;

    @SerializedName("preferred_allowed")
    private boolean preferredAllowed;

    @SerializedName("name")
    private String name;

    @SerializedName("enabled")
    private boolean enabled;

    @SerializedName("key")
    private int key;

    @SerializedName("direct_allowed")
    private boolean directAllowed;

    @SerializedName("group_call_allowed")
    private boolean groupCallAllowed;

    public int getActionId() {
        return actionId;
    }

    public String getActionType() {
        return actionType;
    }

    public boolean isPreferredAllowed() {
        return preferredAllowed;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getKey() {
        return key;
    }

    public boolean isDirectAllowed() {
        return directAllowed;
    }

    public boolean isGroupCallAllowed() {
        return groupCallAllowed;
    }

    @Override
    public String toString() {
        return actionType + " - " + name + " - " + actionId;
    }
}