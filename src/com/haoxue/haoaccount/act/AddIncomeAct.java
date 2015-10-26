package com.haoxue.haoaccount.act;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import cn.pedant.SweetAlert.SweetAlertDialog;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.adapter.ListViewAdapter;
import com.haoxue.haoaccount.adapter.ListViewAdapter2;
import com.haoxue.haoaccount.base.AssetDBManager;
import com.haoxue.haoaccount.base.Constant;
import com.haoxue.haoaccount.util.ToastUtil;
import com.haoxue.haoaccount.view.wheel.StrericWheelAdapter;
import com.haoxue.haoaccount.view.wheel.WheelView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 说明:添加收入
 * 作者:Luoyangs
 * 时间:2015-10-5
 */
@ContentView(R.layout.act_addincom_layout)
public class AddIncomeAct extends FragmentActivity implements OnClickListener{

	@ViewInject(R.id.titilbar_title)
	private TextView titilbar;
	@ViewInject(R.id.input)
	private EditText etNum;
	@ViewInject(R.id.tv_type)
	private TextView tvType;
	@ViewInject(R.id.tv_src)
	private TextView tvSrc;
	@ViewInject(R.id.tv_to)
	private TextView tvTo;
	@ViewInject(R.id.tv_date)
	private TextView tvDate;
	@ViewInject(R.id.etinfo)
	private EditText tvInfo;
	
	private WheelView yearWheel,monthWheel,dayWheel,hourWheel,minuteWheel,secondWheel;
	private static String[] yearContent=null;
	private static String[] monthContent=null;
	private static String[] dayContent=null;
	private static String[] hourContent = null;
	private static String[] minuteContent=null;
	private static String[] secondContent=null;
	
