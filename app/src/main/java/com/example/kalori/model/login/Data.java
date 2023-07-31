package com.example.kalori.model.login;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("berat_badan_kg")
	private String beratBadanKg;

	@SerializedName("tempat_lahir")
	private String tempatLahir;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("tinggi_badan_cm")
	private String tinggiBadanCm;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("jenis_kelamin")
	private String jenisKelamin;

	@SerializedName("tanggal_lahir")
	private String tanggalLahir;

	@SerializedName("username")
	private String username;

	public String getBeratBadanKg(){
		return beratBadanKg;
	}

	public String getTempatLahir(){
		return tempatLahir;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getTinggiBadanCm(){
		return tinggiBadanCm;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getId(){
		return id;
	}

	public String getJenisKelamin(){
		return jenisKelamin;
	}

	public String getTanggalLahir(){
		return tanggalLahir;
	}

	public String getUsername(){
		return username;
	}
}