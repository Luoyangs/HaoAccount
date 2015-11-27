package com.haoxue.haoaccount.act.frag;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.adapter.WeathAdapter;
import com.haoxue.haoaccount.base.Constant;
import com.haoxue.haoaccount.base.Constant.MSG;
import com.haoxue.haoaccount.base.ShareDataHelper;
import com.haoxue.haoaccount.bean.ResponseBean;
import com.haoxue.haoaccount.bean.WeatherSubBean;
import com.haoxue.haoaccount.util.NetWorkUtil;
import com.haoxue.haoaccount.util.ToastUtil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 说明:主页主页面
 * 作者:Luoyangs
 * 时间:2015-9-30
 */
@SuppressLint({ "ValidFragment", "SimpleDateFormat" })
public class MainFragment extends Fragment{
	
	private GridView wetherlist;
	private TextView local;
	private TextView time;
	private ImageView weathBg;
	private String normalCity = "";
	private LocationClient mLocClient = null;
	private MyLocationListenner myListener = null;
	private boolean isNeedFresh;
	private static ResponseBean response; //存储的天气
	private WeathAdapter adapter;//显示天气的适配器
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case MSG.NO_NETWORK:
					ToastUtil.showShort(getActivity(), "网络故障~");
					break;
				case MSG.LOAD_OK:
					//显示天气
					display();
					break;
				case MSG.LOAD_ERROR:
					//天气加载失败
					ToastUtil.showShort(getActivity(), "天气加载失败~");
					break;
				case MSG.FRESH_OK://加载天气
					mLocClient.stop();
					local.setText(normalCity);
					local.invalidate();
					loadWeather(normalCity);
					break;
			}
		}
	};
	
	public MainFragment(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main_layout,container,false);
		wetherlist = (GridView) view.findViewById(R.id.wetherlist);
		local = (TextView) view.findViewById(R.id.loal);
		time = (TextView) view.findViewById(R.id.timesp);
		weathBg = (ImageView) view.findViewById(R.id.iv);
		
		isNeedFresh = true;
		time.setText(new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
		// 初始化百度地图
		mLocClient = new LocationClient(getActivity().getApplication());
		myListener = new MyLocationListenner();
		mLocClient.registerLocationListener(myListener);
		initLocalWhere();
		mLocClient.start();
		//加载中心模块
		getFragmentManager().beginTransaction()
			.setCustomAnimations(R.anim.fade_in, R.anim.fade_out).add(R.id.main_frame, new MCFragment()).commit();
		return view;
	}
	
	//显示天气
	private void display(){
		// 读取天气信息
		String jsonString = ShareDataHelper.getInstance(getActivity()).getWeather(normalCity);
		if (jsonString != null && jsonString.length() > 0) {
			response = JSON.parseObject(jsonString, ResponseBean.class);
		} else {
			return;
		}
		List<WeatherSubBean> weathlist= response.getResults().get(0).getWeather_data();
		if (weathlist != null && weathlist.size() > 0) {
			String todayString = weathlist.get(0).getWeather().trim();
			adapter = new WeathAdapter(getActivity(), weathlist);
			wetherlist.setAdapter(adapter);
			//设置背景
			weathBg.setImageResource(getWeatherBg(todayString));
		}
	}
	
	//加载天气
	private void loadWeather(final String local){
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2000);// 注：异步线程中不能设置UI
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					if (!NetWorkUtil.isNetworkAvailable(getActivity())) {
						handler.sendEmptyMessage(MSG.NO_NETWORK);
					} else {
						String jsonString = NetWorkUtil.doGet(getRequestURL(local),null,null,null);
						response = JSON.parseObject(jsonString, ResponseBean.class);
						if (response != null && response.getError() == 0) {
							// 保存到本地
							ShareDataHelper.getInstance(getActivity()).saveWeather(local, jsonString);
							handler.sendEmptyMessage(MSG.LOAD_OK);
						}
					}
				} catch (Exception e) {
					handler.sendEmptyMessage(MSG.LOAD_ERROR);
				}
			}
		}).start();
	}
	
	/**
	 *	说明：获取请求百度天气API的URL
	 *	@param cityName 城市名字
	 */
	public static String getRequestURL(String cityName){
		return Constant.WETHER_URL+"?location=" +cityName + "&output="+ Constant.DATA_TYPE +"&ak="+ Constant.BAIDU_AK
				+"&mcode="+ Constant.BAIDU_MCODE;
	}
	
	/**实例化百度定位*/
	private void initLocalWhere(){
		// 第一次进来或者之前定位失败：先定位，然后进入引导页
		mLocClient = new LocationClient(getActivity());
		mLocClient.registerLocationListener(myListener);// 注册监听函数：
		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(10000);// 设置发起定位请求的间隔时间为10分钟
		option.setAddrType("all");
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
		mLocClient.setLocOption(option);
	}
	
	/** 定位SDK监听函数 */
	private class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (!isNeedFresh) {
				return;
			}
			isNeedFresh = false;
			if (location != null) {
				normalCity = location.getCity();
				Message msg = Message.obtain();
				msg.what = MSG.FRESH_OK;
				if (normalCity == null) {
					normalCity = "武汉";
				} else {
					String[] str = normalCity.split("市");
					normalCity = str[0];
				}
				handler.sendMessage(msg);
			}
		}
	}
	
	private int getWeatherBg(String weather){
		int imageId = R.drawable.clear_n;
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY); 
		if (hour >17 || hour < 7) {//晚上
			if (weather.contains("晴") && !weather.contains("雨")) {
				imageId = R.drawable.sun_n;
			}else if (weather.contains("多云")) {
				imageId = R.drawable.cloudy_n;
			}else if (weather.contains("雨")) {
				imageId = R.drawable.rain_n;
			}else if (weather.contains("雾")) {
				imageId = R.drawable.foggy_n;
			}else if (weather.contains("沙") || weather.contains("尘")) {
				imageId = R.drawable.storm_n;
			}else if (weather.contains("雪")) {
				imageId = R.drawable.snow_n;
			}else if (weather.contains("雾")) {
				imageId = R.drawable.foggy_n;
			}
		}else{
			if (weather.contains("晴") && !weather.contains("雨")) {
				imageId = R.drawable.sun_d;
			}else if (weather.contains("多云")) {
				imageId = R.drawable.cloudy_d;
			}else if (weather.contains("雨")) {
				imageId = R.drawable.weather_bg_rain;
			}else if (weather.contains("雾")) {
				imageId = R.drawable.foggy_d;
			}else if (weather.contains("沙") || weather.contains("尘")) {
				imageId = R.drawable.storm_d;
			}else if (weather.contains("雪")) {
				imageId = R.drawable.snow_d;
			}else if (weather.contains("雾")) {
				imageId = R.drawable.foggy_d;
			}
		}
		return imageId;
	}
}
