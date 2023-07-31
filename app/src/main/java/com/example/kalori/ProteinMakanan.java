package com.example.kalori;

public class ProteinMakanan {
    private String namaMakanan;
    private double jumlahProtein;

    public ProteinMakanan(String namaMakanan, double jumlahProtein) {
        this.namaMakanan = namaMakanan;
        this.jumlahProtein = jumlahProtein;
    }

    @Override
    public String toString() {
        return namaMakanan;
    }

    public double getJumlahProtein() {return jumlahProtein;}
}
