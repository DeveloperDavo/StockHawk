package com.udacity.stockhawk.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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

        final YAxis axisLeft = lineChart.getAxisLeft();
        final XAxis xAxis = lineChart.getXAxis();
        final YAxis axisRight = lineChart.getAxisRight();

        axisLeft.setDrawLabels(false);

        xAxis.setTextSize(14);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new TimeFormatter());

        axisRight.setTextSize(14);
        axisRight.setValueFormatter(new PriceFormatter());

        List<HistoryValues> dataObjects = extras.getParcelableArrayList(HISTORY_VALUES_LIST);

        List<Entry> entries = new ArrayList<>();
        for (HistoryValues data : dataObjects) {
            // turn your data into Entry objects
            entries.add(new Entry(data.getTimeInMillis(), (float) data.getPriceInUSD()));
        }

        Collections.sort(entries, new EntryXComparator());
        LineDataSet dataSet = new LineDataSet(entries, extras.getString(SYMBOL)); // add entries to dataset
        dataSet.setDrawValues(false);

        LineData lineData = new LineData(dataSet);
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
