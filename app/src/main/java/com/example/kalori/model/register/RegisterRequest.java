package com.example.kalori.model.register;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest{

	@SerializedName("berat_badan_kg")
	private int beratBadanKg;

	@SerializedName("password")
	private String password;

	@SerializedName("tempat_lahir")
	private String tempatLahir;

	@SerializedName("tinggi_badan_cm")
	private int tinggiBadanCm;

	@SerializedName("jenis_kelamin")
	private String jenisKelamin;

	@SerializedName("tanggal_lahir")
	private String tanggalLahir;

	@SerializedName("username")
	private String username;

	public RegisterRequest(int beratBadanKg, String password, String tempatLahir, int tinggiBadanCm, String jenisKelamin, String tanggalLahir, String username) {
		this.beratBadanKg = beratBadanKg;
		this.password = password;
		this.tempatLahir = tempatLahir;
		this.tinggiBadanCm = tinggiBadanCm;
		this.jenisKelamin = jenisKelamin;
		this.tanggalLahir = tanggalLahir;
		this.username = username;
	}

	public int getBeratBadanKg(){
		return beratBadanKg;
	}

	public String getPassword(){
		return password;
	}

	public String getTempatLahir(){
		return tempatLahir;
	}

	public int getTinggiBadanCm(){
		return tinggiBadanCm;
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