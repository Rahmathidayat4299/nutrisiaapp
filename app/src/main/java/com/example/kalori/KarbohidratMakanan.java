package com.example.kalori;

public class KarbohidratMakanan {
    private String namaMakanan;
    private double jumlahKarbohidrat;

    public KarbohidratMakanan(String namaMakanan, double jumlahKarbohidrat) {
        this.namaMakanan = namaMakanan;
        this.jumlahKarbohidrat = jumlahKarbohidrat;
    }

    @Override
    public String toString() {
        return namaMakanan;
    }

    public double getJumlahKarbohidrat() {return jumlahKarbohidrat;}
}