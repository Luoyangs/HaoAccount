package com.haoxue.haoaccount.act;

import cn.pedant.SweetAlert.SweetAlertDialog;
import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.base.AssetDBManager;
import com.haoxue.haoaccount.base.BaseActivity;
import com.haoxue.haoaccount.base.Constant;
import com.haoxue.haoaccount.base.Constant.ACTION;
import com.haoxue.haoaccount.base.Constant.DB;
import com.haoxue.haoaccount.util.ToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.swichlayout.BaseEffects;
import com.tandong.swichlayout.SwitchLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 说明:添加支出
 * 作者:Luoyangs
 * 时间:2015-10-5
 */
@ContentView(R.layout.act_addaccount_layout)
public class AddAccountAct extends BaseActivity implements OnClickListener{

	@ViewInject(R.id.titilbar_title)
	private TextView titilbar;
	@ViewInject(R.id.input)
	private EditText etNum;
	@ViewInject(R.id.tv_item1_label)
	private TextView tv_item1_label;
	@ViewInject(R.id.tv_item1)
	private TextView tv_item1;
	@ViewInject(R.id.et_item1)
	private EditText et_item1;//输入框
	@ViewInject(R.id.tv_itemp_label)//补充
	private TextView tv_itemp_label;
	@ViewInject(R.id.tv_itemp)
	private TextView tv_itemp;
	@ViewInject(R.id.tv_item2_label)
	private TextView tv_item2_label;
	@ViewInject(R.id.tv_item2)
	private TextView tv_item2;
	@ViewInject(R.id.tv_item3_label)
	private TextView tv_item3_label;
	@ViewInject(R.id.tv_item3)
	private TextView tv_item3;
	@ViewInject(R.id.tv_date)
	private TextView tvDate;
	@ViewInject(R.id.etinfo)
	private EditText tvInfo;
	@ViewInject(R.id.lp)
	private LinearLayout lp;
	
