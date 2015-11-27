package com.haoxue.haoaccount.base;

import java.util.Arrays;
import java.util.Calendar;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.act.EditPropertyAct;
import com.haoxue.haoaccount.util.DateUtil;
import com.haoxue.haoaccount.view.WheelView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker.OnTimeChangedListener;

/**
 * 说明：记账页面的模板页
 * 作者：Luoyangs
 * 时间：2015-11-27
 */
public abstract class BaseActivity extends Activity {
	
	protected int pyear,pmonth,pday,phour,pminute;
	protected String dateStr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 获取当前的年、月、日、小时、分钟
		Calendar c = Calendar.getInstance();
		pyear = c.get(Calendar.YEAR);
		pmonth = c.get(Calendar.MONTH);
		pday = c.get(Calendar.DAY_OF_MONTH);
		phour = c.get(Calendar.HOUR_OF_DAY);
		pminute = c.get(Calendar.MINUTE);
		dateStr = pyear+"-"+getTimeFormat(pmonth)+"-"+getTimeFormat(pday)+"  "+getTimeFormat(phour)+":"+getTimeFormat(pminute);
	}
	
	protected void finishPage(){
		finish();
		overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
	}
	
	@SuppressWarnings({ "deprecation", "rawtypes" })
	protected void initMultChoice(final View view,String[] mainData,String[] subData,final Class editClass){
		//通过布局注入器，注入布局给View对象
        View myView = getLayoutInflater().inflate(R.layout.pop_twowheel_layout, null);
        //通过view 和宽·高，构造PopopWindow
        final PopupWindow pw = new PopupWindow(myView, LayoutParams.MATCH_PARENT, 694, true);
        //设置焦点为可点击
        pw.setFocusable(true);//可以试试设为false的结果
        pw.setOutsideTouchable(true);  
        pw.setBackgroundDrawable(new BitmapDrawable());
        //将window视图显示在最下面
        pw.showAtLocation(findViewById(R.id.myscroll),Gravity.BOTTOM, 0, 0);
        final WheelView main_wheel = (WheelView) myView.findViewById(R.id.main_wheel);
        final WheelView sub_wheel = (WheelView) myView.findViewById(R.id.sub_wheel);
        main_wheel.setItems(Arrays.asList(mainData));
        sub_wheel.setItems(Arrays.asList(subData));
        main_wheel.setSeletion(1);
        sub_wheel.setSeletion(1);
        main_wheel.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
            	((TextView)view).setText(item+"  ->  "+sub_wheel.getSeletedItem());
            }
        });
        sub_wheel.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
            	((TextView)view).setText(main_wheel.getSeletedItem()+"  ->  "+item);
            }
        });
        ((TextView)myView.findViewById(R.id.btnok)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setText(((TextView)view),main_wheel.getSeletedItem()+"  ->  "+sub_wheel.getSeletedItem());
				pw.dismiss();
			}
		});
        ((TextView)myView.findViewById(R.id.tv_ok)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setText(((TextView)view),main_wheel.getSeletedItem()+"  ->  "+sub_wheel.getSeletedItem());
				pw.dismiss();
			}
		});
        ((TextView)myView.findViewById(R.id.tv_edit)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(getBaseContext(),EditPropertyAct.class));
				pw.dismiss();
			}
		});
	}
	
	@SuppressWarnings({ "deprecation", "rawtypes" })
	protected void initSingleChoice(final View view,String[] mainData,final Class editClass){
		//通过布局注入器，注入布局给View对象
        View myView = getLayoutInflater().inflate(R.layout.pop_onewheel_layout, null);
        //通过view 和宽·高，构造PopopWindow
        final PopupWindow pw = new PopupWindow(myView, LayoutParams.MATCH_PARENT, 694, true);
        //设置焦点为可点击
        pw.setFocusable(true);//可以试试设为false的结果
        pw.setOutsideTouchable(true);  
        pw.setBackgroundDrawable(new BitmapDrawable());
        //将window视图显示在最下面
        pw.showAtLocation(findViewById(R.id.myscroll),Gravity.BOTTOM, 0, 0);
        final WheelView main_wheel = (WheelView) myView.findViewById(R.id.main_wheel);
        main_wheel.setItems(Arrays.asList(mainData));
        main_wheel.setSeletion(1);
        main_wheel.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                ((TextView)view).setText(item);
            }
        });
        ((TextView)myView.findViewById(R.id.btnok)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setText(((TextView)view),main_wheel.getSeletedItem());
				pw.dismiss();
			}
		});
        ((TextView)myView.findViewById(R.id.tv_ok)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setText(((TextView)view),main_wheel.getSeletedItem());
				pw.dismiss();
			}
		});
        ((TextView)myView.findViewById(R.id.tv_edit)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(getBaseContext(),editClass));
				pw.dismiss();
			}
		});
	}
	
	@SuppressWarnings({ "deprecation" })
	protected void initDateTime(final View view){
		//通过布局注入器，注入布局给View对象
        View myView = getLayoutInflater().inflate(R.layout.pop_datetime_layout, null);
        //通过view 和宽·高，构造PopopWindow
        final PopupWindow pw = new PopupWindow(myView, LayoutParams.MATCH_PARENT, 790, true);
        //设置焦点为可点击
        pw.setFocusable(true);//可以试试设为false的结果
        pw.setOutsideTouchable(true);  
        pw.setBackgroundDrawable(new BitmapDrawable());
        //将window视图显示在最下面
        pw.showAtLocation(findViewById(R.id.myscroll),Gravity.BOTTOM, 0, 0);
        final DatePicker datePicker = (DatePicker) myView.findViewById(R.id.datePicker);
        final TimePicker timePicker = (TimePicker) myView.findViewById(R.id.timePicker);
        DateUtil.resizePicker(datePicker);//调整datepicker大小
        DateUtil.resizePicker(timePicker);//调整timepicker大小
        //初始化DatePicker组件，初始化时指定监听器
        datePicker.init(pyear, pmonth, pday, new OnDateChangedListener() {
        	@Override
			public void onDateChanged(DatePicker arg0, int year, int month,int day) {
				pyear = year;
				pmonth = month;
				pday = day;
				((TextView)view).setText(year+"-"+getTimeFormat(month)+"-"+getTimeFormat(day)+"  "+getTimeFormat(phour)+":"+getTimeFormat(pminute));
			}
		});
        timePicker.setIs24HourView(true);  
        timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker arg0, int hour, int minute) {
				phour = hour;
				pminute = minute;
				((TextView)view).setText(pyear+"-"+getTimeFormat(pmonth)+"-"+getTimeFormat(pday)+"  "+getTimeFormat(hour)+":"+getTimeFormat(minute));
			}
        });
        ((TextView)myView.findViewById(R.id.btnok)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				((TextView)view).setText(pyear+"-"+getTimeFormat(pmonth)+"-"+getTimeFormat(pday)+"  "+getTimeFormat(phour)+":"+getTimeFormat(pminute));
				pw.dismiss();
			}
		});
        ((TextView)myView.findViewById(R.id.tv_ok)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				((TextView)view).setText(pyear+"-"+getTimeFormat(pmonth)+"-"+getTimeFormat(pday)+"  "+getTimeFormat(phour)+":"+getTimeFormat(pminute));
				pw.dismiss();
			}
		});
	}
	
	private void setText(TextView view,String text){
		if (view.getText() == null || view.getText().equals("")) {
			view.setText(text);
		}
	}
	
	protected String getTimeFormat(int form){
		return form < 10 ? ("0" + form) : String.valueOf(form);
	}
}
