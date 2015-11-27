package com.haoxue.haoaccount.act;

import java.util.Arrays;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.base.AssetDBManager;
import com.haoxue.haoaccount.base.Constant;
import com.haoxue.haoaccount.util.DateUtil;
import com.haoxue.haoaccount.util.ToastUtil;
import com.haoxue.haoaccount.view.WheelView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

/**
 * 说明：转账
 * 作者：Luoyangs
 * 时间：2015-11-26
 */
@ContentView(R.layout.act_addtransfer_layout)
public class AddTransferAct extends Activity implements OnClickListener{

	@ViewInject(R.id.titilbar_title)
	private TextView titilbar;
	@ViewInject(R.id.input)
	private EditText etNum;
	@ViewInject(R.id.tv_type)
	private TextView tvTo;//转出
	@ViewInject(R.id.tv_src)
	private TextView tvCmp;//成员
	@ViewInject(R.id.tv_to)
	private TextView tvCus;//商家
	@ViewInject(R.id.tv_date)
	private TextView tvDate;
	@ViewInject(R.id.etinfo)
	private EditText tvInfo;
	private int pyear,pmonth,pday,phour,pminute;
	private SQLiteDatabase database;
	private int index = -1;
	private boolean finish = false;
	private int userId = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		database = new AssetDBManager().openDatabase(this);
		// 获取当前的年、月、日、小时、分钟
		Calendar c = Calendar.getInstance();
		pyear = c.get(Calendar.YEAR);
		pmonth = c.get(Calendar.MONTH);
		pday = c.get(Calendar.DAY_OF_MONTH);
		phour = c.get(Calendar.HOUR_OF_DAY);
		pminute = c.get(Calendar.MINUTE);
		
		tvDate.setText(pyear+"-"+getTimeFormat(pmonth)+"-"+getTimeFormat(pday)+"  "+getTimeFormat(phour)+":"+getTimeFormat(pminute));//默认
		//添加事件监听
		tvTo.setOnClickListener(this);
		tvCmp.setOnClickListener(this);
		tvCus.setOnClickListener(this);
		tvDate.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_type:
			initType(view);
			break;
		case R.id.tv_src:
			initSingleChoice(view,Constant.DATA_ZA_CMP,EditPropertyAct.class);
			break;
		case R.id.tv_to:
			initSingleChoice(view,Constant.DATA_ZA_CUS,EditPropertyAct.class);
			break;
		case R.id.tv_date:
			initDateTime();
			break;
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
		if (tvTo.getText() == null || tvTo.getText().length() == 0) {
			ToastUtil.showShort(this, "请选择转出方向...");
			return;
		}
		if (tvCmp.getText() == null || tvCmp.getText().length() == 0) {
			ToastUtil.showShort(this, "请选择成员...");
			return;
		}
		if (tvCus.getText() == null || tvCus.getText().length() == 0) {
			ToastUtil.showShort(this, "请选择商家...");
			return;
		}
		String info = "金额：" + etNum.getText().toString() + "\n转出方向：" + tvTo.getText().toString();
		info += "\n成员：" + tvCmp.getText().toString()+ "\n商家：" + tvTo.getText().toString();
		info += "\n日期：" + tvDate.getText().toString()+ "\n备注：" + tvCus.getText().toString();
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
				values.put("type", 5);  
				values.put("froms", tvTo.getText().toString()+"&"+tvCmp.getText().toString());  //转出&成员
				values.put("save", tvCus.getText().toString());  //商家
				values.put("info", tvInfo.getText().toString()); 
				values.put("date", tvDate.getText().toString()); 
				long result = database.insert(Constant.DB.OUTCOME_TABLE_NAME, "id", values);
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

	@SuppressWarnings("deprecation")
	private void initType(View view){
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
        main_wheel.setItems(Arrays.asList(Constant.DATA_ZA_SRC));
        sub_wheel.setItems(Arrays.asList(Constant.DATA_ZA_SRC));
        main_wheel.setSeletion(1);
        sub_wheel.setSeletion(1);
        main_wheel.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                tvTo.setText(item+" ->: "+sub_wheel.getSeletedItem());
            }
        });
        sub_wheel.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                tvTo.setText(main_wheel.getSeletedItem()+" ->: "+item);
            }
        });
        ((TextView)myView.findViewById(R.id.btnok)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setText(tvTo,main_wheel.getSeletedItem()+" ->: "+sub_wheel.getSeletedItem());
				pw.dismiss();
			}
		});
        ((TextView)myView.findViewById(R.id.tv_ok)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setText(tvTo,main_wheel.getSeletedItem()+" ->: "+sub_wheel.getSeletedItem());
				pw.dismiss();
			}
		});
        ((TextView)myView.findViewById(R.id.tv_edit)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(AddTransferAct.this,EditPropertyAct.class));
				pw.dismiss();
			}
		});
	}
	
	@SuppressWarnings({ "deprecation", "rawtypes" })
	private void initSingleChoice(final View view,String[] dataStrs,final Class editClass){
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
        main_wheel.setItems(Arrays.asList(dataStrs));
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
				startActivity(new Intent(AddTransferAct.this,editClass));
				pw.dismiss();
			}
		});
	}
	
	@SuppressWarnings("deprecation")
	private void initDateTime(){
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
				tvDate.setText(year+"-"+getTimeFormat(month)+"-"+getTimeFormat(day)+"  "+getTimeFormat(phour)+":"+getTimeFormat(pminute));
			}
		});
        timePicker.setIs24HourView(true);  
        timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker arg0, int hour, int minute) {
				phour = hour;
				pminute = minute;
				tvDate.setText(pyear+"-"+getTimeFormat(pmonth)+"-"+getTimeFormat(pday)+"  "+getTimeFormat(hour)+":"+getTimeFormat(minute));
			}
        });
        ((TextView)myView.findViewById(R.id.btnok)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				tvDate.setText(pyear+"-"+getTimeFormat(pmonth)+"-"+getTimeFormat(pday)+"  "+getTimeFormat(phour)+":"+getTimeFormat(pminute));
				pw.dismiss();
			}
		});
        ((TextView)myView.findViewById(R.id.tv_ok)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				tvDate.setText(pyear+"-"+getTimeFormat(pmonth)+"-"+getTimeFormat(pday)+"  "+getTimeFormat(phour)+":"+getTimeFormat(pminute));
				pw.dismiss();
			}
		});
	}
	
	private void setText(TextView view,String text){
		if (view.getText() == null || view.getText().equals("")) {
			view.setText(text);
		}
	}
	
	private String getTimeFormat(int form){
		return form < 10 ? ("0" + form) : String.valueOf(form);
	}
}
