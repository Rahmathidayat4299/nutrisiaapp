package com.example.kalori.db;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MenuMakanan {

    public MenuMakanan() {}
    public MenuMakanan(String tanggal, String jam,String makanan1, String makanan2, String makanan3, String makanan4, String bm1, String bm2, String bm3, String bm4, String totalcl, String user) {
        this.tanggal = tanggal;
        this.jam = jam;
        this.makanan1 = makanan1;
        this.makanan2 = makanan2;
        this.makanan3 = makanan3;
        this.makanan4 = makanan4;
        this.bm1 = bm1;
        this.bm2 = bm2;
        this.bm3 = bm3;
        this.bm4 = bm4;
        this.totalcl = totalcl;
        this.user = user;

        String jenisMakanan = this.makanan1;
        if (!this.makanan2.isEmpty()) {
            jenisMakanan = jenisMakanan.concat("; " + this.makanan2);
        }
        if (!this.makanan3.isEmpty()) {
            jenisMakanan = jenisMakanan.concat("; " + this.makanan3);
        }
        if (!this.makanan4.isEmpty()) {
            jenisMakanan = jenisMakanan.concat("; " + this.makanan4);
        }
        this.jenis = jenisMakanan;
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "tanggal")
    public String tanggal;

    @ColumnInfo(name = "jam")
    public String jam;

    @ColumnInfo(name = "jenis")
    public String jenis;

    @ColumnInfo(name = "makanan1")
    public String makanan1;

    @ColumnInfo(name = "makanan2")
    public String makanan2;

    @ColumnInfo(name = "makanan3")
    public String makanan3;

    @ColumnInfo(name = "makanan4")
    public String makanan4;

    @ColumnInfo(name = "bm1")
    public String bm1;

    @ColumnInfo(name = "bm2")
    public String bm2;

    @ColumnInfo(name = "bm3")
    public String bm3;

    @ColumnInfo(name = "bm4")
    public String bm4;

    @ColumnInfo(name="totalcl")
    public String totalcl;

    @ColumnInfo(name="user")
    public String user;
}
