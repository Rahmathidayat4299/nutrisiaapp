package com.example.kalori;

public class AktivitasFisik {
    private String aktivitas;
    private double bobot;

    public AktivitasFisik(String aktivitas, double bobot) {
        this.aktivitas = aktivitas;
        this.bobot = bobot;
    }

    @Override
    public String toString() {
        return aktivitas;
    }

    public double getBobot() {
        return bobot;
    }

    public String getAktivitas() {
        return aktivitas;
    }
}
