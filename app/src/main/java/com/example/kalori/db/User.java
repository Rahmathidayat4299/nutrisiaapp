package com.example.kalori.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "nama")
    private String nama;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "tempat_lahir")
    private String tempatLahir;

    @ColumnInfo(name = "tanggal_lahir")
    private String tanggalLahir;

    @ColumnInfo(name = "berat")
    private int berat;

    @ColumnInfo(name = "tinggi")
    private int tinggi;

    @ColumnInfo(name = "gender")
    private String gender;

    public int getUid() { return uid; }
    public void setUid(int uid) { this.uid = uid;}

    public void setPassword(String password){ this.password = password; }
    public String getPassword() { return this.password; }

    public String getNama(){ return nama; }
    public void setNama(String nama){ this.nama = nama;}

    public void setTanggalLahir(String tgl){this.tanggalLahir = tgl; }
    public String getTanggalLahir() { return this.tanggalLahir; }

    public void setTempatLahir(String tempat){ this.tempatLahir = tempat; }
    public String getTempatLahir(){ return this.tempatLahir; }

    public void setBerat(int berat){ this.berat = berat;}
    public int getBerat(){ return this.berat; }

    public void setTinggi(int tinggi){ this.tinggi = tinggi; }
    public int getTinggi() { return this.tinggi; }

    public void setGender(String gender){ this.gender = gender; }
    public String getGender() { return this.gender; }
}
