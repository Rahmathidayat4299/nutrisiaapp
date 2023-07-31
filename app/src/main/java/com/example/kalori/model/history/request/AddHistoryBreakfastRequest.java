package com.example.kalori.model.history.request;

import com.google.gson.annotations.SerializedName;

public class AddHistoryBreakfastRequest{

	@SerializedName("breakfast_protein")
	private float breakfastProtein;

	@SerializedName("breakfast_karbo")
	private float breakfastKarbo;

	@SerializedName("waktu_breakfast")
	private String waktuBreakfast;

	@SerializedName("breakfast_lemak")
	private float breakfastLemak;

	@SerializedName("breakfast_kkal")
	private float breakfastKkal;

	@SerializedName("username")
	private String username;

	@SerializedName("total_breakfast_kkal")
	private float totalBreakfastKkal;

	public AddHistoryBreakfastRequest(float breakfastProtein, float breakfastKarbo, String waktuBreakfast, float breakfastLemak, float breakfastKkal, String username, float totalBreakfastKkal) {
		this.breakfastProtein = breakfastProtein;
		this.breakfastKarbo = breakfastKarbo;
		this.waktuBreakfast = waktuBreakfast;
		this.breakfastLemak = breakfastLemak;
		this.breakfastKkal = breakfastKkal;
		this.username = username;
		this.totalBreakfastKkal = totalBreakfastKkal;
	}

	public float getBreakfastProtein(){
		return breakfastProtein;
	}

	public float getBreakfastKarbo(){
		return breakfastKarbo;
	}

	public String getWaktuBreakfast(){
		return waktuBreakfast;
	}

	public float getBreakfastLemak(){
		return breakfastLemak;
	}

	public float getBreakfastKkal(){
		return breakfastKkal;
	}

	public String getUsername(){
		return username;
	}

	public float getTotalBreakfastKkal(){
		return totalBreakfastKkal;
	}
}