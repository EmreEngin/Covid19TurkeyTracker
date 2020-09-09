package com.engin.covid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import com.engin.covid19.DownloaData.ApiUtil;
import com.engin.covid19.DownloaData.CovidInterface;
import com.engin.covid19.DownloaData.Covidata;
import com.engin.covid19.quizes.QuizActivity1;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.github.mikephil.charting.utils.ColorTemplate.*;

public class MainActivity extends AppCompatActivity {
    private CovidInterface covidInterface;
    Covidata covidata;
    CardView cardView;

    TreeMap<Date,Integer> covidDailyInfected;
    TreeMap<Date,Integer> covidDailyRecovered;
    TreeMap<Date,Integer> covidDailyDeceased;
    LineChart lineChart;
    BarChart barChart;
    TextView dailyInfectedTv,dailyRecoveredTv,dailyDeceasedTv,totalInfectedTv,totalRecoveredTv,totalDeceasedTv,latestUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardView = findViewById(R.id.cardViewQuiz);
        covidInterface = ApiUtil.getCovidInterface();
        lineChart = findViewById(R.id.line_chart);
        barChart = findViewById(R.id.bar_chart);
        dailyInfectedTv = findViewById(R.id.dailyInfected);
        dailyRecoveredTv = findViewById(R.id.dailyRecovered);
        dailyDeceasedTv = findViewById(R.id.dailyDeceased);
        totalInfectedTv = findViewById(R.id.vaka_sayisi);
        totalRecoveredTv = findViewById(R.id.iyilesen_sayisi);
        totalDeceasedTv = findViewById(R.id.olum_sayisi);
        latestUpdate = findViewById(R.id.latest_update);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, QuizActivity1.class));
            }
        });
        showProgressDialog();
        getData();
    }
    public void getData(){

        covidInterface.veriAl().enqueue(new Callback<List<Covidata>>() {
            @Override
            public void onResponse(Call<List<Covidata>> call, Response<List<Covidata>> response) {

                if (response.isSuccessful()) {

                    List<Covidata> covidataList = response.body();
                    List<Covidata> upToDate = covidataList.subList(10, 23);
                    dailyDeceasedTv.setText("+"+ covidataList.get(0).getGunlukVefat().toString());
                    dailyInfectedTv.setText("+"+ covidataList.get(0).getGunlukVaka().toString());
                    dailyRecoveredTv.setText("+" +  covidataList.get(0).getGunlukIyilesen().toString());
                    totalDeceasedTv.setText( covidataList.get(0).getToplamVefat());
                    totalInfectedTv.setText( covidataList.get(0).getToplamVaka());
                    totalRecoveredTv.setText( covidataList.get(0).getToplamIyilesen());
                    latestUpdate.setText("Son Güncelleme : " +covidataList.get(0).getTarih());
                    covidDailyInfected = new TreeMap<>();
                    covidDailyRecovered = new TreeMap<>();
                    covidDailyDeceased = new TreeMap<>();
                    for (Covidata c : upToDate) {
                        String draftDate = c.getTarih();
                        Date date = cleanDate(draftDate);
                        covidDailyInfected.put(date, c.getGunlukVaka());
                        covidDailyRecovered.put(date, c.getGunlukIyilesen());
                        covidDailyDeceased.put(date, c.getGunlukVefat());
                    }

                    System.out.println(covidDailyDeceased);
                    System.out.println(covidDailyInfected);
                    System.out.println(covidDailyRecovered);
                    setLineChart(covidDailyInfected,covidDailyRecovered,covidDailyDeceased);
                }
            }

            @Override
            public void onFailure(Call<List<Covidata>> call, Throwable t) {

            }
        });
    }
    public Date cleanDate(String s){

        int gun =  Integer.parseInt(s.substring(0,2));
        int ay = Integer.parseInt(s.substring(3,5))-1;
        int yil = Integer.parseInt(s.substring(6,10));
        return new Date(yil,ay,gun);
    }
    @SuppressLint("ResourceAsColor")
    public void setLineChart(TreeMap<Date,Integer> infected,TreeMap<Date,Integer>  recovered,TreeMap<Date,Integer>  deceased){
        List<Date> dates = new ArrayList<Date>(infected.keySet());
        List<Integer> days = new ArrayList<>();
        int yeniAy = 0;
        for (int i= 0;i<dates.size();i++){
                days.add(dates.get(i).getDate());
        }
        System.out.println(days);
        List<Integer> dailyInfected = new ArrayList<>(infected.values());
        List<Integer> dailyRecovered = new ArrayList<>(recovered.values());
        List<Integer> dailyDeceased = new ArrayList<>(deceased.values());
        ArrayList<Entry> dataValues1 = new ArrayList<>();
        ArrayList<Entry> dataValues2 = new ArrayList<>();
        for (int i = 0; i<days.size();i++){
            dataValues1.add(new Entry(days.get(i),dailyInfected.get(i)));
            dataValues2.add(new Entry(days.get(i),dailyRecovered.get(i)));
        }
        //------------ LineChart-------
        {
            LineDataSet lineDataSet = new LineDataSet(dataValues1, "Günlük Vaka Sayisi");
            LineDataSet lineDataSet2 = new LineDataSet(dataValues2, "Günlük İyleşen Sayisi");
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(lineDataSet);
            dataSets.add(lineDataSet2);
            LineData data = new LineData(dataSets);
            lineChart.setData(data);

            lineChart.setScaleEnabled(true);
            Description description = new Description();
            description.setText("Son 15 Günlük Veriler");
            description.setTextSize(12f);
            description.setTextColor(Color.BLACK);
            lineChart.setDescription(description);
            lineChart.setKeepPositionOnRotation(true);
            lineDataSet.setCircleRadius(5.1f);
            lineDataSet.setColor(getColor(R.color.Cyan));
            lineDataSet.setDrawValues(false);
            lineDataSet.setLineWidth(4f);
            lineDataSet2.setColor(getColor(R.color.LightGreen));
            lineDataSet2.setCircleRadius(4.2f);
            lineDataSet2.setCircleColor(getColor(R.color.LightGreen));
            lineDataSet2.setDrawValues(false);
            lineDataSet2.setLineWidth(4f);
            YAxis leftAxis = lineChart.getAxisLeft();
            YAxis rigtAxis = lineChart.getAxisRight();
            rigtAxis.setEnabled(false);
            leftAxis.setDrawAxisLine(false);
            leftAxis.setDrawLabels(true);
            leftAxis.setDrawGridLines(true);
            leftAxis.setGranularity(100f);
            leftAxis.setGranularityEnabled(true);
            leftAxis.setSpaceBottom(250f);
            leftAxis.setAxisLineWidth(2.2f);
            leftAxis.setAxisLineColor(Color.BLACK);
            XAxis xAxis = lineChart.getXAxis();
            xAxis.setAxisMinimum(18f);
            xAxis.setLabelCount(13, true);
            xAxis.setDrawGridLines(false);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setAxisLineWidth(2.2f);
            xAxis.setDrawAxisLine(true);
            xAxis.setTextSize(10f);
            xAxis.setAxisLineColor(Color.BLACK);
            int[] colorClassArray = {getColor(R.color.LightBlue), getColor(R.color.LightGreen)};
            String[] legendName = {"Vaka", "İyleşen Hasta"};
            Legend legend = lineChart.getLegend();
            LegendEntry[] legendEntries = new LegendEntry[2];
            for (int i = 0; i < legendEntries.length; i++) {
                LegendEntry entry = new LegendEntry();
                entry.formColor = colorClassArray[i];
                entry.formSize = 10f;
                entry.label = String.valueOf(legendName[i]);
                legendEntries[i] = entry;
            }
            legend.setCustom(legendEntries);
            legend.setXEntrySpace(0f);
            legend.setForm(Legend.LegendForm.CIRCLE);
            legend.setTextSize(11f);
            IMarker iMarker = new CustomMarkerView(MainActivity.this,R.layout.popup_marker);
            lineChart.setMarker(iMarker);
            lineChart.fitScreen();
            lineChart.animateX(1200, Easing.EaseInOutExpo);
            lineChart.invalidate();
        }
        //-------------BarChart---------
        {
            ArrayList<BarEntry> dataValues3 = new ArrayList<>();
            for (int i = 0; i<days.size();i++){
                dataValues3.add(new BarEntry(days.get(i),dailyDeceased.get(i)));
            }
            BarDataSet barDataSet = new BarDataSet(dataValues3,"Günlük Ölüm Sayısı");
            BarData barData = new BarData();
            barData.addDataSet(barDataSet);
            barChart.setData(barData);
            barChart.setScaleEnabled(false);
            barDataSet.setColor(getColor(R.color.Red));
            barDataSet.setBarBorderColor(Color.BLACK);
            barDataSet.setBarBorderWidth(2.5f);
            barDataSet.setDrawValues(false);
            YAxis leftAxis = barChart.getAxisLeft();
            YAxis rigtAxis = barChart.getAxisRight();
            rigtAxis.setEnabled(false);
            leftAxis.setDrawGridLines(false);
            leftAxis.setAxisMinimum(0f);
            leftAxis.setDrawGridLines(true);
            leftAxis.setAxisMaximum(95f);
            leftAxis.setTextColor(Color.RED);
            leftAxis.setAxisLineColor(Color.BLACK);
            leftAxis.setAxisLineWidth(1.5f);
            XAxis xAxis = barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextSize(10f);
            xAxis.setTextColor(Color.RED);
            xAxis.setDrawGridLines(false);
            xAxis.setAxisMinimum(17.5f);
            xAxis.setLabelCount(13,false);
            xAxis.setAxisLineColor(Color.BLACK);
            xAxis.setAxisLineWidth(2.2f);
            IMarker iMarker = new CustomMarkerView(MainActivity.this,R.layout.popup_marker);
            barChart.setMarker(iMarker);
            barChart.fitScreen();
            barChart.animateX(1200);
            Legend legendBar = barChart.getLegend();
            legendBar.setEnabled(false);
        }
    }
    private void showProgressDialog(){

        LottieDialogFragment lottieDialogFragment=new LottieDialogFragment().newInstance();
        lottieDialogFragment.show(getSupportFragmentManager(),"");
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                lottieDialogFragment.dismiss();
            }
        },7000);
    }
    /*public void getAllData(){
        covidInterface.topluVeriAl().enqueue(new Callback<List<Covidata>>() {
            @Override
            public void onResponse(Call<List<Covidata>> call, Response<List<Covidata>> response) {
                if(response.isSuccessful()){
                    List<Covidata> covidataList = response.body();
                    List<Covidata>  upToDate =   covidataList.subList(covidataList.size()-42,covidataList.size()-28);
                    covidDailyInfected = new TreeMap<>();
                    covidDailyRecovered = new TreeMap<>();
                    covidDailyDeceased = new TreeMap<>();
                    for(Covidata c : upToDate){
                        String draftDate = c.getLastUpdatedAtSource().substring(0,10);
                        Date date = cleanDate(draftDate);
                        covidDailyInfected.put(date,c.getDailyInfected());
                        covidDailyRecovered.put(date,c.getDailyRecovered());
                        covidDailyDeceased.put(date,c.getDailyDeceased());
                    }


                    System.out.println(covidDailyDeceased);
                    System.out.println(covidDailyInfected);
                    System.out.println(covidDailyRecovered);
                    setLineChart(covidDailyInfected,covidDailyRecovered,covidDailyDeceased);
                }
            }

            @Override
            public void onFailure(Call<List<Covidata>> call, Throwable t) {

            }
        });
    }*/
/*covidata = response.body();
                    dailyDeceasedTv.setText("+"+covidata.getDailyDeceased().toString());
                    dailyInfectedTv.setText("+"+covidata.getDailyInfected().toString());
                    dailyRecoveredTv.setText("+" + covidata.getDailyRecovered().toString());
                    totalDeceasedTv.setText(covidata.getDeceased().toString());
                    totalInfectedTv.setText(covidata.getInfected().toString());
                    totalRecoveredTv.setText(covidata.getRecovered().toString());
                    int gun =  Integer.parseInt( covidata.getlastUpdateresultpify().substring(8,10));
                    int ay = Integer.parseInt(covidata.getlastUpdateresultpify().substring(5,7));
                    int yil = Integer.parseInt(covidata.getlastUpdateresultpify().substring(0,4));
                    latestUpdate.setText("Son Güncelleme : " + gun +"."+ ay +"."+yil);*/
}