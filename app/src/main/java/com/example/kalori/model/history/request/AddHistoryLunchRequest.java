package com.example.kalori.model.history.request;

import com.google.gson.annotations.SerializedName;

public class AddHistoryLunchRequest {

	@SerializedName("lunch_protein")
	private float lunchProtein;

	@SerializedName("lunch_karbo")
	private float lunchKarbo;

	@SerializedName("waktu_lunch")
	private String waktulunch;

	@SerializedName("lunch_lemak")
	private float lunchLemak;

	@SerializedName("lunch_kkal")
	private float lunchKkal;

	@SerializedName("username")
	private String username;

	@SerializedName("total_lunch_kkal")
	private float totallunchKkal;

	public AddHistoryLunchRequest(float lunchProtein, float lunchKarbo, String waktulunch, float lunchLemak, float lunchKkal, String username, float totallunchKkal) {
		this.lunchProtein = lunchProtein;
		this.lunchKarbo = lunchKarbo;
		this.waktulunch = waktulunch;
		this.lunchLemak = lunchLemak;
		this.lunchKkal = lunchKkal;
		this.username = username;
		this.totallunchKkal = totallunchKkal;
	}

	public float getlunchProtein(){return lunchProtein;}

	public float getlunchKarbo(){return lunchKarbo;}

	public String getWaktulunch(){return waktulunch;}

	public float getlunchLemak(){return lunchLemak;}

	public float getlunchKkal(){return lunchKkal;}

	public String getUsername(){
		return username;
	}

	public float getTotallunchKkal(){
		return totallunchKkal;
	}
}