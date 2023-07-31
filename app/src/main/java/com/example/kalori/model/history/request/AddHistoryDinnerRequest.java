package com.example.kalori.model.history.request;

import com.google.gson.annotations.SerializedName;

public class AddHistoryDinnerRequest {

	@SerializedName("dinner_protein")
	private float dinnerProtein;

	@SerializedName("dinner_karbo")
	private float dinnerKarbo;

	@SerializedName("waktu_dinner")
	private String waktudinner;

	@SerializedName("dinner_lemak")
	private float dinnerLemak;

	@SerializedName("dinner_kkal")
	private float dinnerKkal;

	@SerializedName("username")
	private String username;

	@SerializedName("total_dinner_kkal")
	private float totaldinnerKkal;

	public AddHistoryDinnerRequest(float dinnerProtein, float dinnerKarbo, String waktudinner, float dinnerLemak, float dinnerKkal, String username, float totaldinnerKkal) {
		this.dinnerProtein = dinnerProtein;
		this.dinnerKarbo = dinnerKarbo;
		this.waktudinner = waktudinner;
		this.dinnerLemak = dinnerLemak;
		this.dinnerKkal = dinnerKkal;
		this.username = username;
		this.totaldinnerKkal = totaldinnerKkal;
	}

	public float getdinnerProtein(){
		return dinnerProtein;
	}

	public float getdinnerKarbo(){
		return dinnerKarbo;
	}

	public String getWaktudinner(){
		return waktudinner;
	}

	public float getdinnerLemak(){
		return dinnerLemak;
	}

	public float getdinnerKkal(){
		return dinnerKkal;
	}

	public String getUsername(){
		return username;
	}

	public float getTotaldinnerKkal(){
		return totaldinnerKkal;
	}
}