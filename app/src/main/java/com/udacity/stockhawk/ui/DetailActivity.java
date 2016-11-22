package com.udacity.stockhawk.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.udacity.stockhawk.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String KEY = "data";
    @BindView(R.id.chart)
    LineChart lineChart;

    private HistoryValues[] data;

    /* Required empty constructor */
    public DetailActivity() {
    }

    public static Intent newIntent(Context context, List<HistoryValues> data) {
        final Intent intent = new Intent(context, DetailActivity.class);
        intent.putParcelableArrayListExtra(KEY, (ArrayList<? extends Parcelable>) data);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        List<HistoryValues> dataObjects = getIntent().getParcelableArrayListExtra(KEY);

        List<Entry> entries = new ArrayList<>();
        for (HistoryValues data : dataObjects) {
            // turn your data into Entry objects
            entries.add(new Entry(data.getTimeInMillis(), (float) data.getPriceInUSD()));
        }

        Collections.sort(entries, new EntryXComparator());
        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate(); // refresh

    }
}
