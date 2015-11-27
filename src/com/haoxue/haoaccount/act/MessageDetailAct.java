package com.haoxue.haoaccount.act;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.base.AssetDBManager;
import com.haoxue.haoaccount.base.Constant;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * 说明：消息详细页
 * 作者：Luoyangs
 * 时间：2015-10-31
 */
@ContentView(R.layout.act_messagedetail_layout)
public class MessageDetailAct extends Activity {

	@ViewInject(R.id.titilbar_title)
	private TextView titilbar_title;
	@ViewInject(R.id.tvtitle)
	private TextView tvtitle;
	@ViewInject(R.id.tvtype)
	private TextView tvtype;
	@ViewInject(R.id.tvtime)
	private TextView tvtime;
	@ViewInject(R.id.tvcontent)
	private TextView tvcontent;
	private SQLiteDatabase database;
	private String msgId = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		database = new AssetDBManager().openDatabase(this);
		msgId = getIntent().getStringExtra("msgId");
		Cursor cursor = database.rawQuery(Constant.DB.GET_MSG_BY_ID, new String[]{msgId});
		while(cursor.moveToNext()){
			tvtitle.setText(cursor.getString(cursor.getColumnIndex("title")));
			int type = cursor.getInt(cursor.getColumnIndex("type"));
			tvtype.setText(type == 0?"系统消息":"用户消息");
			tvtype.setTextColor(type == 0?Color.RED:Color.GREEN);
			tvtime.setText(cursor.getString(cursor.getColumnIndex("createTime")));
			tvcontent.setText("\t\t"+ToDBC(cursor.getString(cursor.getColumnIndex("content"))));
			titilbar_title.setText(type == 0?"系统消息":"用户消息");
		}
		//设置为已读状态
		ContentValues values = new ContentValues();
		values.put("state", 1);
		database.update(Constant.DB.MSG_TABLE_NAME, values, "id = ?", new String[]{msgId});
	}
	
	@OnClick(R.id.titilbar_left)
	public void onClick(View view){
		this.finish();
		overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
	}
	
	/**
	 * 半角转换为全角
	 */
	public String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return stringFilter(new String(c));
	}
	
	public String stringFilter(String str) {  
        str = str.replaceAll("【", "[").replaceAll("】", "]")  
                .replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号  
        String regEx = "[『』]"; // 清除掉特殊字符  
        Pattern p = Pattern.compile(regEx);  
        Matcher m = p.matcher(str);  
        return m.replaceAll("").trim();  
    }  
}
