package com.example.sfspertanian;

public class SemprotanItem {
    private String idSemprotan;
    private String namaSemprotan;

    public SemprotanItem(String idSemprotan, String namaSemprotan) {
        this.idSemprotan = idSemprotan;
        this.namaSemprotan = namaSemprotan;
    }

    public String getIdSemprotan() {
        return idSemprotan;
    }

    public String getNamaSemprotan() {
        return namaSemprotan;
    }

    @Override
    public String toString() {
        return namaSemprotan;
    }
}
