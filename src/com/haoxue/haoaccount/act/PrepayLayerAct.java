package com.haoxue.haoaccount.act;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.adapter.ListViewAdapter3;
import com.haoxue.haoaccount.base.AssetDBManager;
import com.haoxue.haoaccount.base.Constant;
import com.haoxue.haoaccount.base.ShareDataHelper;
import com.haoxue.haoaccount.util.FileUtil;
import com.haoxue.haoaccount.util.ToastUtil;
import com.haoxue.haoaccount.view.CuListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 说明:二级预算
 * 作者:Luoyangs
 * 时间:2015-10-8
 */
@ContentView(R.layout.act_prepay_layer_layout)
public class PrepayLayerAct extends Activity {

	@ViewInject(R.id.img)
	private ImageView img;
	@ViewInject(R.id.type)
	private TextView ptype;
	@ViewInject(R.id.num)
	private ProgressBar pbar;
	@ViewInject(R.id.pay)
	private TextView pay;
	@ViewInject(R.id.pre)
	private TextView pre;
	@ViewInject(R.id.list)
	private CuListView listView;
	
	//键盘
	private TextView keyval;
	private Button btn0,btn1,btn2,btn3,btn4,btn5,
		btn6,btn7,btn8,btn9,btndot,btndel,btncl,btnok;
	private boolean isDecimal = false;
	private PopupWindow pw;
	
