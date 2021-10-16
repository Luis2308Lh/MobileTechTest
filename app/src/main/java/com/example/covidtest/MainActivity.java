package com.example.covidtest;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidtest.apicovid.AdapterData;
import com.example.covidtest.apicovid.CountryData;
import com.example.covidtest.apicovid.CountryDataEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    ArrayList <CountryData> countryDataList = new ArrayList<>();
    RecyclerView recycler ;
    Spinner options;
    EditText searchData;
    Button btChart;
    Util util = new Util();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadCountriesData();
        loadViews();
        searchData.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(searchData.getText().length() != 0 ){
                    String search = searchData.getText().toString();
                    recyclerWithFilter(search);
                } else if (searchData.getText().length() == 0) {
                    loadRecyclerView();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this,R.array.options, android.R.layout.simple_spinner_item);
        options.setAdapter(adapterSpinner);
        options.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 1:
                        Collections.sort(countryDataList, new Comparator<CountryData>() {
                            @Override
                            public int compare(CountryData c1, CountryData c2) {
                                return c2.getConfirmed() - c1.getConfirmed();
                            }
                        });
                        loadRecyclerView();
                        break;
                    case 2:
                        Collections.sort(countryDataList, new Comparator<CountryData>() {
                            @Override
                            public int compare(CountryData c1, CountryData c2) {
                                return c2.getDeaths() - c1.getDeaths();
                            }
                        });
                        loadRecyclerView();
                        break;
                    case 3:
                        Collections.sort(countryDataList, new Comparator<CountryData>() {
                            @Override
                            public int compare(CountryData c1, CountryData c2) {
                                int hundredHabC1,hundredHabC2;
                                String HC1,HC2;
                                HC1 = hundredHab(c1.getPopulation(),c1.getConfirmed());
                                HC2 = hundredHab(c2.getPopulation(),c2.getConfirmed());
                                if(!HC1.matches(".*\\d.*")){
                                    hundredHabC1 = 0;
                                } else {
                                    hundredHabC1 = (int)Float.parseFloat(HC1);
                                }
                                if (!HC2.matches(".*\\d.*")) {
                                    hundredHabC2 = 0;
                                } else {
                                    hundredHabC2 = (int)Float.parseFloat(HC2);
                                }
                                   return hundredHabC2 - hundredHabC1;
                                }
                        });
                        loadRecyclerView();
                        break;
                    case 4:
                        Collections.sort(countryDataList, new Comparator<CountryData>() {
                            @Override
                            public int compare(CountryData c1, CountryData c2) {
                                int hundredHabC1,hundredHabC2;
                                String HC1,HC2;
                                HC1 = hundredHab(c1.getPopulation(),c1.getDeaths());
                                HC2 = hundredHab(c2.getPopulation(),c2.getDeaths());
                                if(!HC1.matches(".*\\d.*")){
                                    hundredHabC1 = 0;
                                } else {
                                    hundredHabC1 = (int)Float.parseFloat(HC1);
                                }
                                if (!HC2.matches(".*\\d.*")) {
                                    hundredHabC2 = 0;
                                } else {
                                    hundredHabC2 = (int)Float.parseFloat(HC2);
                                }
                                return hundredHabC2 - hundredHabC1;
                            }
                        });
                        loadRecyclerView();
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    showCustomDialog();

            }
        });
        loadRecyclerView();
    }
    public void loadViews(){
        options = (Spinner)findViewById(R.id.SPSort);
        searchData = (EditText)findViewById(R.id.ETSearch);
        btChart = (Button)findViewById(R.id.btSearch);
        recycler = (RecyclerView) findViewById(R.id.recyclerViewCountries);
    }

    void showCustomDialog(){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.barchartdialog);

        final BarChart barChartDialog = dialog.findViewById(R.id.barChartCasesDialog);
        final Button closeButton = dialog.findViewById(R.id.BTcloseDialog);

        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labelCountries = new ArrayList<>();
        int j = 0;
        for(int i = 0; i < countryDataList.size(); i++){
            if(countryDataList.get(i).isCheck()){
                entries.add(new BarEntry(j,countryDataList.get(i).getConfirmed()));
                labelCountries.add(countryDataList.get(i).getCountry());
                j++;
            }
        }
        BarDataSet dataSet = new BarDataSet(entries, "Active cases");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        Description description = new Description();
        description.setText("Month");

        barChartDialog.setDescription(description);

        BarData data = new BarData(dataSet);
        barChartDialog.setData(data );

        XAxis xAxis = barChartDialog.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelCountries));
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labelCountries.size());
        xAxis.setLabelRotationAngle(270);
        barChartDialog.invalidate();


        dialog.show();
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void recyclerWithFilter(String search){
        String firstLetter = search.substring(0,1);
        firstLetter = firstLetter.toUpperCase();
        String restLetter = search.substring(1);
        restLetter = restLetter.toLowerCase();
        String capSearch = firstLetter + restLetter;
        ArrayList <CountryData> searchCountries = new ArrayList<>();
        for(int i=0;i < countryDataList.size();i++){
            if(countryDataList.get(i).getCountry().contains(search)){
                searchCountries.add(countryDataList.get(i));
            }
        }
        for(int i=0;i < countryDataList.size();i++){
            if(countryDataList.get(i).getCountry().contains(capSearch)){
                if(searchCountries.isEmpty()){
                    searchCountries.add(countryDataList.get(i));
                }else{
                    for(int j=0;j < searchCountries.size();j++){
                        if(!searchCountries.get(j).getCountry().contains(capSearch)){
                            searchCountries.add(countryDataList.get(i));
                        }
                    }
                }
            }
        }
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        AdapterData adapter = new AdapterData(searchCountries,this);
        recycler.setAdapter(adapter);
    }

    public void  loadRecyclerView(){
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        AdapterData adapter = new AdapterData(countryDataList,this);
        recycler.setAdapter(adapter);
    }

    public String hundredHab(int population, int cases){
        float hab = 100000, pop = population, fCas = cases;
        float hundredHab = hab * (fCas/pop);
        String total = String.valueOf(hundredHab);
        return  total;
    }

    public void loadCountriesData (){
        String jsonCovid = loadLocalJson();
        CountryData countryData;
        ObjectMapper mapper = new ObjectMapper();

        try{
            Map<String, String> map = mapper.readValue(jsonCovid,Map.class);
            JSONObject jso = new JSONObject(jsonCovid);

           for(Map.Entry<String,String> pair :map.entrySet()){
               JSONObject JSO = jso.getJSONObject(pair.getKey());
               JSONObject country = JSO.getJSONObject("All");
               countryData = new CountryData();

               if(country.has(CountryDataEnum.CONFIRMED.label)){
                   countryData.setConfirmed(util.stringToInt(country.getString(CountryDataEnum.CONFIRMED.label)));
               }
               if(country.has(CountryDataEnum.RECOVERED.label)){
                   countryData.setRecovered(util.stringToInt(country.getString(CountryDataEnum.RECOVERED.label)));
               }
               if(country.has(CountryDataEnum.DEATHS.label)){
                   countryData.setDeaths(util.stringToInt(country.getString(CountryDataEnum.DEATHS.label)));
               }
               if(country.has(CountryDataEnum.COUNTRY.label)){
                   countryData.setCountry(country.getString(CountryDataEnum.COUNTRY.label));
               }else {
                   countryData.setCountry(pair.getKey());
               }
               if(country.has(CountryDataEnum.POPULATION.label)){
                   countryData.setPopulation(util.stringToInt(country.getString(CountryDataEnum.POPULATION.label)));
               }
               if(country.has(CountryDataEnum.SQ_KM_AREA.label)){
                   countryData.setKmArea(util.stringToInt(country.getString(CountryDataEnum.SQ_KM_AREA.label)));
               }
               if(country.has(CountryDataEnum.LIFE_EXPECTANCY.label)){
                   countryData.setLifeExpectancy(util.stringToDouble(country.getString(CountryDataEnum.LIFE_EXPECTANCY.label)));
               }
               if(country.has(CountryDataEnum.ELEVATION_IN_METERS.label)){
                   countryData.setElevationMeters(util.stringToInt(country.getString(CountryDataEnum.ELEVATION_IN_METERS.label)));
               }
               if(country.has(CountryDataEnum.CONTINENT.label)){
                   countryData.setContinent(country.getString(CountryDataEnum.CONTINENT.label));
               }
               if(country.has(CountryDataEnum.ABBREVIATION.label)){
                   countryData.setAbbreviation(country.getString(CountryDataEnum.ABBREVIATION.label));
               }
               if(country.has(CountryDataEnum.LOCATION.label)){
                   countryData.setLocation(country.getString(CountryDataEnum.LOCATION.label));
               }
               if(country.has(CountryDataEnum.ISO.label)){
                   countryData.setIso(util.stringToInt(country.getString(CountryDataEnum.ISO.label)));
               }if(country.has(CountryDataEnum.CAPITAL_CITY.label)){
                   countryData.setCapitalCity(country.getString(CountryDataEnum.CAPITAL_CITY.label));
               }
               if(country.has(CountryDataEnum.LAT.label)){
                   countryData.setLatitude(util.stringToDouble(country.getString(CountryDataEnum.LAT.label)));
               }
               if(country.has(CountryDataEnum.LONG.label)){
                   countryData.setLongitude(util.stringToDouble(country.getString(CountryDataEnum.LONG.label)));
               }if(country.has(CountryDataEnum.UPDATED.label)){
                   countryData.setUpdated(util.stringToDate(country.getString(CountryDataEnum.UPDATED.label)));
               }
               countryDataList.add(countryData);
            }
        }catch (IOException | JSONException e){
            e.printStackTrace();
        }
    }

    public String loadLocalJson (){
        String json = null;
        try {
            InputStream is = getAssets().open("covidData.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    public void updateChekState(String country, boolean state){
        for (int i = 0; i < countryDataList.size(); i ++){
            if(countryDataList.get(i).getCountry().equals(country)){
                countryDataList.get(i).setCheck(state);
            }
        }
    }
}