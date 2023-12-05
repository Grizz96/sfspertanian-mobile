package com.example.sfspertanian;
// LocationItem.java
public class LocationItem {
    public static int getIdSawah;
    private int idSawah;
    private String locationName;
    private String locationAddress;
    private String locationSize;
    private String locationDescription;
    private int idUser;
    private String startDate;

    public LocationItem(int idSawah, String locationName, String locationAddress,
                        String locationSize, String locationDescription,
                        int idUser, String startDate) {
        this.idSawah = idSawah;
        this.locationName = locationName;
        this.locationAddress = locationAddress;
        this.locationSize = locationSize;
        this.locationDescription = locationDescription;
        this.idUser = idUser;
        this.startDate = startDate;
    }
    public void setIdSawah(int idSawah) {
        this.idSawah = idSawah;
    }
    public int getIdSawah() {
        return idSawah;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public String getLocationSize() {
        return locationSize;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getStartDate() {
        return startDate;
    }
}