	private SQLiteDatabase database;
	private int pid;//父类Id
	private float presum = 0;//当前页的预算值之和
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		//初始化操作
		Intent intent = getIntent();
		img.setImageDrawable(FileUtil.getDrawableByName(this,intent.getStringExtra("img")));
		pid = Integer.parseInt(intent.getStringExtra("pid"));
		ptype.setText(intent.getStringExtra("name"));
		pay.setText("已用 "+ new DecimalFormat("0.00").format(Float.parseFloat(intent.getStringExtra("payout"))));
		pre.setText("预算 "+ new DecimalFormat("0.00").format(Float.parseFloat(intent.getStringExtra("num"))));
		//初始化预算之和
		presum += Float.parseFloat(intent.getStringExtra("num"));
		pbar.setMax((int)Float.parseFloat(intent.getStringExtra("num")));
		pbar.setProgress((int)Float.parseFloat(intent.getStringExtra("payout")));
        if (((int)Float.parseFloat(intent.getStringExtra("payout"))) >0 || ((int)Float.parseFloat(intent.getStringExtra("num"))) >0) {
        	pbar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_color));
		}else{
			pbar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_base));
		}
		//初始化数据集
		database = new AssetDBManager().openDatabase(this);
		listView.setAdapter(new ListViewAdapter3(PrepayLayerAct.this, getChildType()));
		listView.setOnItemClickListener(new MyOnItemClickListener());
	}
	
	private class MyOnItemClickListener implements OnItemClickListener{

		@SuppressWarnings("deprecation")
		@Override
		public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
			final TextView cprepay = (TextView)view.findViewById(R.id.pre);
	        final ProgressBar cbar = (ProgressBar)view.findViewById(R.id.num);
			final float itempre = Float.parseFloat(cprepay.getText().toString().substring(2));
			//通过布局注入器，注入布局给View对象
	        View keyboardView = getLayoutInflater().inflate(R.layout.pop_keyboard, null);
	        //初始化
	        isDecimal = false;
	        keyval = (TextView) keyboardView.findViewById(R.id.keyval);
	        btn0 = (Button) keyboardView.findViewById(R.id.keyboard_btn0);  
	        btn1 = (Button) keyboardView.findViewById(R.id.keyboard_btn1);  
	        btn2 = (Button) keyboardView.findViewById(R.id.keyboard_btn2);  
	        btn3 = (Button) keyboardView.findViewById(R.id.keyboard_btn3);  
	        btn4 = (Button) keyboardView.findViewById(R.id.keyboard_btn4);  
	        btn5 = (Button) keyboardView.findViewById(R.id.keyboard_btn5);  
	        btn6 = (Button) keyboardView.findViewById(R.id.keyboard_btn6);  
	        btn7 = (Button) keyboardView.findViewById(R.id.keyboard_btn7);  
	        btn8 = (Button) keyboardView.findViewById(R.id.keyboard_btn8);  
	        btn9 = (Button) keyboardView.findViewById(R.id.keyboard_btn9);  
	        btndot = (Button) keyboardView.findViewById(R.id.keyboard_btndot); 
	        btndel = (Button) keyboardView.findViewById(R.id.keyboard_btndel);  
	        btncl = (Button) keyboardView.findViewById(R.id.keyboard_btnclr);  
	        btnok = (Button) keyboardView.findViewById(R.id.keyboard_btnok); 
	        btn0.setOnClickListener(new KeyBoardClickListener());
	        btn1.setOnClickListener(new KeyBoardClickListener());
	        btn2.setOnClickListener(new KeyBoardClickListener());
	        btn3.setOnClickListener(new KeyBoardClickListener());
	        btn4.setOnClickListener(new KeyBoardClickListener());
	        btn5.setOnClickListener(new KeyBoardClickListener());
	        btn6.setOnClickListener(new KeyBoardClickListener());
	        btn7.setOnClickListener(new KeyBoardClickListener());
	        btn8.setOnClickListener(new KeyBoardClickListener());
	        btn9.setOnClickListener(new KeyBoardClickListener());
	        btndot.setOnClickListener(new KeyBoardClickListener());
	        btndel.setOnClickListener(new KeyBoardClickListener());
	        btncl.setOnClickListener(new KeyBoardClickListener());
	        btnok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String oldStr = keyval.getText().toString().trim();
					if ("0.".equals(oldStr)) {
						oldStr = "0.00";
					}else if ("0".equals(oldStr)) {
						oldStr = "0.00";
					}else{
						//大于0  更新自己
				        cprepay.setText("预算 "+ new DecimalFormat("0.00").format(Float.parseFloat(oldStr)));
				        cbar.setMax((int)Float.parseFloat(oldStr));
				        cbar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_color));
						//更新总预算
				        presum += Float.parseFloat(oldStr);
				        presum -= itempre;//减去当前项之前的设置值
						pre.setText("预算 "+ new DecimalFormat("0.00").format(presum));
						pbar.setMax((int)presum);
						pbar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_color));
						//插入数据库
						//判断是否存在当前子项的记录
						//判断是否存在记录
						String cid = getChildType().get(position).get("cid");
			        	Cursor cursor = database.rawQuery(Constant.DB.HAS_PREPAY_THREE, new String[]{cid}); 
			        	if (cursor.moveToFirst() && cursor.getInt(0) == 0) {
			        		//不存在时，插入一条新的记录
			        		ContentValues values = new ContentValues();  
			        		String Idstr = ShareDataHelper.getInstance(PrepayLayerAct.this).getUser("userId");
			        		int userId = ((Idstr == "" || Idstr == null) ? -1 : Integer.parseInt(Idstr));
			        		Calendar calendar = Calendar.getInstance();    
			        		values.put("UserId", userId);  
			        		values.put("num", Float.parseFloat(oldStr)); 
			        		values.put("type", 3);  
			        		values.put("ptype", pid); 
			        		values.put("ctype", Integer.parseInt(cid));  
			        		values.put("year", calendar.get(Calendar.YEAR)); 
			        		values.put("month", calendar.get(Calendar.MONTH)+1); 
			        		database.insert(Constant.DB.PREPAY_TABLE_NAME, "id", values);
			        	}else{
							//已经存在，修改当前预算值
			        		database.execSQL(Constant.DB.SET_PREPAY_THREE_NUM, new String[]{oldStr,cid});
						}
			        	//更新父类记录
			        	//判断是否存在记录
			        	Cursor cursor2 = database.rawQuery(Constant.DB.HAS_PREPAY_TWO, new String[]{String.valueOf(pid)}); 
			        	if (cursor2.moveToFirst() && cursor2.getInt(0) == 0) {
			        		//不存在时，插入一条新的记录
			        		ContentValues values = new ContentValues();  
			        		String Idstr = ShareDataHelper.getInstance(PrepayLayerAct.this).getUser("userId");
			        		int userId = ((Idstr == "" || Idstr == null) ? -1 : Integer.parseInt(Idstr));
			        		Calendar calendar = Calendar.getInstance();    
			        		values.put("UserId", userId);  
			        		values.put("num", presum); 
			        		values.put("type", 3);  
			        		values.put("ptype", pid); 
			        		values.put("ctype", 0);  //表示总类别
			        		values.put("year", calendar.get(Calendar.YEAR)); 
			        		values.put("month", calendar.get(Calendar.MONTH)+1); 
			        		database.insert(Constant.DB.PREPAY_TABLE_NAME, "id", values);
			        	}else{
			        		//已经存在，修改父类预算值
			        		database.execSQL(Constant.DB.SET_PREPAY_TWO_NUM, new String[]{String.valueOf(presum),String.valueOf(pid)});
			        	}
					}
					pw.dismiss();
				}
			});
	        //通过view 和宽·高，构造PopopWindow
	        pw = new PopupWindow(keyboardView, LayoutParams.MATCH_PARENT, 752, true);
	        //设置焦点为可点击
	        pw.setFocusable(true);//可以试试设为false的结果
	        pw.setOutsideTouchable(true);  
	        pw.setBackgroundDrawable(new BitmapDrawable());
	        //将window视图显示在最下面
	        pw.showAtLocation(findViewById(R.id.main),Gravity.BOTTOM, 0, 0);
		}
		
	}
	
	//获取数据
	private ArrayList<Map<String, String>> getChildType() {
		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Cursor cursor = database.rawQuery("select c.id,c.name,c.img from CTYPE_TABLE c,PTYPE_TABLE p where p.type=3 and p.id = c.ptype and p.name = ?", 
        		new String[]{getIntent().getStringExtra("name")}); 
        while(cursor.moveToNext()){
        	Map<String, String> map = new HashMap<String, String>();
        	map.put("cid", ""+cursor.getInt(cursor.getColumnIndex("id")));
        	map.put("img", cursor.getString(cursor.getColumnIndex("img")));
        	map.put("name", cursor.getString(cursor.getColumnIndex("name")));
        	//判断是否存在记录
        	Cursor cursor2 = database.rawQuery(Constant.DB.HAS_PREPAY_THREE, new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex("id")))}); 
        	if (cursor2.moveToFirst() && cursor2.getInt(0) == 0) {
            	map.put("payout", "0");
        		map.put("num", "0");
			}else{
				//取出数据
				Cursor cursor3 = database.rawQuery(Constant.DB.GET_PREPAY_THREE, new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex("id")))}); 
				while(cursor3.moveToNext()){
	        		map.put("payout", ""+cursor3.getFloat(cursor3.getColumnIndex("pay")));
	        		map.put("num", ""+cursor3.getFloat(cursor3.getColumnIndex("num")));
				}
			}
        	list.add(map);
        }
        return list;
    }
	
	@OnClick(R.id.titilbar_left)
	public void onBack(View view){
		this.finish();
		overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
	}
	
	private class KeyBoardClickListener implements OnClickListener{
		
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.keyboard_btn0:
				setValue(((Button)view).getText().toString());
				break;
			case R.id.keyboard_btn1:
				setValue(((Button)view).getText().toString());
				break;
			case R.id.keyboard_btn2:
				setValue(((Button)view).getText().toString());
				break;
			case R.id.keyboard_btn3:
				setValue(((Button)view).getText().toString());
				break;
			case R.id.keyboard_btn4:
				setValue(((Button)view).getText().toString());
				break;
			case R.id.keyboard_btn5:
				setValue(((Button)view).getText().toString());
				break;
			case R.id.keyboard_btn6:
				setValue(((Button)view).getText().toString());
				break;
			case R.id.keyboard_btn7:
				setValue(((Button)view).getText().toString());
				break;
			case R.id.keyboard_btn8:
				setValue(((Button)view).getText().toString());
				break;
			case R.id.keyboard_btn9:
				setValue(((Button)view).getText().toString());
				break;
			case R.id.keyboard_btndot:
				String oldStr = keyval.getText().toString();
				if (!isDecimal && Double.parseDouble(oldStr) == 0) {
					keyval.setText("0.");
					isDecimal = true;
				}else if(isDecimal){//已经是小数
				}else{
					oldStr += ".";
					keyval.setText(oldStr);
					isDecimal = true;
				}
				break;
			case R.id.keyboard_btndel:
				delValue();
				break;
			case R.id.keyboard_btnclr:
				keyval.setText("0");
				break;
			}
		}
	}
	
	private void setValue(String value){
		String oldStr = keyval.getText().toString().trim();
		if ("0.".equals(oldStr)) {
			oldStr += value;
			keyval.setText(oldStr);
		}else if (Double.parseDouble(oldStr) == 0) {
			keyval.setText(value);
		}else{
			oldStr += value;
			keyval.setText(oldStr);
		}
	}
	
	private void delValue(){
		String oldStr = keyval.getText().toString().trim();
		if(oldStr.length() > 0) {
			keyval.setText(oldStr.subSequence(0, oldStr.length()-1));
		}
		if (oldStr.length() == 0) {
			keyval.setText("0");
		}
	}

}
