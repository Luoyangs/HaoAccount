package com.haoxue.haoaccount.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.adapter.NoteMgrAdapter;
import com.haoxue.haoaccount.base.AssetDBManager;
import com.haoxue.haoaccount.base.Constant;
import com.haoxue.haoaccount.base.ShareDataHelper;
import com.haoxue.haoaccount.base.Constant.DB;
import com.haoxue.haoaccount.view.CuTipDialog;
import com.haoxue.haoaccount.view.swipemenu.SwipeMenu;
import com.haoxue.haoaccount.view.swipemenu.SwipeMenuCreator;
import com.haoxue.haoaccount.view.swipemenu.SwipeMenuItem;
import com.haoxue.haoaccount.view.swipemenu.SwipeMenuListView;
import com.haoxue.haoaccount.view.swipemenu.SwipeMenuListView.OnMenuItemClickListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

/**
 * 说明：
 * 作者：Luoyangs
 * 时间：2015-11-15
 */
@ContentView(R.layout.act_notetypemgr_layout)
public class NoteMgrAct extends Activity{

	@ViewInject(R.id.lvMain)
	private SwipeMenuListView lvMain;
	private NoteMgrAdapter adapter;
    private List<Map<String, String>> list;
    private SQLiteDatabase database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		database = new AssetDBManager().openDatabase(this);
		initData();
		adapter = new NoteMgrAdapter(this, list);  
        lvMain.setAdapter(adapter); 
        SwipeMenuCreator creator = new SwipeMenuCreator() {

    		@Override
    		public void create(SwipeMenu menu) {
    			SwipeMenuItem editItem = new SwipeMenuItem(getApplicationContext());
    			editItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,0xCE)));
				editItem.setWidth(dp2px(90));
				editItem.setIcon(R.drawable.edit);
				menu.addMenuItem(editItem);
				SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,0x3F, 0x25)));
				deleteItem.setWidth(dp2px(90));
				deleteItem.setIcon(R.drawable.delete);
				menu.addMenuItem(deleteItem);
    		}
    	};
        lvMain.setMenuCreator(creator);
        lvMain.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public void onMenuItemClick(final int position, SwipeMenu menu, int index) {
				switch (index) {
				case 0:
					Intent intent = new Intent(NoteMgrAct.this,AddNoteTypeAct.class);
					intent.putExtra("type", "edit");
					intent.putExtra("id", list.get(position).get("id"));
					intent.putExtra("title", list.get(position).get("title"));
					startActivity(intent);
					break;
				case 1:
					new CuTipDialog.Builder(NoteMgrAct.this)
						.setMessage("删除类别会删除其下的记事!")
						.setPositiveButton(new DialogInterface.OnClickListener(){
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss(); 
								//删除记事
								database.delete(DB.NOTE_TABLE_NAME, "type = ?", new String[]{list.get(position).get("id")});
								//删除类别
								database.delete(DB.NOTE_TYPE_TABLE_NAME, "id = ?", new String[]{list.get(position).get("id")});
								list.remove(position);
					            adapter.notifyDataSetChanged();
							}
						}).setNegativeButton(new DialogInterface.OnClickListener(){
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss(); 
							}
						}).create();
					break;
				}
			}
		});
	}
	
	private void initData(){
		//判断是否存在记录
		list = new ArrayList<Map<String,String>>();
		Map<String, String> map = null;
		Cursor cursor = database.rawQuery(Constant.DB.HAS_NOTE_TYPE, null); 
		if (cursor.moveToFirst() && cursor.getInt(0) == 0) {
			return;
		}else{
			//取出数据
			Cursor cursor2 = database.rawQuery(Constant.DB.GET_NOTE_TYPE, new String[]{String.valueOf(ShareDataHelper.getInstance(NoteMgrAct.this).getUserId())}); 
			while(cursor2.moveToNext()){
				map = new HashMap<String, String>();
		        map.put("id", ""+cursor2.getInt(cursor2.getColumnIndex("id")));
		        map.put("title", ""+cursor2.getString(cursor2.getColumnIndex("title")));
		        list.add(map);
			}
		}
	}
	
	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,getResources().getDisplayMetrics());
	}
	
	@OnClick(R.id.titilbar_left)
	public void onBack(View view){
		this.finish();
		overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
	}
	
	@OnClick(R.id.titilbar_right)
	public void onPost(View view){
		Intent intent = new Intent(NoteMgrAct.this,AddNoteTypeAct.class);
		intent.putExtra("type", "addtype");
		startActivity(intent);
	}
}
