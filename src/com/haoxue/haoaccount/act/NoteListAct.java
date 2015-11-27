package com.haoxue.haoaccount.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.adapter.NoteListAdapter;
import com.haoxue.haoaccount.adapter.NoteTypeAdapter;
import com.haoxue.haoaccount.base.AssetDBManager;
import com.haoxue.haoaccount.base.Constant;
import com.haoxue.haoaccount.base.Constant.DB;
import com.haoxue.haoaccount.base.ShareDataHelper;
import com.haoxue.haoaccount.view.CuTipDialog;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.PopupWindow;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 说明：笔记列表
 * 作者：Luoyangs
 * 时间：2015-11-14
 */
@ContentView(R.layout.act_notelist_layout)
public class NoteListAct extends Activity implements OnClickListener{

	@ViewInject(R.id.classify_mainlist)
	private ListView mainlist;
	@ViewInject(R.id.classify_morelist)
	private ListView morelist;
	private List<Map<String, String>> mainLists;//主表
	private List<Map<String, String>> moreLists;//次表
	private SQLiteDatabase database;
	private NoteTypeAdapter typeAdapter;
	private NoteListAdapter listAdapter;
	private boolean isFirstIn;
	private PopupWindow pw;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		//初始化数据集
		isFirstIn = true;
		database = new AssetDBManager().openDatabase(this);
		mainLists = new ArrayList<Map<String,String>>();
		moreLists = new ArrayList<Map<String,String>>();
		//添加事件
		mainlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				loadMoreList(mainLists.get(position).get("pid"));
				typeAdapter.setSelectItem(position);
				typeAdapter.notifyDataSetChanged();
			}
		});
		morelist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
				listAdapter.setSelectItem(position);
				listAdapter.notifyDataSetChanged();
			}
		});
		morelist.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, final View view, final int position, long arg3) {
				new CuTipDialog.Builder(NoteListAct.this)
					.setMessage("删除当前选中项么？")
					.setPositiveButton(new DialogInterface.OnClickListener(){
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss(); 
							deletePattern(view,position);
						}
					}).setNegativeButton(new DialogInterface.OnClickListener(){
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss(); 
						}
					}).create();
				return true;
			}
		});
	}
	
	private void deletePattern(final View view, final int position) {

        Animation.AnimationListener al = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
               database.delete(DB.NOTE_TABLE_NAME, "id = ?", new String[]{moreLists.get(position).get("id")});
               moreLists.remove(position);
               listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        };
        collapse(view, al);
    }

    private void collapse(final View view, Animation.AnimationListener al) {
        final int originHeight = view.getMeasuredHeight();

        Animation animation = new Animation() {
            
            @Override
            protected void applyTransformation(float interpolatedTime,Transformation t) {
            	if (interpolatedTime == 1.0f) {
                    view.setVisibility(View.GONE);
                } else {
                    view.getLayoutParams().height = originHeight - (int) (originHeight * interpolatedTime);
                    view.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        if (al != null) {
            animation.setAnimationListener(al);
        }
        animation.setDuration(300);
        view.startAnimation(animation);
    }
	
    @Override
    protected void onResume() {
    	//刷新适配器
    	loadMainList();
		if (mainLists != null && mainLists.size() >0) {
			loadMoreList(mainLists.get(0).get("pid"));
			isFirstIn = false;
		}
		typeAdapter = new NoteTypeAdapter(NoteListAct.this, mainLists);
		listAdapter = new NoteListAdapter(NoteListAct.this, moreLists);
		mainlist.setAdapter(typeAdapter);
		morelist.setAdapter(listAdapter);
    	super.onResume();
    }
    
	//加载类别表
	private void loadMainList(){
		//判断是否存在记录
		Map<String, String> map = null;
    	Cursor cursor = database.rawQuery(Constant.DB.HAS_NOTE_TYPE, null); 
    	if (cursor.moveToFirst() && cursor.getInt(0) == 0) {
    		mainLists = new ArrayList<Map<String,String>>();
		}else{
			//取出数据
			mainLists.clear();
			Cursor cursor2 = database.rawQuery(Constant.DB.GET_NOTE_TYPE, new String[]{String.valueOf(ShareDataHelper.getInstance(NoteListAct.this).getUserId())}); 
			while(cursor2.moveToNext()){
				map = new HashMap<String, String>();
        		map.put("pid", ""+cursor2.getInt(cursor2.getColumnIndex("id")));
        		map.put("ptitle", ""+cursor2.getString(cursor2.getColumnIndex("title")));
        		mainLists.add(map);
			}
		}
	}
	
	private void loadMoreList(String typeId){
		//判断是否存在记录
		Map<String, String> map = null;
		moreLists.clear();
		Cursor cursor = database.rawQuery(Constant.DB.GET_NOTE_BY_TYPE, new String[]{typeId});
		if (cursor.moveToFirst() && cursor.getCount() == 0) {
			moreLists = new ArrayList<Map<String,String>>();
		}else{
			//取出数据
			moreLists.clear();
			Cursor cursor2 = database.rawQuery(Constant.DB.GET_NOTE_BY_TYPE, new String[]{typeId}); 
			while(cursor2.moveToNext()){
				map = new HashMap<String, String>();
		        map.put("id", ""+cursor2.getInt(cursor2.getColumnIndex("id")));
		        map.put("title", ""+cursor2.getString(cursor2.getColumnIndex("title")));
		        String[] timestr = cursor2.getString(cursor2.getColumnIndex("updateTime")).split(" ");
    			map.put("updateTime", timestr[0]);
		        moreLists.add(map);
			}
			//显示数据
			if (!isFirstIn) {
				listAdapter.setDataLists(moreLists);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@OnClick(R.id.titilbar_right)
	public void onAddClick(View view){
		View menu = getLayoutInflater().inflate(R.layout.pop_layout4, null);
		menu.findViewById(R.id.btn_mp).setOnClickListener(this);
		menu.findViewById(R.id.btn_ap).setOnClickListener(this);
		menu.findViewById(R.id.btn_an).setOnClickListener(this);
		//通过view 和宽·高，构造PopopWindow
        pw = new PopupWindow(menu, 380, 652, true);
        //设置焦点为可点击
        pw.setFocusable(true);
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setOutsideTouchable(true);
        pw.showAsDropDown(view);
	}
	
	@OnClick(R.id.titilbar_left)
	public void onBack(View view){
		this.finish();
		overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_mp://管理分类
			startActivity(new Intent(NoteListAct.this,NoteMgrAct.class));
			break;
		case R.id.btn_ap://添加分类
			Intent intent = new Intent(NoteListAct.this,AddNoteTypeAct.class);
			intent.putExtra("type", "addtype");
			startActivity(intent);
			break;
		case R.id.btn_an://添加记事
			startActivity(new Intent(NoteListAct.this,AddNoteAct.class));
			break;
		}
		pw.dismiss();
	}
}
