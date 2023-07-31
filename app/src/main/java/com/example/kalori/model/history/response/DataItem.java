package com.example.kalori.model.history.response;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("waktu_breakfast")
	private String waktuBreakfast;

	@SerializedName("waktu_dinner")
	private String waktuDinner;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("waktu_lunch")
	private String waktuLunch;

	@SerializedName("dinner_kkal")
	private float dinnerKkal;

	@SerializedName("total1_kalori_kkal")
	private float total1KaloriKkal;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("breakfast_lemak")
	private int breakfastLemak;

	@SerializedName("id")
	private int id;

	@SerializedName("lunch_lemak")
	private float lunchLemak;

	@SerializedName("dinner_protein")
	private float dinnerProtein;

	@SerializedName("faktor_aktivitas_fisik")
	private String faktorAktivitasFisik;

	@SerializedName("lunch_karbo")
	private float lunchKarbo;

	@SerializedName("dinner_karbo")
	private float dinnerKarbo;

	@SerializedName("breakfast_protein")
	private int breakfastProtein;

	@SerializedName("lunch_kkal")
	private float lunchKkal;

	@SerializedName("dinner_lemak")
	private float dinnerLemak;

	@SerializedName("total_kalori_kkal")
	private float totalKaloriKkal;

	@SerializedName("total_dinner_kkal")
	private float totalDinnerKkal;

	@SerializedName("breakfast_karbo")
	private int breakfastKarbo;

	@SerializedName("total_lunch_kkal")
	private float totalLunchKkal;

	@SerializedName("lunch_protein")
	private float lunchProtein;

	@SerializedName("tanggal")
	private String tanggal;

	@SerializedName("is_diet")
	private String isDiet;

	@SerializedName("breakfast_kkal")
	private float breakfastKkal;

	@SerializedName("username")
	private String username;

	@SerializedName("total_breakfast_kkal")
	private String totalBreakfastKkal;

	public String getWaktuBreakfast(){
		return waktuBreakfast;
	}

	public String getWaktuDinner(){
		return waktuDinner;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getWaktuLunch(){
		return waktuLunch;
	}

	public float getDinnerKkal(){
		return dinnerKkal;
	}

	public float getTotal1KaloriKkal(){
		return total1KaloriKkal;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public int getBreakfastLemak(){
		return breakfastLemak;
	}

	public int getId(){
		return id;
	}

	public float getLunchLemak(){
		return lunchLemak;
	}

	public float getDinnerProtein(){
		return dinnerProtein;
	}

	public String getFaktorAktivitasFisik(){
		return faktorAktivitasFisik;
	}

	public float getLunchKarbo(){
		return lunchKarbo;
	}

	public float getDinnerKarbo(){
		return dinnerKarbo;
	}

	public int getBreakfastProtein(){
		return breakfastProtein;
	}

	public float getLunchKkal(){
		return lunchKkal;
	}

	public float getDinnerLemak(){
		return dinnerLemak;
	}

	public float getTotalKaloriKkal(){
		return totalKaloriKkal;
	}

	public float getTotalDinnerKkal(){
		return totalDinnerKkal;
	}

	public int getBreakfastKarbo(){
		return breakfastKarbo;
	}

	public float getTotalLunchKkal(){
		return totalLunchKkal;
	}

	public float getLunchProtein(){
		return lunchProtein;
	}

	public String getTanggal(){
		return tanggal;
	}

	public String getIsDiet(){
		return isDiet;
	}

	public float getBreakfastKkal(){
		return breakfastKkal;
	}

	public String getUsername(){
		return username;
	}

	public String getTotalBreakfastKkal(){
		return totalBreakfastKkal;
	}
}