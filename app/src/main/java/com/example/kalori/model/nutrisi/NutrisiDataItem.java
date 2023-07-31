package com.example.kalori.model.nutrisi;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NutrisiDataItem implements Serializable {

	@SerializedName("No")
	private int no;

	@SerializedName("kelompok")
	private String kelompok;

	@SerializedName("karbohidrat")
	private String karbohidrat;

	@SerializedName("abu")
	private String abu;

	@SerializedName("protein")
	private String protein;

	@SerializedName("nama_pangan")
	private String namaPangan;

	@SerializedName("kode_pangan")
	private String kodePangan;

	@SerializedName("tipe")
	private String tipe;

	@SerializedName("energi")
	private String energi;

	@SerializedName("lemak")
	private String lemak;

	public NutrisiDataItem(int no, String kelompok, String karbohidrat, String abu, String protein, String namaPangan, String kodePangan, String tipe, String energi, String lemak) {
		this.no = no;
		this.kelompok = kelompok;
		this.karbohidrat = karbohidrat;
		this.abu = abu;
		this.protein = protein;
		this.namaPangan = namaPangan;
		this.kodePangan = kodePangan;
		this.tipe = tipe;
		this.energi = energi;
		this.lemak = lemak;
	}

	public int getNo(){
		return no;
	}

	public String getKelompok(){
		return kelompok;
	}

	public String getKarbohidrat(){
		return karbohidrat;
	}

	public String getAbu(){
		return abu;
	}

	public String getProtein(){
		return protein;
	}

	public String getNamaPangan(){
		return namaPangan;
	}

	public String getKodePangan(){
		return kodePangan;
	}

	public String getTipe(){
		return tipe;
	}

	public String getEnergi(){
		return energi;}

	public String getLemak(){
		return lemak;
	}
}