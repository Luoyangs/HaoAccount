package com.haoxue.haoaccount.act.frag;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.handmark.pulltorefresh.PullToRefreshBase;
import com.handmark.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.PullToRefreshScrollView;
import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.act.DayBalanceAct;
import com.haoxue.haoaccount.base.ShareDataHelper;
import com.haoxue.haoaccount.base.Constant.MSG;
import com.haoxue.haoaccount.util.ToastUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

/**
 * 说明:主页主页面
 * 作者:Luoyangs
 * 时间:2015-9-30
 */
@SuppressLint("ValidFragment")
public class MainFragment extends Fragment{
	
	private PullToRefreshScrollView mPullRefreshScrollView;

	private LineChart income_chart;//收入
	private LineChart outcome_chart;//支出
	private LineChart prepay_chart;//预算
	
	private RelativeLayout dayInfo;
	private RelativeLayout weekInfo;
	private RelativeLayout monthInfo;
	
	private List<Float> incomelist;//收入
	private List<Float> outcomelist;//支出
	private List<Float> prepaylist;//预算
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			//停止刷新
			mPullRefreshScrollView.onRefreshComplete();
			
			switch (msg.what) {
			case MSG.NO_NETWORK:
				ShareDataHelper.getInstance(getActivity()).savePullToRefreshTime("fresh", new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
				ShareDataHelper.getInstance(getActivity()).savePullToRefreshTime("load", new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
				break;
			case MSG.FRESH_OK:
				ShareDataHelper.getInstance(getActivity()).savePullToRefreshTime("fresh", new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
				ToastUtil.showShort(getActivity(), "刷新完成");
				break;
			case MSG.FRESH_ERROR:
				ShareDataHelper.getInstance(getActivity()).savePullToRefreshTime("fresh", new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
				break;
			case MSG.LOAD_OK:
				ShareDataHelper.getInstance(getActivity()).savePullToRefreshTime("load", new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
				ToastUtil.showShort(getActivity(), "加载完成");
				break;
			case MSG.LOAD_ERROR:
				ShareDataHelper.getInstance(getActivity()).savePullToRefreshTime("load", new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
				break;
			}
		}
	};
	
	public MainFragment(){
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main_layout,container,false);
		mPullRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.pull_refresh_scrollview);
		mPullRefreshScrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				if (mPullRefreshScrollView.isHeaderShown()) {
					mPullRefreshScrollView.getLoadingLayoutProxy(true, false).setPullLabel(getString(R.string.pull_to_refresh_pull_label));
					mPullRefreshScrollView.getLoadingLayoutProxy(true, false).setRefreshingLabel(getString(R.string.pull_to_refresh_release_label)); 
					mPullRefreshScrollView.getLoadingLayoutProxy(true, false).setReleaseLabel(getString(R.string.pull_to_refresh_refreshing_label));
					mPullRefreshScrollView.getLoadingLayoutProxy(true, false).setLastUpdatedLabel("上次刷新时间："+ShareDataHelper.getInstance(getActivity()).getPullToRefreshTime("fresh"));
					refreashData();
				} else if (mPullRefreshScrollView.isFooterShown()) {
					mPullRefreshScrollView.getLoadingLayoutProxy(false, true).setPullLabel(getString(R.string.pull_to_refresh_from_bottom_pull_label));
					mPullRefreshScrollView.getLoadingLayoutProxy(false, true).setRefreshingLabel(getString(R.string.pull_to_refresh_from_bottom_refreshing_label)); 
					mPullRefreshScrollView.getLoadingLayoutProxy(false, true).setReleaseLabel(getString(R.string.pull_to_refresh_from_bottom_release_label)); 
					mPullRefreshScrollView.getLoadingLayoutProxy(false, true).setLastUpdatedLabel("上次加载时间："+ShareDataHelper.getInstance(getActivity()).getPullToRefreshTime("load"));
					loadMore();
				}
			}
		});
		income_chart = (LineChart) view.findViewById(R.id.income_chat);
		outcome_chart = (LineChart) view.findViewById(R.id.outcome_chat);
		prepay_chart = (LineChart) view.findViewById(R.id.prepay_chat);
		dayInfo = (RelativeLayout) view.findViewById(R.id.day_info);
		weekInfo = (RelativeLayout) view.findViewById(R.id.week_info);
		monthInfo = (RelativeLayout) view.findViewById(R.id.month_info);
		dayInfo.setOnClickListener(new MyClickListener());
		weekInfo.setOnClickListener(new MyClickListener());
		monthInfo.setOnClickListener(new MyClickListener());
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
	
	private void refreashData(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message msg = new Message();
				msg.what = MSG.FRESH_OK;
				handler.sendMessage(msg);
			}
		}).start();
	}
	
	private void loadMore(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message msg = new Message();
				msg.what = MSG.LOAD_OK;
				handler.sendMessage(msg);
			}
		}).start();
	}
	
	private class MyClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.day_info:
				startActivity(new Intent(getActivity(),DayBalanceAct.class));
				break;
			case R.id.week_info:
				startActivity(new Intent(getActivity(),DayBalanceAct.class));
				break;
			case R.id.month_info:
				startActivity(new Intent(getActivity(),DayBalanceAct.class));
				break;
			}
		}
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
