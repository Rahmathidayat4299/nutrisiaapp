package com.example.kalori.model.history.request;

import com.google.gson.annotations.SerializedName;

public class AddHistoryRequest {

    @SerializedName("faktor_aktivitas_fisik")
    public String faktorAktivitasFisik;

    @SerializedName("is_diet")
    public int isDiet;

    @SerializedName("total_kalori_kkal")
    public float total_kalori_kkal;

    @SerializedName("total_protein_gr")
    public float total_protein_kkal;

    @SerializedName("total_lemak_gr")
    public float total_lemak_kkal;

    @SerializedName("total_karbo_gr")
    public float total_karbo_kkal;

    @SerializedName("username")
    public String username;

    @SerializedName("makanan1")
    public String makanan1;

    @SerializedName("makanan2")
    public String makanan2;

    @SerializedName("makanan3")
    public String makanan3;

    @SerializedName("makanan4")
    public String makanan4;

    @SerializedName("bm1")
    public String bm1;

    @SerializedName("bm2")
    public String bm2;

    @SerializedName("bm3")
    public String bm3;

    @SerializedName("bm4")
    public String bm4;

    @SerializedName("kkal1")
    public String kkal1;

    @SerializedName("kkal2")
    public String kkal2;

    @SerializedName("kkal3")
    public String kkal3;

    @SerializedName("kkal4")
    public String kkal4;

    @SerializedName("lemak1")
    public String lemak1;

    @SerializedName("lemak2")
    public String lemak2;

    @SerializedName("lemak3")
    public String lemak3;

    @SerializedName("lemak4")
    public String lemak4;

    @SerializedName("protein1")
    public String protein1;

    @SerializedName("protein2")
    public String protein2;

    @SerializedName("protein3")
    public String protein3;

    @SerializedName("protein4")
    public String protein4;

    @SerializedName("karbo1")
    public String karbo1;

    @SerializedName("karbo2")
    public String karbo2;

    @SerializedName("karbo3")
    public String karbo3;

    @SerializedName("karbo4")
    public String karbo4;

    @SerializedName("batas_kalori_harian")
    public String batasKalori;

    public AddHistoryRequest(String faktorAktivitasFisik, int isDiet, float totalKaloriKkal, String batasKalori,float totalProteinKkal,
                             float totalLemakKkal, float totalKarboKkal, String username,
                             String makanan1, String makanan2, String makanan3, String makanan4,
                             String bm1, String bm2, String bm3, String bm4,
                             String kkal1, String kkal2, String kkal3, String kkal4,
                             String lemak1, String lemak2, String lemak3, String lemak4,
                             String protein1, String protein2, String protein3, String protein4,
                             String karbo1, String karbo2, String karbo3, String karbo4) {
        this.faktorAktivitasFisik = faktorAktivitasFisik;
        this.isDiet = isDiet;
        this.total_kalori_kkal = totalKaloriKkal;
        this.batasKalori = batasKalori;
        this.total_protein_kkal = totalProteinKkal;
        this.total_lemak_kkal = totalLemakKkal;
        this.total_karbo_kkal = totalKarboKkal;
        this.username = username;
        this.makanan1 = makanan1.isBlank() ? "-" : makanan1;
        this.makanan2 = makanan2.isBlank() ? "-" : makanan2;
        this.makanan3 = makanan3.isBlank() ? "-" : makanan3;
        this.makanan4 = makanan4.isBlank() ? "-" : makanan4;
        this.bm1 = bm1;
        this.bm2 = bm2;
        this.bm3 = bm3;
        this.bm4 = bm4;
        this.kkal1 = kkal1;
        this.kkal2 = kkal2;
        this.kkal3 = kkal3;
        this.kkal4 = kkal4;
        this.lemak1 = lemak1;
        this.lemak2 = lemak2;
        this.lemak3 = lemak3;
        this.lemak4 = lemak4;
        this.protein1 = protein1;
        this.protein2 = protein2;
        this.protein3 = protein3;
        this.protein4 = protein4;
        this.karbo1 = karbo1;
        this.karbo2 = karbo2;
        this.karbo3 = karbo3;
        this.karbo4 = karbo4;
    }
}
