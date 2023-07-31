package com.example.kalori;

public class WaktuMakan {
    private String kategori;

    public WaktuMakan(String kategori) {
        this.kategori = kategori;
    }

    @Override
    public String toString() {
        return kategori;
    }
}
