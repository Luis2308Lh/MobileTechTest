package com.example.covidtest.apicovid;

public enum CountryDataEnum {
    CONFIRMED("confirmed"),
    RECOVERED("recovered"),
    DEATHS ("deaths"),
    COUNTRY ("country"),
    POPULATION ("population"),
    SQ_KM_AREA ("sq_km_area"),
    LIFE_EXPECTANCY ("life_expectancy"),
    ELEVATION_IN_METERS ("elevation_in_meters"),
    CONTINENT ("continent"),
    ABBREVIATION ("abbreviation"),
    LOCATION ("location"),
    ISO ("iso"),
    CAPITAL_CITY("capital_city"),
    LAT ("lat"),
    LONG ("long"),
    UPDATED("updated");


    public final String label;
    private CountryDataEnum(String label){
        this.label = label;
    }
}
