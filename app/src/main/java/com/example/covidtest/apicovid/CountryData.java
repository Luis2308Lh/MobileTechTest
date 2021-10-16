package com.example.covidtest.apicovid;

import java.util.Date;

public class CountryData {

    private String country;
    private int confirmed;
    private int recovered;
    private int deaths;
    private int population;
    private int kmArea;
    private double lifeExpectancy;
    private int elevationMeters;
    private String continent;
    private String abbreviation;
    private String location;
    private int iso;
    private String capitalCity;
    private double latitude;
    private double longitude;
    private Date updated;
    private boolean check;

    public CountryData() {
    }


    public CountryData(String country, int confirmed, int recovered, int deaths, int population, int kmArea, double lifeExpectancy, int elevationMeters, String continent, String abbreviation, String location, int iso, String capitalCity, double latitude, double longitude, Date updated, boolean check) {
        this.country = country;
        this.confirmed = confirmed;
        this.recovered = recovered;
        this.deaths = deaths;
        this.population = population;
        this.kmArea = kmArea;
        this.lifeExpectancy = lifeExpectancy;
        this.elevationMeters = elevationMeters;
        this.continent = continent;
        this.abbreviation = abbreviation;
        this.location = location;
        this.iso = iso;
        this.capitalCity = capitalCity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.updated = updated;
        this.check = check;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getKmArea() {
        return kmArea;
    }

    public void setKmArea(int kmArea) {
        this.kmArea = kmArea;
    }

    public double getLifeExpectancy() {
        return lifeExpectancy;
    }

    public void setLifeExpectancy(double lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    public int getElevationMeters() {
        return elevationMeters;
    }

    public void setElevationMeters(int elevationMeters) {
        this.elevationMeters = elevationMeters;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getIso() {
        return iso;
    }

    public void setIso(int iso) {
        this.iso = iso;
    }

    public String getCapitalCity() {
        return capitalCity;
    }

    public void setCapitalCity(String capitalCity) {
        this.capitalCity = capitalCity;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

}
