package com.haoxue.haoaccount.act;

import java.util.ArrayList;
import java.util.List;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.base.AssetDBManager;
import com.haoxue.haoaccount.base.SQLiteHelper;
import com.haoxue.haoaccount.util.FileUtil;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 说明:
 * 作者:Luoyangs
 * 时间:2015-10-6
 */
public class TestAct extends Activity {

	private Button btnButton;
	private TextView textView;
	private ImageView imageView;
	
	private SQLiteHelper helper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_test_layout);
		
		btnButton = (Button) findViewById(R.id.button1);
		imageView = (ImageView) findViewById(R.id.imageView1);
		textView = (TextView) findViewById(R.id.textView1);
		btnButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				set0();
				set00();
			}
		});
		
	}
	
	private void set00(){
		imageView.setImageDrawable(FileUtil.getDrawableByName(this,"i0"));
	}
	
	private void set0(){
		SQLiteDatabase database = new AssetDBManager().openDatabase(this);
		String string = "";
		Cursor cursor = database.rawQuery("select * from INCOME_TABLE", null);
		while (cursor.moveToNext()) {
			String id = String.valueOf(cursor.getInt(cursor.getColumnIndex("id")));
			String name = cursor.getString(cursor.getColumnIndex("num"));
			string = string +id+"\t"+ name + "\n";
		}
		textView.setText(string);
	}

	private List<String> getList211(){
		List<String> list = new ArrayList<String>();
		list.add("其他支出");
		list.add("意外丢失");
		list.add("烂账损失");
		return list;
	}
	private List<String> getList210(){
		List<String> list = new ArrayList<String>();
		list.add("银行手续");
		list.add("投资亏损");
		list.add("按揭还款");
		list.add("消费税收");
		list.add("利息支出");
		list.add("赔偿罚款");
		return list;
	}
	private List<String> getList29(){
		List<String> list = new ArrayList<String>();
		list.add("药品费");
		list.add("保健费");
		list.add("美容费");
		list.add("医疗费");
		return list;
	}
	private List<String> getList28(){
		List<String> list = new ArrayList<String>();
		list.add("送礼请客");
		list.add("孝敬家长");
		list.add("换人钱物");
		list.add("慈善捐助");
		return list;
	}
	private List<String> getList27(){
		List<String> list = new ArrayList<String>();
		list.add("书报杂志");
		list.add("培训教学");
		list.add("数码装备");
		return list;
	}
	private List<String> getList26(){
		List<String> list = new ArrayList<String>();
		list.add("运动健身");
		list.add("腐败聚会");
		list.add("休闲玩乐");
		list.add("宠物宝贝");
		list.add("旅游度假");
		return list;
	}
	private List<String> getList25(){
		List<String> list = new ArrayList<String>();
		list.add("座机费");
		list.add("手机费");
		list.add("上网费");
		list.add("邮寄费");
		return list;
	}
	private List<String> getList24(){
		List<String> list = new ArrayList<String>();
		list.add("公共交通");
		list.add("打出租车");
		list.add("私家车");
		return list;
	}
	private List<String> getList23(){
		List<String> list = new ArrayList<String>();
		list.add("日常用品");
		list.add("水电煤气");
		list.add("房租");
		list.add("业务管理");
		list.add("维修保养");
		return list;
	}
	private List<String> getList22(){
		List<String> list = new ArrayList<String>();
		list.add("早午晚餐");
		list.add("烟酒茶水");
		list.add("水果零食");
		return list;
	}
	private List<String> getList21(){
		List<String> list = new ArrayList<String>();
		list.add("衣服裤子");
		list.add("鞋帽包包");
		list.add("化妆饰品");
		return list;
	}
	
	private List<String> getList2(){
		List<String> list = new ArrayList<String>();
		list.add("衣服饰品");
		list.add("食品酒水");
		list.add("居家业务");
		list.add("行业交通");
		list.add("交流通讯");
		list.add("休闲娱乐");
		list.add("学习进修");
		list.add("人情往来");
		list.add("医疗保健");
		list.add("经融保险");
		list.add("其他杂项");
		return list;
	}
	
	private List<String> getList12(){
		List<String> list = new ArrayList<String>();
		list.add("礼金收入");
		list.add("中奖收入");
		list.add("意外来钱");
		list.add("经营所得");
		return list;
	}
	
	private List<String> getList11(){
		List<String> list = new ArrayList<String>();
		list.add("工资收入");
		list.add("利息收入");
		list.add("加班收入");
		list.add("奖金收入");
		list.add("投资收入");
		list.add("兼职收入");
		return list;
	}
	
	private List<String> getList1(){
		List<String> list = new ArrayList<String>();
		list.add("职业收入");
		list.add("其他收入");
		return list;
	}
	
	private List<String> getList(){
		List<String> list = new ArrayList<String>();
		list.add("收入");
		list.add("支出");
		list.add("预算");
		list.add("借贷");
		list.add("班费");
		return list;
	}
	
}
