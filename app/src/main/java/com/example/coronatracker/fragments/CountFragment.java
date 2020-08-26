package com.example.coronatracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coronatracker.R;
import com.example.coronatracker.adapters.MyAdapterCount;
import com.example.coronatracker.apiCall.GetMethod;
import com.example.coronatracker.apiInterface.ApiInterface;
import com.example.coronatracker.jsonClass.CoronaApiCurl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountFragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private ProgressBar progressBar_count;
    private FloatingActionButton floatingActionButton;
    private List<CoronaApiCurl> coronaApiCurls;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.count_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_count);
        progressBar_count = (ProgressBar) view.findViewById(R.id.progressBar_count);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingButton);
        recyclerView.setHasFixedSize(true);
        progressBar_count.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        floatingActionButton.setOnClickListener(this);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    floatingActionButton.show();
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy<0 && !floatingActionButton.isShown()){
                    floatingActionButton.show();
                }

                else if (dy>0 && floatingActionButton.isShown()){

                    floatingActionButton.hide();
                }
            }
        });

        fetchAllCountry();

        return view;
    }

    public void fetchAllCountry(){

        final Call<List<CoronaApiCurl>> CALL_COUNT;

            ApiInterface apiInterfaceCount = GetMethod.getRetrofit().create(ApiInterface.class);

            CALL_COUNT = apiInterfaceCount.getCountryCases("country");


            CALL_COUNT.enqueue(new Callback<List<CoronaApiCurl>>() {
                @Override
                public void onResponse(Call<List<CoronaApiCurl>> call, Response<List<CoronaApiCurl>> response) {

                    if (!response.isSuccessful()) {
                        Toast.makeText(getActivity().getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                        CALL_COUNT.cancel();
                        progressBar_count.setVisibility(View.GONE);
                    } else {
                        coronaApiCurls = response.body();
                        progressBar_count.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(new MyAdapterCount(getActivity().getBaseContext(), coronaApiCurls));
                    }
                }

                @Override
                public void onFailure(Call<List<CoronaApiCurl>> call, Throwable t) {

                    Toast.makeText(getActivity().getBaseContext(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
