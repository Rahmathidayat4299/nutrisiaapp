package com.example.kalori;

public class LemakMakanan {
    private String namaMakanan;
    private double jumlahLemak;

    public LemakMakanan(String namaMakanan, double jumlahLemak) {
        this.namaMakanan = namaMakanan;
        this.jumlahLemak = jumlahLemak;
    }

    @Override
    public String toString() {
        return namaMakanan;
    }

    public double getJumlahLemak() {return jumlahLemak;}
}