	private int index = -1;
	private boolean finish = false;
	private int userId = -1;
	private int ptypeId = -1;
	private int ctypeId = -1;
	private int isEdit = 0;//0表示新添，1表示查看，2表示编辑
	private SQLiteDatabase database;
	private String ptype = "";
	private String ctype = "";
	
	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		database = new AssetDBManager().openDatabase(this);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		isEdit = getIntent().getIntExtra("isEdit", 0);
		if (isEdit == 0) {
			titilbar.setText("新加收入");
			tvDate.setText(sdf.format(new Date()));
			tvDate.setOnClickListener(this);
			tvType.setOnClickListener(this);
			tvSrc.setOnClickListener(this);
			tvTo.setOnClickListener(this);
		}else if (isEdit == 1) {
			titilbar.setText("查看收入");
		}else{
			titilbar.setText("编辑收入");
		}
	}

	@OnClick(R.id.titilbar_left)
	public void onBack(View view){
		finishPage();
	}
	
	@OnClick(R.id.btn_cancel)
	public void onCancel(View view){
		finishPage();
	}
	
	@OnClick(R.id.btn_save)
	public void onSave(View view){
		//核查输入
		if (etNum.getText() == null || etNum.getText().length() == 0) {
			ToastUtil.showShort(this, "还没填写收入...");
			return;
		}
		if (tvType.getText() == null || tvType.getText().length() == 0) {
			ToastUtil.showShort(this, "请选择类别...");
			return;
		}
		if (tvSrc.getText() == null || tvSrc.getText().length() == 0) {
			ToastUtil.showShort(this, "请选择来源...");
			return;
		}
		if (tvTo.getText() == null || tvTo.getText().length() == 0) {
			ToastUtil.showShort(this, "请选择存储...");
			return;
		}
		String info = "金额：" + etNum.getText().toString() + "\n类别：" + tvType.getText().toString();
		info += "\n来源：" + tvSrc.getText().toString()+ "\n存储：" + tvTo.getText().toString();
		info += "\n日期：" + tvDate.getText().toString()+ "\n备注：" + tvInfo.getText().toString();
		final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
		pDialog.setTitleText("请确认信息···");
		pDialog.setContentText(info);
		pDialog.show();
		final CountDownTimer timer = new CountDownTimer(800 * 7, 800) {
			public void onTick(long millisUntilFinished) {
				index++;
				switch (index) {
				case 0:
					pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.blue_btn_bg_color));
					break;
				case 1:
					pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_50));
					break;
				case 2:
					pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
					break;
				case 3:
					pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_20));
					break;
				case 4:
					pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_blue_grey_80));
					break;
				case 5:
					pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.warning_stroke_color));
					break;
				case 6:
					pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
					break;
				}
			}

			@Override
			public void onFinish() {
				index = -1;
				if (!finish) {
					this.start();
				}else{
					pDialog.setTitleText("数据添加成功!").setConfirmText("关闭").showContentText(false).showCancelButton(false).changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
					pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
						@Override
						public void onClick(SweetAlertDialog sweetAlertDialog) {
							pDialog.dismissWithAnimation();
							finish = false;
							//关闭页面
							finishPage();
						}
					});
				}
			}
		}.start();
		pDialog.setConfirmText("确定无误").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {

			@Override
			public void onClick(SweetAlertDialog sweetAlertDialog) {
				//存进数据库
				ContentValues values = new ContentValues();  
				values.put("userId", userId);  
				values.put("num", Float.parseFloat(etNum.getText().toString()));  
				values.put("type", 1);  
				values.put("ptype", ptypeId);  
				values.put("ctype", ctypeId);  
				values.put("froms", tvSrc.getText().toString());  
				values.put("save", tvTo.getText().toString());  
				values.put("info", tvInfo.getText().toString()); 
				values.put("info", tvDate.getText().toString()); 
				long result = database.insert(Constant.DB.INCOME_TABLE_NAME, "id", values);
				if (result > 0) {
					finish = true;
				}
				timer.onFinish();
			}

		});
		
		pDialog.setCancelText("返回修改").setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
			
			@Override
			public void onClick(SweetAlertDialog sweetAlertDialog) {
				sweetAlertDialog.dismissWithAnimation();
			}
		});
	}
	
	private void finishPage(){
		finish();
		overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_type:
			initType(view);
			break;
		case R.id.tv_src:
			initSrc(view);
			break;
		case R.id.tv_to:
			initTo(view);
			break;
		case R.id.tv_date:
			initDateTimer();
			break;
		}
	}
	
	private void initTo(View view) {
		String[] srcs = getResources().getStringArray(R.array.income_save);
		final List<String> list = Arrays.asList(srcs);
		//通过布局注入器，注入布局给View对象
        View myView = getLayoutInflater().inflate(R.layout.pop_layout, null);
        //通过view 和宽·高，构造PopopWindow
        final PopupWindow pw = new PopupWindow(myView, 420, 500, true);
        pw.setBackgroundDrawable(getResources().getDrawable(R.color.base_gray));
        //设置焦点为可点击
        pw.setFocusable(true);//可以试试设为false的结果
        //将window视图显示在myButton下面
        pw.showAsDropDown(view,0,0,Gravity.RIGHT);

        ListView lv = (ListView) myView.findViewById(R.id.lv_pop);
        lv.setAdapter(new ListViewAdapter(AddIncomeAct.this, list));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvTo.setText(list.get(position));
                pw.dismiss();
            }
        });
	}

	private void initSrc(View view){
		String[] srcs = getResources().getStringArray(R.array.income_from);
		final List<String> list = Arrays.asList(srcs);
		//通过布局注入器，注入布局给View对象
        View myView = getLayoutInflater().inflate(R.layout.pop_layout, null);
        //通过view 和宽·高，构造PopopWindow
        final PopupWindow pw = new PopupWindow(myView, 420, 500, true);
        pw.setBackgroundDrawable(getResources().getDrawable(R.color.base_gray));
        //设置焦点为可点击
        pw.setFocusable(true);//可以试试设为false的结果
        //将window视图显示在myButton下面
        pw.showAsDropDown(view,0,0,Gravity.RIGHT);

        ListView lv = (ListView) myView.findViewById(R.id.lv_pop);
        lv.setAdapter(new ListViewAdapter(AddIncomeAct.this, list));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvSrc.setText(list.get(position));
                pw.dismiss();
            }
        });
	}
	
	@SuppressWarnings("deprecation")
	private void initDateTimer(){
		yearContent = new String[10];
		for(int i=0;i<10;i++)
			yearContent[i] = String.valueOf(i+2013);
		
		monthContent = new String[12];
		for(int i=0;i<12;i++)
		{
			monthContent[i]= String.valueOf(i+1);
			if(monthContent[i].length()<2)
	        {
				monthContent[i] = "0"+monthContent[i];
	        }
		}
			
		dayContent = new String[31];
		for(int i=0;i<31;i++)
		{
			dayContent[i]=String.valueOf(i+1);
			if(dayContent[i].length()<2)
	        {
				dayContent[i] = "0"+dayContent[i];
	        }
		}	
		hourContent = new String[24];
		for(int i=0;i<24;i++)
		{
			hourContent[i]= String.valueOf(i);
			if(hourContent[i].length()<2)
	        {
				hourContent[i] = "0"+hourContent[i];
	        }
		}
			
		minuteContent = new String[60];
		for(int i=0;i<60;i++)
		{
			minuteContent[i]=String.valueOf(i);
			if(minuteContent[i].length()<2)
	        {
				minuteContent[i] = "0"+minuteContent[i];
	        }
		}
		secondContent = new String[60];
		for(int i=0;i<60;i++)
		{
			secondContent[i]=String.valueOf(i);
			if(secondContent[i].length()<2)
	        {
				secondContent[i] = "0"+secondContent[i];
	        }
		}
		Calendar calendar = Calendar.getInstance();
	    int curYear = calendar.get(Calendar.YEAR);
        int curMonth= calendar.get(Calendar.MONTH)+1;
        int curDay = calendar.get(Calendar.DAY_OF_MONTH);
        int curHour = calendar.get(Calendar.HOUR_OF_DAY);
        int curMinute = calendar.get(Calendar.MINUTE);
        int curSecond = calendar.get(Calendar.SECOND);
 	    
        View view = LayoutInflater.from(this).inflate(R.layout.time_picker, null);
	    yearWheel = (WheelView)view.findViewById(R.id.yearwheel);
	    monthWheel = (WheelView)view.findViewById(R.id.monthwheel);
	    dayWheel = (WheelView)view.findViewById(R.id.daywheel);
	    hourWheel = (WheelView)view.findViewById(R.id.hourwheel);
	    minuteWheel = (WheelView)view.findViewById(R.id.minutewheel);
	    secondWheel = (WheelView)view.findViewById(R.id.secondwheel);
        
        yearWheel.setAdapter(new StrericWheelAdapter(yearContent));
	 	yearWheel.setCurrentItem(curYear-2013);
	    yearWheel.setCyclic(true);
	    yearWheel.setInterpolator(new AnticipateOvershootInterpolator());
 
        monthWheel.setAdapter(new StrericWheelAdapter(monthContent));
        monthWheel.setCurrentItem(curMonth-1);
     
        monthWheel.setCyclic(true);
        monthWheel.setInterpolator(new AnticipateOvershootInterpolator());
        
        dayWheel.setAdapter(new StrericWheelAdapter(dayContent));
        dayWheel.setCurrentItem(curDay-1);
        dayWheel.setCyclic(true);
        dayWheel.setInterpolator(new AnticipateOvershootInterpolator());
        
        hourWheel.setAdapter(new StrericWheelAdapter(hourContent));
        hourWheel.setCurrentItem(curHour);
        hourWheel.setCyclic(true);
        hourWheel.setInterpolator(new AnticipateOvershootInterpolator());
        
        minuteWheel.setAdapter(new StrericWheelAdapter(minuteContent));
        minuteWheel.setCurrentItem(curMinute);
        minuteWheel.setCyclic(true);
        minuteWheel.setInterpolator(new AnticipateOvershootInterpolator());
        
        secondWheel.setAdapter(new StrericWheelAdapter(secondContent));
        secondWheel.setCurrentItem(curSecond);
        secondWheel.setCyclic(true);
        secondWheel.setInterpolator(new AnticipateOvershootInterpolator());
        
        final PopupWindow pw = new PopupWindow(view, LayoutParams.MATCH_PARENT, 636, true);
        //设置焦点为可点击
        pw.setFocusable(true);//可以试试设为false的结果
        pw.setOutsideTouchable(true);  
        pw.setBackgroundDrawable(new BitmapDrawable());
        //将window视图显示在最下面
        pw.showAtLocation(findViewById(R.id.myscroll),Gravity.BOTTOM, 0, 0);
        TextView btnok = (TextView) view.findViewById(R.id.btnok);
        btnok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				StringBuffer sb = new StringBuffer();  
        		sb.append(yearWheel.getCurrentItemValue())
        		  .append("-")
        		  .append(monthWheel.getCurrentItemValue())
        		  .append("-")
        		  .append(dayWheel.getCurrentItemValue())
        		  .append(" ") 
        		  .append(hourWheel.getCurrentItemValue())  
        		  .append(":")
        		  .append(minuteWheel.getCurrentItemValue())
        		  .append(":")
        		  .append(secondWheel.getCurrentItemValue());  
        		tvDate.setText(sb.toString());
				pw.dismiss();
			}
		});
	}
	
	@SuppressWarnings("deprecation")
	private void initType(View view){
		final ArrayList<Map<String, String>> plist = getParentType();
		//通过布局注入器，注入布局给View对象
        View myView = getLayoutInflater().inflate(R.layout.pop_layout2, null);
        //通过view 和宽·高，构造PopopWindow
        final PopupWindow pw = new PopupWindow(myView, LayoutParams.MATCH_PARENT, 600, true);
        //设置焦点为可点击
        pw.setFocusable(true);//可以试试设为false的结果
        pw.setOutsideTouchable(true);  
        pw.setBackgroundDrawable(new BitmapDrawable());
        //将window视图显示在最下面
        pw.showAtLocation(findViewById(R.id.myscroll),Gravity.BOTTOM, 0, 0);

        ListView plv = (ListView) myView.findViewById(R.id.plist);
        final ListView clv = (ListView) myView.findViewById(R.id.clist);
        TextView btnok = (TextView) myView.findViewById(R.id.btnok);
        plv.setAdapter(new ListViewAdapter2(AddIncomeAct.this, plist));
        plv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	if (ptype.length() >0) {
            		ptype = "";
				}
            	ptypeId = position;
            	ptype = plist.get(position).get("name");
            	final ArrayList<Map<String, String>> clist = getChildType(ptype);
                clv.setAdapter(new ListViewAdapter2(AddIncomeAct.this, clist));
                clv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						if (ctype.length() >0) {
							ctype = "";
						}
						ctypeId = position;
						ctype = clist.get(position).get("name");
						tvType.setText(ptype+"-"+ctype);
					}
                });
            }
        });
        btnok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				pw.dismiss();
			}
		});
	}
	
	/**获取父类别*/
	private ArrayList<Map<String, String>> getParentType() {
		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Cursor cursor = database.rawQuery(Constant.DB.GET_PTYPE_BY_TYPE, new String[]{String.valueOf(1)});
        while(cursor.moveToNext()){
        	Map<String, String> map = new HashMap<String, String>();
        	map.put("img", cursor.getString(cursor.getColumnIndex("img")));
        	map.put("name", cursor.getString(cursor.getColumnIndex("name")));
        	list.add(map);
        }
        return list;
    }
	
	/**依据父类别获取子类别*/
	private ArrayList<Map<String, String>> getChildType(String ptype){
		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Cursor cursor = database.rawQuery(Constant.DB.GET_CTYPE_BY_PTYPE, new String[]{String.valueOf(1),ptype}); 
        while(cursor.moveToNext()){
        	Map<String, String> map = new HashMap<String, String>();
        	map.put("img", cursor.getString(cursor.getColumnIndex("img")));
        	map.put("name", cursor.getString(cursor.getColumnIndex("name")));
        	list.add(map);
        }
        return list;
	}

}
