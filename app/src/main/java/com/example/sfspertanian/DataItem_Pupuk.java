package com.example.sfspertanian;

public class DataItem_Pupuk {

    private String namaPupuk;
    private String gambarPathMain;
    private String deskripsiSingkat;

    public DataItem_Pupuk(String namaPupuk, String deskripsiSingkat, String gambarPathMain) {
        this.namaPupuk = namaPupuk;
        this.deskripsiSingkat = deskripsiSingkat;
        this.gambarPathMain = gambarPathMain;
    }


    public String getNamaBibit() {
        return namaPupuk;
    }

    public String getDeskripsiSingkat() {
        return deskripsiSingkat;
    }
    public String getGambarPathMain() {
        return gambarPathMain;
    }
}
