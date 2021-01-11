package com.example.coronatracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coronatracker.R;
import com.example.coronatracker.jsonClass.CoronaApiCurl;

import java.util.List;

public class MyAdapterCount extends RecyclerView.Adapter<MyAdapterCount.MyViewHolder>{

     Context context;
     List<CoronaApiCurl> coronaApiCurlList;

    public MyAdapterCount(Context context, List<CoronaApiCurl> coronaApiCurlList) {
        this.context = context;
        this.coronaApiCurlList = coronaApiCurlList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_countries, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.country_count.setText(coronaApiCurlList.get(position).getCountry());
        holder.cases_count.setText("Cases :"+" " +coronaApiCurlList.get(position).getCases());
        holder.todayCases_count.setText("Today Cases :"+" " +coronaApiCurlList.get(position).getTodayCases());
        holder.deaths_count.setText("Deaths :"+" " +coronaApiCurlList.get(position).getDeaths());
        holder.todayDeaths_count.setText("Today Deaths :"+" " +coronaApiCurlList.get(position).getTodayDeaths());
        holder.recovered_count.setText("Recovered :"+" " +coronaApiCurlList.get(position).getRecovered());
        holder.active_count.setText("Active :"+" " +coronaApiCurlList.get(position).getActive());
        holder.critical_count.setText("Critical :"+" " +coronaApiCurlList.get(position).getCritical());

        Glide.with(context).load(coronaApiCurlList.get(position).getCountryInfo().getFlag()).into(holder.flag_count);
    }

    @Override
    public int getItemCount() {
        return coronaApiCurlList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView cases_count, todayCases_count, deaths_count, todayDeaths_count, recovered_count, active_count, critical_count, country_count;
        private ImageView flag_count;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cases_count = (TextView) itemView.findViewById(R.id.casesCountFragment1);
            todayCases_count = (TextView) itemView.findViewById(R.id.todayCasesCountFragment1);
            deaths_count = (TextView) itemView.findViewById(R.id.deathsCountFragment1);
            todayDeaths_count = (TextView) itemView.findViewById(R.id.todayDeathsCountFragment1);
            recovered_count = (TextView) itemView.findViewById(R.id.recoveredCountFragment1);
            active_count = (TextView) itemView.findViewById(R.id.activeCountFragment1);
            critical_count = (TextView) itemView.findViewById(R.id.criticalCountFragment1);
            country_count = (TextView) itemView.findViewById(R.id.countryNameCount);
            flag_count = (ImageView) itemView.findViewById(R.id.flagCount);
        }
    }
}
