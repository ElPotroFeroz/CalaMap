package com.example.calamap;

public class Beach {
    private String name;
    private double latitude;
    private double longitude;
    private String description;

    private String url;

    public Beach(String name, double latitude, double longitude, String url, String description) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getDescription() { return description; }

    public String getUrl() { return url; }
}