	private int index = -1;
	private boolean finish = false;
	private SQLiteDatabase database;
	private int action = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		SwitchLayout.getSlideFromBottom(this, false,BaseEffects.getQuickToSlowEffect());
		database = new AssetDBManager().openDatabase(this);
		tvDate.setText(dateStr);
		//判断当前是什么操作
		action = getIntent().getIntExtra("action", 0);
		switch (action) {
		case ACTION.INCOMEACT:
			titilbar.setText("新加收入");
			lp.setVisibility(View.VISIBLE);
			tv_item1_label.setText("类别");
			tv_itemp_label.setText("账户");
			tv_item2_label.setText("成员");
			tv_item3_label.setText("项目");
			break;
		case ACTION.OUTCOMEACT:
			titilbar.setText("新加支出");
			lp.setVisibility(View.VISIBLE);
			tv_item1_label.setText("类别");
			tv_itemp_label.setText("账户");
			tv_item2_label.setText("成员");
			tv_item3_label.setText("项目");
			break;
		case ACTION.TRANSFERACT:
			titilbar.setText("新加转账");
			lp.setVisibility(View.VISIBLE);
			tv_item1_label.setText("转出");
			tv_itemp_label.setText("商家");
			tv_item2_label.setText("成员");
			tv_item3_label.setText("项目");
			break;
		case ACTION.LENDACT:
			titilbar.setText("新加借贷");
			tv_item1_label.setText("债权人");
			tv_item2_label.setText("账户");
			tv_itemp_label.setText("成员");
			tv_item3_label.setText("项目");
			break;
		case ACTION.EXPENCEACT:
			titilbar.setText("新加报销");
			tv_item1_label.setText("报销项目");
			tv_item2_label.setText("债权账户");
			tv_item3_label.setText("项目");
			break;
		case ACTION.CLASSACT:
			titilbar.setText("新加班费");
			break;
		case ACTION.CHARGEACT:
			titilbar.setText("新加代付");
			break;
		case ACTION.PAYBACKACT:
			titilbar.setText("新加退款");
			break;
		case ACTION.BUYINACT:
			titilbar.setText("新加进货");
			break;
		case ACTION.CUSTOMEACT:
			titilbar.setText("新加销售");
			break;
		}
		//添加监听事件
		tv_item1.setOnClickListener(this);
		tv_itemp.setOnClickListener(this);
		tv_item2.setOnClickListener(this);
		tv_item3.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		switch (action) {
		case ACTION.INCOMEACT:
			switch (view.getId()) {
			case R.id.tv_item1:
				initMultChoice(view,getOutcomeParentType(1),null);
				break;
			case R.id.tv_itemp:
				initMultChoice(view, Constant.DATA_OUT_ACCOUNT, Constant.DATA_OUT_ACCOUNT_SUB, EditPropertyAct.class);
				break;
			case R.id.tv_item2:
				initSingleChoice(view, Constant.DATA_ZA_CMP, EditPropertyAct.class);
				break;
			case R.id.tv_item3:
				initSingleChoice(view, Constant.DATA_ZA_CUS, EditPropertyAct.class);
				break;
			}
			break;
		case ACTION.OUTCOMEACT:
			switch (view.getId()) {
			case R.id.tv_item1:
				initMultChoice(view,getOutcomeParentType(2),null);
				break;
			case R.id.tv_itemp:
				initMultChoice(view, Constant.DATA_OUT_ACCOUNT, Constant.DATA_OUT_ACCOUNT_SUB, EditPropertyAct.class);
				break;
			case R.id.tv_item2:
				initSingleChoice(view, Constant.DATA_ZA_CMP, EditPropertyAct.class);
				break;
			case R.id.tv_item3:
				initSingleChoice(view, Constant.DATA_ZA_CUS, EditPropertyAct.class);
				break;
			}
			break;
		case ACTION.TRANSFERACT:
			switch (view.getId()) {
			case R.id.tv_item1:
				initMultChoice(view, Constant.DATA_ZA_SRC, Constant.DATA_ZA_SRC, EditPropertyAct.class);
				break;
			case R.id.tv_itemp:
				initSingleChoice(view, Constant.DATA_ZA_CUS, EditPropertyAct.class);
				break;
			case R.id.tv_item2:
				initSingleChoice(view, Constant.DATA_ZA_CMP, EditPropertyAct.class);
				break;
			case R.id.tv_item3:
				initSingleChoice(view, Constant.DATA_ZA_PRO, EditPropertyAct.class);
				break;
			}
			break;
		case ACTION.LENDACT:
			switch (view.getId()) {
			case R.id.tv_item1:
				tv_item1.setVisibility(View.GONE);
				et_item1.setVisibility(View.VISIBLE);
				break;
			case R.id.tv_itemp://成员
				initSingleChoice(view, Constant.DATA_ZA_CMP, EditPropertyAct.class);
				break;
			case R.id.tv_item2://账户
				initMultChoice(view, Constant.DATA_LD_ACCOUNT, Constant.DATA_ZA_SRC, null);
				break;
			case R.id.tv_item3://项目
				initSingleChoice(view, Constant.DATA_ZA_PRO, EditPropertyAct.class);
				break;
			}
			break;
		case ACTION.EXPENCEACT:
			switch (view.getId()) {
			case R.id.tv_item1:
				tv_item1.setVisibility(View.GONE);
				et_item1.setVisibility(View.VISIBLE);
				break;
			case R.id.tv_item2:
				initSingleChoice(view, Constant.DATA_ZA_SRC, EditPropertyAct.class);
				break;
			case R.id.tv_item3:
				initSingleChoice(view, Constant.DATA_ZA_PRO, EditPropertyAct.class);
				break;
			}
			break;
		case ACTION.CLASSACT:
			
			break;
		case ACTION.CHARGEACT:
			
			break;
		case ACTION.PAYBACKACT:
			
			break;
		case ACTION.BUYINACT:
			
			break;
		case ACTION.CUSTOMEACT:
			
			break;
		}
	}
	
	@OnClick(R.id.titilbar_title)
	public void onChangeView(View view){
		startActivity(new Intent(AddAccountAct.this,MoreTypeAct.class));
	}
	
	@OnClick(R.id.tv_date)
	public void onDatePick(View view){
		initDateTime(view);
	}
	
	@OnClick(R.id.btn_cancel)
	public void onCancel(View view){
		finishPage();
	}
	
	@OnClick(R.id.btn_save)
	public void onSave(View view){
		switch (action) {
		case ACTION.INCOMEACT:
			saveIncome();
			break;
		case ACTION.OUTCOMEACT:
			saveOutcome();
			break;
		case ACTION.TRANSFERACT:
			
			break;
		case ACTION.LENDACT:
			
			break;
		case ACTION.EXPENCEACT:

			break;
		case ACTION.CLASSACT:

			break;
		case ACTION.CHARGEACT:
			
			break;
		case ACTION.PAYBACKACT:
			
			break;
		case ACTION.BUYINACT:
			
			break;
		case ACTION.CUSTOMEACT:
			
			break;
		}
	}
	
	private void saveIncome(){
		if (etNum.getText() == null || etNum.getText().length() == 0) {
			ToastUtil.showShort(this, "还没填写金额...");
			return;
		}
		validateText(new String[]{"请选择类别...","请选择账户...","请选择成员...","请选择项目..."},tv_item1,tv_itemp,tv_item2,tv_item3);
		String info = "金额：" + etNum.getText().toString() + "\n类别：" + tv_item1.getText().toString();
		info += "\n账户：" + tv_itemp.getText().toString()+ "\n成员：" + tv_item2.getText().toString()+ "\n项目：" + tv_item3.getText().toString();
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
				values.put("item1", tv_item1.getText().toString());  
				values.put("item2", tv_itemp.getText().toString()); 
				values.put("item3", tv_item2.getText().toString());  
				values.put("item4", tv_item3.getText().toString());  
				values.put("info", tvInfo.getText().toString()); 
				values.put("date", tvDate.getText().toString()); 
				long result = database.insert(DB.ACCOUNT_TABLE_NAME, "id", values);
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
	
	/**保存支出*/
	private void saveOutcome(){
		if (etNum.getText() == null || etNum.getText().length() == 0) {
			ToastUtil.showShort(this, "还没填写金额...");
			return;
		}
		validateText(new String[]{"请选择类别...","请选择来源...","请选择用途..."},tv_item1,tv_item2,tv_item3);
		String info = "金额：" + etNum.getText().toString() + "\n类别：" + tv_item1.getText().toString();
		info += "\n来源：" + tv_item2.getText().toString()+ "\n用途：" + tv_item3.getText().toString();
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
				values.put("type", 2);  
				values.put("ptype", ptypeId);  
				values.put("ctype", ctypeId);  
				values.put("froms", tv_item2.getText().toString());  
				values.put("use", tv_item3.getText().toString());  
				values.put("info", tvInfo.getText().toString()); 
				values.put("date", tvDate.getText().toString()); 
				long result = database.insert(DB.PREPAY_TABLE_NAME, "id", values);
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
	
	/**验证是否填写*/
	private void validateText(String[] msgInfo,TextView... view){
		for (int i = 0; i < msgInfo.length; i++) {
			if (view[i].getText() == null || view[i].getText().length() == 0) {
				ToastUtil.showShort(this, msgInfo[i]);
				return;
			}
		}
	}
	
	/**获取父类别*/
	private ArrayList<Map<String, String>> getOutcomeParentType(int type) {
		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Cursor cursor = database.rawQuery(DB.GET_PTYPE_BY_TYPE, new String[]{String.valueOf(type)});
        while(cursor.moveToNext()){
        	Map<String, String> map = new HashMap<String, String>();
        	map.put("img", cursor.getString(cursor.getColumnIndex("img")));
        	map.put("name", cursor.getString(cursor.getColumnIndex("name")));
        	list.add(map);
        }
        return list;
    }
	
	@Override
	protected ArrayList<Map<String, String>> getChildType(String ptype){
		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        if (action == ACTION.OUTCOMEACT) {
        	list.clear();
        	Cursor cursor = database.rawQuery(DB.GET_CTYPE_BY_PTYPE, new String[]{String.valueOf(2),ptype}); 
            while(cursor.moveToNext()){
            	Map<String, String> map = new HashMap<String, String>();
            	map.put("img", cursor.getString(cursor.getColumnIndex("img")));
            	map.put("name", cursor.getString(cursor.getColumnIndex("name")));
            	list.add(map);
            }
		}else if (action == ACTION.INCOMEACT) {
			list.clear();
			Cursor cursor = database.rawQuery(Constant.DB.GET_CTYPE_BY_PTYPE, new String[]{String.valueOf(1),ptype}); 
	        while(cursor.moveToNext()){
	        	Map<String, String> map = new HashMap<String, String>();
	        	map.put("img", cursor.getString(cursor.getColumnIndex("img")));
	        	map.put("name", cursor.getString(cursor.getColumnIndex("name")));
	        	list.add(map);
	        }
		}
        return list;
	}

}
