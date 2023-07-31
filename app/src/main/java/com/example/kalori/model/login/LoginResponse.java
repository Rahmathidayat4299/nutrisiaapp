package com.example.kalori.model.login;

import com.google.gson.annotations.SerializedName;

public class LoginResponse{

	@SerializedName("data")
	private Data data;

	@SerializedName("message")
	private String message;

	public Data getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}
}