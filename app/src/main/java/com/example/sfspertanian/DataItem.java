package com.example.sfspertanian;

public class DataItem {

    private String namaBibit;
    private String gambarPathMain;
    private String deskripsiSingkat;

    public DataItem(String namaBibit, String deskripsiSingkat, String gambarPathMain) {
        this.namaBibit = namaBibit;
        this.deskripsiSingkat = deskripsiSingkat;
        this.gambarPathMain = gambarPathMain;
    }


    public String getNamaBibit() {
        return namaBibit;
    }

    public String getDeskripsiSingkat() {
        return deskripsiSingkat;
    }
    public String getGambarPathMain() {
        return gambarPathMain;
    }
}
