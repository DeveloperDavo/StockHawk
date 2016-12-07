package com.udacity.stockhawk.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.udacity.stockhawk.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String HISTORY_VALUES_LIST = "data";
    public static final String SYMBOL = "symbol";
    public static final int TEXT_SIZE = 10;

    @BindView(R.id.chart)
    LineChart lineChart;

    /* Required empty constructor */
    public DetailActivity() {
    }

    public static Intent newIntent(Context context, List<HistoryValues> data, String symbol) {
        final Intent intent = new Intent(context, DetailActivity.class);
        intent.putParcelableArrayListExtra(HISTORY_VALUES_LIST, (ArrayList<? extends Parcelable>) data);
        intent.putExtra(SYMBOL, symbol);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        final Bundle extras = getIntent().getExtras();

        setUpActionBar();

        setAxesProperties();

        final LineDataSet dataSet = addEntriesToDataSet(extras);

        setDataSetProperties(dataSet);

        setLineChartData(dataSet);

    }

    private void setUpActionBar() {
        final ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(false);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setAxesProperties() {
        setLeftAxisProperties();
        setXAxisProperties();
        setRightAxisProperties();
    }

    private void setLeftAxisProperties() {
        final YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setDrawLabels(false);
    }

    private void setXAxisProperties() {
        final XAxis xAxis = lineChart.getXAxis();
        xAxis.setTextSize(TEXT_SIZE);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new TimeFormatter());
    }

    private void setRightAxisProperties() {
        final YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setTextSize(TEXT_SIZE);
        rightAxis.setValueFormatter(new PriceFormatter());
    }

    @NonNull
    private List<Entry> getEntries(Bundle extras) {
        List<HistoryValues> dataObjects = extras.getParcelableArrayList(HISTORY_VALUES_LIST);
        assert dataObjects != null;
        List<Entry> entries = new ArrayList<>();
        for (HistoryValues data : dataObjects) {
            // turn your data into Entry objects
            entries.add(new Entry(data.getTimeInMillis(), (float) data.getPriceInUSD()));
        }

        Collections.sort(entries, new EntryXComparator());
        return entries;
    }

    @NonNull
    private LineDataSet addEntriesToDataSet(Bundle extras) {
        return new LineDataSet(getEntries(extras), extras.getString(SYMBOL));
    }

    private void setDataSetProperties(LineDataSet dataSet) {
        dataSet.setDrawCircles(false);
        dataSet.setDrawFilled(true);
        dataSet.setDrawValues(false);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
    }

    private void setLineChartData(LineDataSet dataSet) {
        LineData lineData = new LineData(dataSet);
        lineChart.getDescription().setEnabled(false);
        lineChart.setAutoScaleMinMaxEnabled(true);
        lineChart.setData(lineData);
        lineChart.invalidate(); // refresh
    }

    public class PriceFormatter implements IAxisValueFormatter {

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            DecimalFormat mFormat = new DecimalFormat("0.00");
            return mFormat.format(value);
        }
    }

    public class TimeFormatter implements IAxisValueFormatter {

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis((long) value);
            return new SimpleDateFormat("dd/MM/yy").format(calendar.getTime());
        }
    }
}
