package com.example.kalori.model.history.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HistoryResponse {

	@SerializedName("data")
	private List<DataItemV2> data;

	@SerializedName("message")
	private String message;

	public List<DataItemV2> getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}
}