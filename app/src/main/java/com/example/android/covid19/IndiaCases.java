package com.example.android.covid19;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import android.widget.TextView;

import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import android.widget.ScrollView;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IndiaCases extends AppCompatActivity {

    private TextView tvCases,tvRecovered,tvPreviousDayTests,tvActive,tvTodayCases,tvTotalDeaths,tvTodayDeaths,tvAffectedCountries, tvTodayRecovered;
    String USGS_REQUEST_URL  = "https://api.apify.com/v2/key-value-stores/toDWvRj1JpTXiM8FF/records/LATEST?disableRedirect=true";
    SimpleArcLoader simpleArcLoader;
    ScrollView scrollView;
    PieChart pieChart;
    public ArrayList<String> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_cases);

        tvCases = findViewById(R.id.tvCases);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvPreviousDayTests = findViewById(R.id.tvPreviousDayTests);
        tvActive = findViewById(R.id.tvActive);
        tvTodayCases = findViewById(R.id.tvTodayCases);
        tvTotalDeaths = findViewById(R.id.tvTotalDeaths);
        tvTodayDeaths = findViewById(R.id.tvTodayDeaths);
       //tvAffectedCountries = findViewById(R.id.tvAffectedCountries);
        tvTodayRecovered = findViewById(R.id.tvTodayRecovered);

        simpleArcLoader = findViewById(R.id.loader);
        scrollView = findViewById(R.id.scrollStats);
        pieChart = findViewById(R.id.piechart);

        // ArrayList<String> result = new ArrayList<String>;


        final ExecutorService executorservice = Executors.newSingleThreadExecutor();
        executorservice.execute(new Runnable() {
            @Override
            public void run() {
                // doInBackground
                result = QueryUtils.fetchIndiaCasesData(USGS_REQUEST_URL);

                //postExecute
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvCases.setText(result.get(0));
                        tvRecovered.setText(result.get(1));
                        tvPreviousDayTests.setText(result.get(2));
                        tvActive.setText(result.get(3));
                        tvTodayCases.setText(result.get(4));
                        tvTotalDeaths.setText(result.get(5));
                        tvTodayDeaths.setText(result.get(6));
                      //  tvAffectedCountries.setText(result.get(7));
                        tvTodayRecovered.setText(result.get(7));

                        //pie chart
                        pieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(result.get(0)), Color.parseColor("#FFA726")));
                        pieChart.addPieSlice(new PieModel("Recovered",Integer.parseInt(result.get(1)), Color.parseColor("#66BB6A")));
                        pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(result.get(6)), Color.parseColor("#EF5350")));
                        pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(result.get(3)), Color.parseColor("#29B6F6")));
                        pieChart.startAnimation();

                        simpleArcLoader.stop();
                        simpleArcLoader.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);

                    }
                });
            }
        });


    }

    public void goTrackStates(View View){
        Intent intent = new Intent(this,Affected_Sates.class);
        startActivity(intent);
    }



}