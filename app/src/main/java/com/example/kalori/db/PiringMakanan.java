package com.example.kalori.db;

import java.io.StringReader;

public class PiringMakanan {
    public String makanan;
    public double berat;
    public String energi;
    public String protein;
    public String lemak;
    public String karbohidrat;

    public PiringMakanan(String makanan, double berat, String energi, String protein, String lemak, String karbohidrat) {
        this.makanan = makanan;
        this.berat = berat;
        this.energi = energi;
        this.protein = protein;
        this.lemak = lemak;
        this.karbohidrat = karbohidrat;
    }
}
