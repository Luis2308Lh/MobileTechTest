package com.example.covidtest.apicovid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.covidtest.MainActivity;
import com.example.covidtest.R;
import com.example.covidtest.Util;

import java.util.ArrayList;

public class AdapterData extends RecyclerView.Adapter<AdapterData.ViewHolderData> {

    ArrayList<CountryData> listCountries;
    private Context mContext;
    Util util = new Util();

    public AdapterData(ArrayList<CountryData> listCountries, Context context){
        this.listCountries = listCountries;
        this.mContext = context;
    }

    @Override
    public ViewHolderData onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,null);
        return new ViewHolderData(view);
    }

    @Override
    public void onBindViewHolder(AdapterData.ViewHolderData holder, int position) {
        holder.showCountryData(listCountries.get(position));
    }

    @Override
    public int getItemCount() {
        return listCountries.size();
    }

    public class ViewHolderData extends RecyclerView.ViewHolder {
        TextView country;
        TextView activeCases;
        TextView deaths;
        TextView date;
        TextView activeCasesHab;
        TextView deathHab;
        CheckBox chartBox;
        public ViewHolderData(View itemView) {
            super(itemView);
            country = (TextView) itemView.findViewById(R.id.TVCountryName);
            activeCases = (TextView) itemView.findViewById(R.id.TVActiveCases);
            deaths = (TextView) itemView.findViewById(R.id.TVDeath);
            date = (TextView) itemView.findViewById(R.id.TVDate);
            activeCasesHab = (TextView) itemView.findViewById(R.id.TVActiveCasesHab);
            deathHab = (TextView) itemView.findViewById(R.id.TVDeathHab);
            chartBox = (CheckBox) itemView.findViewById(R.id.CBCountry);

        }

        public void showCountryData(CountryData countryData) {
            country.setText(countryData.getCountry());
            activeCases.setText("Active Cases: "+countryData.getConfirmed());
            deaths.setText("Death: "+countryData.getDeaths());
            if(countryData.getUpdated() != null){
                date.setText("Last Update: "+ util.dateToString(countryData.getUpdated()));
            }else {
                date.setText("No info");
            }

            activeCasesHab.setText("Active Case for 100k: "+ hundredHab(countryData.getPopulation(),countryData.getConfirmed()));
            deathHab.setText("Death for 100k: "+hundredHab(countryData.getPopulation(),countryData.getDeaths()));
            chartBox.setOnCheckedChangeListener(null);

            chartBox.setChecked(countryData.isCheck());

            chartBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    ((MainActivity)mContext).updateChekState(countryData.getCountry(),b);
                }
            });
        }

        public String hundredHab(int population, int cases){
            String error = "No info";
            float hab = 100000, pop = population, fCas = cases;
            float hundredHab = hab * (fCas/pop);
            String total = String.valueOf(hundredHab);
            if(!total.matches(".*\\d.*")){
                return error;
            }
            return  total;
        }
        
    }
}
