package com.haoxue.haoaccount.act;

import cn.pedant.SweetAlert.SweetAlertDialog;
import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.adapter.PopMultTypeAdapter;
import com.haoxue.haoaccount.base.AssetDBManager;
import com.haoxue.haoaccount.base.BaseActivity;
import com.haoxue.haoaccount.base.Constant;
import com.haoxue.haoaccount.util.ToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 说明:添加支出
 * 作者:Luoyangs
 * 时间:2015-10-5
 */
@ContentView(R.layout.act_addoutcom_layout)
public class AddOutcomeAct extends BaseActivity implements OnClickListener{

	@ViewInject(R.id.titilbar_title)
	private TextView titilbar;
	@ViewInject(R.id.input)
	private EditText etNum;
	@ViewInject(R.id.tv_type)
	private TextView tvType;
	@ViewInject(R.id.tv_src)
	private TextView tvSrc;
	@ViewInject(R.id.tv_to)
	private TextView tvUse;
	@ViewInject(R.id.tv_date)
	private TextView tvDate;
	@ViewInject(R.id.etinfo)
	private EditText tvInfo;
	
	private int index = -1;
	private boolean finish = false;
	private int userId = -1;
	private int ptypeId = -1;
	private int ctypeId = -1;
	private int isEdit = 0;//0表示新添，1表示查看，2表示编辑
	private SQLiteDatabase database;
	private String ptype = "";
	private String ctype = "";
	private PopMultTypeAdapter pTypeAdapter;
	private PopMultTypeAdapter cTypeAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		database = new AssetDBManager().openDatabase(this);
		
		isEdit = getIntent().getIntExtra("isEdit", 0);
		if (isEdit == 0) {
			titilbar.setText("新加支出");
			tvDate.setText(dateStr);
			tvDate.setOnClickListener(this);
			tvType.setOnClickListener(this);
			tvSrc.setOnClickListener(this);
			tvUse.setOnClickListener(this);
		}else if (isEdit == 1) {
			titilbar.setText("查看支出");
		}else{
			titilbar.setText("编辑支出");
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_type:
			initType(view);
			break;
		case R.id.tv_src:
			initMultChoice(view, Constant.DATA_OUT_ACCOUNT, Constant.DATA_OUT_ACCOUNT, EditPropertyAct.class);
			break;
		case R.id.tv_to:
			initSingleChoice(view, Constant.DATA_ZA_CMP, EditPropertyAct.class);
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
			ToastUtil.showShort(this, "还没填写支出...");
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
		if (tvUse.getText() == null || tvUse.getText().length() == 0) {
			ToastUtil.showShort(this, "请选择用途...");
			return;
		}
		String info = "金额：" + etNum.getText().toString() + "\n类别：" + tvType.getText().toString();
		info += "\n来源：" + tvSrc.getText().toString()+ "\n用途：" + tvUse.getText().toString();
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
				values.put("froms", tvSrc.getText().toString());  
				values.put("use", tvUse.getText().toString());  
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

	@SuppressWarnings("deprecation")
	private void initType(View view){
		final ArrayList<Map<String, String>> plist = getParentType();
		//通过布局注入器，注入布局给View对象
        View myView = getLayoutInflater().inflate(R.layout.pop_imgtext_layout, null);
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
        pTypeAdapter = new PopMultTypeAdapter(AddOutcomeAct.this, plist);
        plv.setAdapter(pTypeAdapter);
        plv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	if (ptype.length() >0) {
            		ptype = "";
				}
            	pTypeAdapter.setSelectIndex(position);
            	pTypeAdapter.notifyDataSetChanged();
            	ptypeId = position;
            	ptype = plist.get(position).get("name");
            	final ArrayList<Map<String, String>> clist = getChildType(ptype);
            	cTypeAdapter = new PopMultTypeAdapter(AddOutcomeAct.this, clist);
                clv.setAdapter(cTypeAdapter);
                clv.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
						if (ctype.length() >0) {
							ctype = "";
						}
						cTypeAdapter.setSelectIndex(pos);
						cTypeAdapter.notifyDataSetChanged();
						ctypeId = pos;
						ctype = clist.get(pos).get("name");
						tvType.setText(ptype+"-"+ctype);
					}
                });
            }
        });
        if (plv != null) {
        	plv.performItemClick( plv.getChildAt(0), 0, pTypeAdapter.getItemId(0));
		}
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
        Cursor cursor = database.rawQuery("select name,img from PTYPE_TABLE where type=2", null); 
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
        Cursor cursor = database.rawQuery("select c.name,c.img from CTYPE_TABLE c,PTYPE_TABLE p where c.type=2 and p.id = c.ptype and p.name = ?", new String[]{ptype}); 
        while(cursor.moveToNext()){
        	Map<String, String> map = new HashMap<String, String>();
        	map.put("img", cursor.getString(cursor.getColumnIndex("img")));
        	map.put("name", cursor.getString(cursor.getColumnIndex("name")));
        	list.add(map);
        }
        return list;
	}

}
