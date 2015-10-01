package com.haoxue.haoaccount.act.frag;

import java.util.ArrayList;
import java.util.List;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.haoxue.haoaccount.R;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 说明:主页主页面
 * 作者:Luoyangs
 * 时间:2015-9-30
 */
@SuppressLint("ValidFragment")
public class MainFragment extends Fragment{

	private LineChart income_chart;//收入
	private LineChart outcome_chart;//支出
	private LineChart prepay_chart;//预算
	
	private List<Float> incomelist;//收入
	private List<Float> outcomelist;//支出
	private List<Float> prepaylist;//预算
	
	public MainFragment(){
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main_layout,container,false);
		income_chart = (LineChart) view.findViewById(R.id.income_chat);
		outcome_chart = (LineChart) view.findViewById(R.id.outcome_chat);
		prepay_chart = (LineChart) view.findViewById(R.id.prepay_chat);
		//设置数据
		setData(income_chart,7, 30);
		setData(outcome_chart,7, 30);
		setData(prepay_chart,7, 30);
		//设置坐标轴
		initChart(income_chart,0f,200f);
		initChart(outcome_chart,0f,240f);
		initChart(prepay_chart,0f,360f);
		return view;
	}
	
	private void initChart(LineChart chart, float minValue,float maxValue){
		chart.setDescription("");
		chart.animateX(2500);
		chart.getAxisRight().setEnabled(false);
		chart.setScaleEnabled(false);
		chart.setTouchEnabled(false);
		chart.setDrawGridBackground(false);
		XAxis xAxis2 = chart.getXAxis();
		xAxis2.setPosition(XAxisPosition.BOTTOM);
		YAxis leftAxis2 = chart.getAxisLeft();
        leftAxis2.removeAllLimitLines(); 
        leftAxis2.setAxisMaxValue(maxValue);
        leftAxis2.setAxisMinValue(minValue);
        leftAxis2.setStartAtZero(false);
        leftAxis2.enableGridDashedLine(10f, 10f, 0f);
	}
	
	/** */
	private void setData(LineChart chart,int count, float range) {
		ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add((i) + "");
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {

            float mult = (range + 1);
            float val = (float) (Math.random() * mult) + 3;// + (float)
                                                           // ((mult *
                                                           // 0.1) / 10);
            yVals.add(new Entry(val, i));
        }
        
        LineDataSet set = null;
        if (chart == income_chart) {
        	set = new LineDataSet(yVals, "最近7次收入表");
        	set.setColor(Color.GREEN);
        	set.setCircleColor(Color.GREEN);
		}else if (chart == outcome_chart) {
			set = new LineDataSet(yVals, "最近7次支出表");
			set.setColor(Color.RED);
			set.setCircleColor(Color.RED);
		}else{
			set = new LineDataSet(yVals, "最近7次预算表");
			set.setColor(Color.YELLOW);
			set.setCircleColor(Color.YELLOW);
		}
        set.enableDashedLine(10f, 5f, 0f);
        set.setLineWidth(1f);
        set.setCircleSize(3f);
        set.setDrawCircleHole(false);
        set.setValueTextSize(9f);
        set.setFillAlpha(65);
        set.setFillColor(Color.BLACK);

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set); // add the datasets

        LineData data = new LineData(xVals, dataSets);

        chart.setData(data);
	}
}
