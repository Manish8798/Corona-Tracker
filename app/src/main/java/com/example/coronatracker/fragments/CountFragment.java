package com.example.coronatracker.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coronatracker.R;
import com.example.coronatracker.adapters.MyAdapterCount;
import com.example.coronatracker.apiCall.GetMethod;
import com.example.coronatracker.apiInterface.ApiInterface;
import com.example.coronatracker.jsonClass.CoronaApiCurl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountFragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private ProgressBar progressBar_count;
    private FloatingActionButton floatingActionButton;
    private List<CoronaApiCurl> coronaApiCurls = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.count_fragment, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_count);
        progressBar_count = view.findViewById(R.id.progressBar_count);
        floatingActionButton = view.findViewById(R.id.floatingButton);
        recyclerView.setHasFixedSize(true);
        progressBar_count.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(Objects.requireNonNull(getActivity()).getBaseContext()));
        floatingActionButton.setOnClickListener(this);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    floatingActionButton.show();
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy < 0 && !floatingActionButton.isShown()) {
                    floatingActionButton.show();
                } else if (dy > 0 && floatingActionButton.isShown()) {

                    floatingActionButton.hide();
                }
            }
        });
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                fetchAllCountry();
            }
        });

        return view;
    }

    public void fetchAllCountry() {

        final Call<List<CoronaApiCurl>> CALL_COUNT;

        ApiInterface apiInterfaceCount = GetMethod.getRetrofit().create(ApiInterface.class);

        CALL_COUNT = apiInterfaceCount.getCountryCases("country");


        CALL_COUNT.enqueue(new Callback<List<CoronaApiCurl>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<List<CoronaApiCurl>> call, @NonNull Response<List<CoronaApiCurl>> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(Objects.requireNonNull(getActivity()).getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    CALL_COUNT.cancel();
                    progressBar_count.setVisibility(View.GONE);
                }
                try {

                    coronaApiCurls = response.body();

                    progressBar_count.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(new MyAdapterCount(Objects.requireNonNull(getActivity()).getBaseContext(), coronaApiCurls));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<List<CoronaApiCurl>> call, @NonNull Throwable t) {

                Toast.makeText(Objects.requireNonNull(getActivity()).getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {

        progressBar_count.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        fetchAllCountry();
    }
}
