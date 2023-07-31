package com.example.kalori.model.history.request;

import com.google.gson.annotations.SerializedName;

public class AddHistoryDietRequest{

	@SerializedName("total1_kalori_kkal")
	private float total1KaloriKkal;

	@SerializedName("faktor_aktivitas_fisik")
	private String faktorAktivitasFisik;

	@SerializedName("is_diet")
	private float isDiet;

	@SerializedName("username")
	private String username;

	public AddHistoryDietRequest(float total1KaloriKkal, String faktorAktivitasFisik, float isDiet, String username) {
		this.total1KaloriKkal = total1KaloriKkal;
		this.faktorAktivitasFisik = faktorAktivitasFisik;
		this.isDiet = isDiet;
		this.username = username;
	}

	public float getTotal1KaloriKkal(){
		return total1KaloriKkal;
	}

	public String getFaktorAktivitasFisik(){
		return faktorAktivitasFisik;
	}

	public float getIsDiet(){
		return isDiet;
	}

	public String getUsername(){
		return username;
	}
}