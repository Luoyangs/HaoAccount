package com.haoxue.haoaccount.act;

import cn.pedant.SweetAlert.SweetAlertDialog;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.base.AssetDBManager;
import com.haoxue.haoaccount.base.BaseActivity;
import com.haoxue.haoaccount.base.Constant;
import com.haoxue.haoaccount.util.ToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 说明：转账
 * 作者：Luoyangs
 * 时间：2015-11-26
 */
@ContentView(R.layout.act_addtransfer_layout)
public class AddTransferAct extends BaseActivity implements OnClickListener{

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
	private SQLiteDatabase database;
	private int index = -1;
	private boolean finish = false;
	private int userId = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		database = new AssetDBManager().openDatabase(this);
		tvDate.setText(dateStr);//默认
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
			initMultChoice(view, Constant.DATA_ZA_SRC, Constant.DATA_ZA_SRC, EditPropertyAct.class);
			break;
		case R.id.tv_src:
			initSingleChoice(view,Constant.DATA_ZA_CMP,EditPropertyAct.class);
			break;
		case R.id.tv_to:
			initSingleChoice(view,Constant.DATA_ZA_CUS,EditPropertyAct.class);
			break;
		case R.id.tv_date:
			initDateTime(view);
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
			ToastUtil.showShort(this, "还没填写金额...");
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
		info += "\n成员：" + tvCmp.getText().toString()+ "\n商家：" + tvCus.getText().toString();
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
				values.put("type", 5);  
				values.put("froms", tvTo.getText().toString()+"&"+tvCmp.getText().toString());  //转出&成员
				values.put("use", tvCus.getText().toString());  //商家
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

}
