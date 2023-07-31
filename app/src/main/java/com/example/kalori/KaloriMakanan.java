package com.example.kalori;

public class KaloriMakanan {
    private String namaMakanan;
    private double jumlahKalori;

    public KaloriMakanan(String namaMakanan, double jumlahKalori) {
        this.namaMakanan = namaMakanan;
        this.jumlahKalori = jumlahKalori;
    }

    @Override
    public String toString() {
        return namaMakanan;
    }

    public double getJumlahKalori() {
        return jumlahKalori;
    }
}
