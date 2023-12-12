package com.example.sfspertanian;

public class Sawah {
    private String idSawah;
    private String namaSawah;

    public String getIdSawah() {
        return idSawah;
    }

    public void setIdSawah(String idSawah) {
        this.idSawah = idSawah;
    }

    public String getNamaSawah() {
        return namaSawah;
    }

    public void setNamaSawah(String namaSawah) {
        this.namaSawah = namaSawah;
    }

    @Override
    public String toString() {
        // Digunakan oleh Spinner untuk menampilkan teks
        return idSawah + " | " + namaSawah;
    }
}
