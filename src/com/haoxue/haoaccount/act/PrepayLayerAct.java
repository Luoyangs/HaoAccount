package com.haoxue.haoaccount.act;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.adapter.ListViewAdapter3;
import com.haoxue.haoaccount.base.AssetDBManager;
import com.haoxue.haoaccount.util.FileUtil;
import com.haoxue.haoaccount.util.ToastUtil;
import com.haoxue.haoaccount.view.CuListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
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
	
	private SQLiteDatabase database;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		//初始化操作
		Intent intent = getIntent();
		img.setImageDrawable(new FileUtil(this).getDrawableByName(intent.getStringExtra("img")));
		ptype.setText(intent.getStringExtra("name"));
		pay.setText("已用 "+ new DecimalFormat(".00").format(Float.parseFloat(intent.getStringExtra("payout"))));
		pre.setText("预算 "+ new DecimalFormat(".00").format(Float.parseFloat(intent.getStringExtra("num"))));
		pbar.setMax(Integer.parseInt(intent.getStringExtra("num")));
		pbar.setProgress(Integer.parseInt(intent.getStringExtra("payout")));
        if (Integer.parseInt(intent.getStringExtra("payout")) >0 || Integer.parseInt(intent.getStringExtra("num")) >0) {
        	pbar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_color));
		}else{
			pbar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_base));
		}
		//初始化数据集
		database = new AssetDBManager().openDatabase(this);
		listView.setAdapter(new ListViewAdapter3(PrepayLayerAct.this, getChildType()));
		ToastUtil.showShort(this, "size:"+getChildType().size());
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
			}
		});
	}
	
	private ArrayList<Map<String, String>> getChildType() {
		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Cursor cursor = database.rawQuery("select c.name,c.img from CTYPE_TABLE c,PTYPE_TABLE p where p.type=3 and p.id = c.ptype and p.name = ?", 
        		new String[]{getIntent().getStringExtra("name")}); 
        while(cursor.moveToNext()){
        	Map<String, String> map = new HashMap<String, String>();
        	map.put("img", cursor.getString(cursor.getColumnIndex("img")));
        	map.put("name", cursor.getString(cursor.getColumnIndex("name")));
        	map.put("payout", String.valueOf(0));
        	map.put("num", String.valueOf(100));
        	list.add(map);
        }
        return list;
    }
	
	@OnClick(R.id.titilbar_left)
	public void onBack(View view){
		this.finish();
		overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
	}
}
