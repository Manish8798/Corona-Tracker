package com.example.coronatracker.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.coronatracker.R;
import com.example.coronatracker.apiCall.GetMethod;
import com.example.coronatracker.apiInterface.ApiInterface;
import com.example.coronatracker.jsonClass.SpecificCountryCoronaAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment implements View.OnClickListener {
    private TextView cases_search, todayCases_search, deaths_search, todayDeaths_search, recovered_search, active_search, country_search, critical_search;
    private ProgressBar progressBar_search;
    private EditText search;
    private RelativeLayout relativeLayout_search;
    private ImageView flag_search;
    private SpecificCountryCoronaAPI sfc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);

        cases_search = (TextView) view.findViewById(R.id.casesSearchFragment1);
        todayCases_search = (TextView) view.findViewById(R.id.todayCasesSearchFragment1);
        deaths_search = (TextView) view.findViewById(R.id.deathsSearchFragment1);
        todayDeaths_search = (TextView) view.findViewById(R.id.todayDeathsSearchFragment1);
        recovered_search = (TextView) view.findViewById(R.id.recoveredSearchFragment1);
        active_search = (TextView) view.findViewById(R.id.activeSearchFragment1);
        country_search = (TextView) view.findViewById(R.id.countryName);
        critical_search = (TextView) view.findViewById(R.id.criticalSearchFragment1);


        search = (EditText) view.findViewById(R.id.search);
        flag_search = (ImageView) view.findViewById(R.id.flag);

        progressBar_search = (ProgressBar) view.findViewById(R.id.progressBarSearch);
        relativeLayout_search = (RelativeLayout) view.findViewById(R.id.relativeLayoutSearchFragment);

        search.setOnClickListener(this);
        flag_search.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        String data = search.getText().toString();

        switch (v.getId()) {

            case R.id.search:
            if (data.isEmpty() || data.length() < 2) {
                Toast.makeText(getActivity().getBaseContext(), "Enter At least 2 Characters", Toast.LENGTH_SHORT).show();
            } else {
                relativeLayout_search.setVisibility(View.GONE);
                progressBar_search.setVisibility(View.VISIBLE);
                fetchSearchResult(data);
            }
            break;

            case R.id.flag:
                Toast.makeText(getActivity().getBaseContext(), sfc.getCountry(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void fetchSearchResult(final String COUNTRY){

        ApiInterface apiInterface = GetMethod.getRetrofit().create(ApiInterface.class);
        final Call<SpecificCountryCoronaAPI> CALL_SEARCH = apiInterface.getSpecificCountryCase(COUNTRY);

        CALL_SEARCH.enqueue(new Callback<SpecificCountryCoronaAPI>() {
            @Override
            public void onResponse(Call<SpecificCountryCoronaAPI> call, Response<SpecificCountryCoronaAPI> response) {

                if (!response.isSuccessful()){
                    Toast.makeText(getActivity().getBaseContext(), "Error(Check the name)", Toast.LENGTH_SHORT).show();
                    CALL_SEARCH.cancel();
                    progressBar_search.setVisibility(View.GONE);
                }

                else {
                     sfc = response.body();

                    progressBar_search.setVisibility(View.GONE);
                    relativeLayout_search.setVisibility(View.VISIBLE);

                    country_search.setText(sfc.getCountry());
                    cases_search.setText("Cases :"+" " +sfc.getCases());
                    todayCases_search.setText("Today Cases :"+" " +sfc.getTodayCases());
                    deaths_search.setText("Deaths :"+" " +sfc.getDeaths());
                    todayDeaths_search.setText("Today Deaths :"+" " +sfc.getTodayDeaths());
                    recovered_search.setText("Recovered :"+" " +sfc.getRecovered());
                    active_search.setText("Active :"+" " +sfc.getActive());
                    critical_search.setText("Critical :"+" " +sfc.getCritical());

                    Glide.with(getActivity().getBaseContext()).load(sfc.getCountryInfo().getFlag()).into(flag_search);
                }
            }

            @Override
            public void onFailure(Call<SpecificCountryCoronaAPI> call, Throwable t) {

                Toast.makeText(getActivity().getBaseContext(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
