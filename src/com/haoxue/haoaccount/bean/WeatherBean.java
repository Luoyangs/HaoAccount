package com.haoxue.haoaccount.bean;

import java.util.List;

/** 
 *	说明：天气预报实体
 *	作者： Luoyangs
 *	时间： 2015年8月17日
 */
public class WeatherBean {
	private String currentCity;//当前城市
	private List<WeatherSubBean> weather_data;//天气预报信息
	private String pm25;//PM2.5值
	private String index;//各项指数
	
	public String getCurrentCity() {
		return currentCity;
	}
	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}
	public List<WeatherSubBean> getWeather_data() {
		return weather_data;
	}
	public void setWeather_data(List<WeatherSubBean> weather_data) {
		this.weather_data = weather_data;
	}
	public String getPm25() {
		return pm25;
	}
	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
}