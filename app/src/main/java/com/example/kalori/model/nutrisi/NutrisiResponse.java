package com.example.kalori.model.nutrisi;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class NutrisiResponse{

	@SerializedName("data")
	private List<NutrisiDataItem> data;

	@SerializedName("message")
	private String message;

	public List<NutrisiDataItem> getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}
}