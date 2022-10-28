package com.tsl.elevator.models.monitoring;

import com.google.gson.annotations.SerializedName;

public class CallStatusResponse{

	@SerializedName("requestId")
	private String requestId;

	@SerializedName("groupId")
	private String groupId;

	@SerializedName("data")
	private CallStatusData callStatusData;

	@SerializedName("subtopic")
	private String subtopic;

	@SerializedName("callType")
	private String callType;

	@SerializedName("buildingId")
	private String buildingId;

	public String getRequestId(){
		return requestId;
	}

	public String getGroupId(){
		return groupId;
	}

	public CallStatusData getCallStatusData(){
		return callStatusData;
	}

	public String getSubtopic(){
		return subtopic;
	}

	public String getCallType(){
		return callType;
	}

	public String getBuildingId(){
		return buildingId;
	}
}