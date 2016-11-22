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
import com.udacity.stockhawk.R;

import java.util.ArrayList;
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

    public static Intent newIntent(Context context, HistoryValues[] data) {
        final Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(KEY, data);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Parcelable[] dataObjects = getIntent().getParcelableArrayExtra(KEY);

        List<Entry> entries = new ArrayList<>();

        for (Parcelable data : dataObjects) {

            // turn your data into Entry objects
            if (data instanceof HistoryValues) {
                entries.add(new Entry(((HistoryValues) data).getTimeInMillis(), (float) ((HistoryValues) data).getPriceInUSD()));
            }
        }

        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate(); // refresh

    }
}
