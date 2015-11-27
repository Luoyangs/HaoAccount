package com.haoxue.haoaccount.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.act.frag.MessageFragment;
import com.haoxue.haoaccount.base.AssetDBManager;
import com.haoxue.haoaccount.base.Constant;
import com.haoxue.haoaccount.view.SegmentControl;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 说明：消息列表
 * 作者：Luoyangs
 * 时间：2015-10-28
 */
@ContentView(R.layout.act_message_layout)
public class MessageAct extends FragmentActivity implements OnCheckedChangeListener{

	@ViewInject(R.id.segment)
	private SegmentControl segment;
	@ViewInject(R.id.content_frame)
	private FrameLayout content_frame;
	@ViewInject(R.id.segmentSys)
	private RadioButton segmentSys;
	private Fragment curFragment;// 当前面板
	private Fragment sysfragment,userfragment;
	private SQLiteDatabase database;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		database = new AssetDBManager().openDatabase(this);
		segment.setOnCheckedChangeListener(this);
		if (savedInstanceState == null) {
			sysfragment = new MessageFragment(getLists(0));
			userfragment = new MessageFragment(getLists(1));
			switchToTargetFragment(sysfragment);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		((MessageFragment) sysfragment).setList(getLists(0));
		((MessageFragment) userfragment).setList(getLists(1));
	}
	
	private List<HashMap<String, String>> getLists(int typeId){
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		Cursor cursor = database.rawQuery(Constant.DB.HAS_MSG_BY_TYPE, new String[]{String.valueOf(typeId)}); 
    	if (cursor.moveToFirst() && cursor.getInt(0) == 0) {
    		list = null;
    	}else{
    		Cursor cursor2 = database.rawQuery(Constant.DB.GET_MSG_BY_TYPE, new String[]{String.valueOf(typeId)});
    		while(cursor2.moveToNext()){
    			HashMap<String,String> map = new HashMap<String, String>();
    			map.put("id", ""+cursor2.getInt(cursor2.getColumnIndex("id")));
    			map.put("title", cursor2.getString(cursor2.getColumnIndex("title")));
    			map.put("state", ""+cursor2.getInt(cursor2.getColumnIndex("state")));
    			list.add(map);
    		}
    	}
		return list;
	}
	
	@OnClick(R.id.titilbar_left)
	public void onClick(View view){
		this.finishPage();
	}
	
	private void finishPage(){
		this.finish();
		overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
	}
	
	public void switchToTargetFragment(Fragment target) {
		this.switchToTargetFragment(curFragment, target);
	}

	private void switchToTargetFragment(Fragment from, Fragment to) {
		if (curFragment != to) {
			if (from == null) {
				// 第一次添加页面
				getSupportFragmentManager()
						.beginTransaction()
						.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
						.add(R.id.content_frame, to)
						.commit();
			} else {
				// 判断是否添加过
				if (!to.isAdded()) {
					getSupportFragmentManager()
							.beginTransaction()
							.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
							.hide(from)
							.add(R.id.content_frame, to).commit();
				} else {
					getSupportFragmentManager()
							.beginTransaction()
							.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
							.hide(from)
							.show(to)
							.commit();
				}
			}
			curFragment = to;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.segmentSys:
			switchToTargetFragment(sysfragment);
			break;
		case R.id.segmentUser:
			switchToTargetFragment(userfragment);
			break;
		}
	}
}
