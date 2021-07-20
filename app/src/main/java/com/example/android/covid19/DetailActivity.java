package com.example.android.covid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class DetailActivity extends AppCompatActivity {

    private  int positionState;
    private TextView tvState,tvCases,tvTodayCases,tvTodayDeaths,tvTotalDeaths,tvRecovered,tvTodayRecovered,tvActive;
    SimpleArcLoader simpleArcLoader;
    ScrollView scrollView;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        positionState = intent.getIntExtra("position",0);
        getSupportActionBar().setTitle("Affected State");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        simpleArcLoader = findViewById(R.id.loader1);
        scrollView = findViewById(R.id.scrollStats1);
        pieChart = findViewById(R.id.piechart1);

        tvState = findViewById(R.id.tvState);
        tvCases = findViewById(R.id.tvCases);
        tvTodayCases = findViewById(R.id.tvTodayCases);
        tvTodayDeaths = findViewById(R.id.tvTodayDeaths);
        tvTotalDeaths = findViewById(R.id.tvTotalDeaths);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvTodayRecovered = findViewById(R.id.tvTodayRecovered);
        tvActive = findViewById(R.id.tvActive);




        tvState.setText(Affected_Sates.stateModelsList.get(positionState).getState());
        tvCases.setText(Affected_Sates.stateModelsList.get(positionState).getCases());
        tvTodayCases.setText(Affected_Sates.stateModelsList.get(positionState).getTodayCases());
        tvTotalDeaths.setText(Affected_Sates.stateModelsList.get(positionState).getDeaths());
        tvTodayDeaths.setText(Affected_Sates.stateModelsList.get(positionState).getTodayDeaths());
        tvRecovered.setText(Affected_Sates.stateModelsList.get(positionState).getRecovered());
        tvTodayRecovered.setText(Affected_Sates.stateModelsList.get(positionState).getNewRecovered());
        tvActive.setText(Affected_Sates.stateModelsList.get(positionState).getActive());

        //pie chart
        pieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(Affected_Sates.stateModelsList.get(positionState).getCases()), Color.parseColor("#FFA726")));
        pieChart.addPieSlice(new PieModel("Recovered",Integer.parseInt(Affected_Sates.stateModelsList.get(positionState).getRecovered()), Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(Affected_Sates.stateModelsList.get(positionState).getDeaths()), Color.parseColor("#EF5350")));
        pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(Affected_Sates.stateModelsList.get(positionState).getActive()), Color.parseColor("#29B6F6")));
        pieChart.startAnimation();

        simpleArcLoader.stop();
        simpleArcLoader.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);


    }

    //fxn continuing fot getsupportactionbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}