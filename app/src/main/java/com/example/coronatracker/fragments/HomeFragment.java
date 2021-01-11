package com.example.coronatracker.fragments;

import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.coronatracker.R;
import com.example.coronatracker.apiCall.GetMethod;
import com.example.coronatracker.apiInterface.ApiInterface;
import com.example.coronatracker.jsonClass.AllCorornaResult;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private TextView updated, cases, todayCases, deaths, todayDeaths, recovered, active, critical,
            casesPerOneMillion, deathsPerOneMillion, tests, testsPerOneMillion, affectedCountries;

    private RelativeLayout relativeLayout_main;
    private ProgressBar progressBar_home;
    private Button btn_refresh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);


        progressBar_home = (ProgressBar) view.findViewById(R.id.progressBarHome);
        relativeLayout_main = (RelativeLayout) view.findViewById(R.id.relativeLayoutMain_home);

        updated = (TextView) view.findViewById(R.id.updatedHome);
        cases = (TextView) view.findViewById(R.id.casesHome);
        todayCases = (TextView) view.findViewById(R.id.todayCasesHome);
        deaths = (TextView) view.findViewById(R.id.deathsHome);
        todayDeaths = (TextView) view.findViewById(R.id.todayDeathsHome);
        recovered = (TextView) view.findViewById(R.id.recoveredHome);
        active = (TextView) view.findViewById(R.id.activeHome);
        critical = (TextView) view.findViewById(R.id.criticalHome);
        casesPerOneMillion = (TextView) view.findViewById(R.id.casesPerOneMHome);
        deathsPerOneMillion = (TextView) view.findViewById(R.id.deathsPerOneMHome);
        tests = (TextView) view.findViewById(R.id.testsHome);
        testsPerOneMillion = (TextView) view.findViewById(R.id.testsPerOneMHome);
        affectedCountries = (TextView) view.findViewById(R.id.affectedCountHome);

        btn_refresh = (Button) view.findViewById(R.id.refresh_btn);
        btn_refresh.setOnClickListener(this);

        relativeLayout_main.setVisibility(View.GONE);
        progressBar_home.setVisibility(View.VISIBLE);

        fetchAllResult();
        return view;
    }

    public void fetchAllResult(){

        ApiInterface apiInterface = GetMethod.getRetrofit().create(ApiInterface.class);
       final Call<AllCorornaResult> CALL = apiInterface.getAllResult();
        CALL.enqueue(new Callback<AllCorornaResult>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<AllCorornaResult> call, @NonNull Response<AllCorornaResult> response) {

                if (!response.isSuccessful()){
                    Toast.makeText(Objects.requireNonNull(getActivity()).getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    progressBar_home.setVisibility(View.GONE);
                    CALL.cancel();
                }

                try {

                    AllCorornaResult allCorornaResult = response.body();

                    progressBar_home.setVisibility(View.GONE);
                    relativeLayout_main.setVisibility(View.VISIBLE);
                    btn_refresh.setVisibility(View.VISIBLE);

                    Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                    assert allCorornaResult != null;
                    cal.setTimeInMillis(allCorornaResult.getUpdated());
                    String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();

                    updated.setText("Updated : " +date);
//                    updated.setText("Updated(T) :"+" " +allCorornaResult.getUpdated());
                    cases.setText("Cases : "+allCorornaResult.getCases());
                    todayCases.setText("Today Cases : "+allCorornaResult.getTodayCases());
                    deaths.setText("Deaths :"+" " +allCorornaResult.getDeaths());
                    todayDeaths.setText("Today Deaths :"+" " +allCorornaResult.getTodayDeaths());
                    recovered.setText("Recovered :"+" " +allCorornaResult.getRecovered());
                    active.setText("Active :"+" " +allCorornaResult.getActive());
                    critical.setText("Critical :"+" " +allCorornaResult.getCritical());
                    casesPerOneMillion.setText("Cases Per 1 Million :"+" " +allCorornaResult.getCasesPerOneMillion());
                    deathsPerOneMillion.setText("Deaths Per 1 Million :"+" " +allCorornaResult.getDeathsPerOneMillion());
                    tests.setText("Tests :"+" " +allCorornaResult.getTests());
                    testsPerOneMillion.setText("Tests Per 1 Million :"+" " +allCorornaResult.getTestsPerOneMillion().toString());
                    affectedCountries.setText("Affected Countries :"+" " +allCorornaResult.getAffectedCountries());

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<AllCorornaResult> call, @NonNull Throwable t) {

                Toast.makeText(Objects.requireNonNull(getActivity()).getBaseContext(), Objects.requireNonNull(t.getCause()).toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {

        relativeLayout_main.setVisibility(View.GONE);
        progressBar_home.setVisibility(View.VISIBLE);
        btn_refresh.setVisibility(View.GONE);
        fetchAllResult();

    }
}
