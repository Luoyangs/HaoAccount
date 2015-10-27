package com.haoxue.haoaccount.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.base.ShareDataHelper;
import com.haoxue.haoaccount.util.ToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * 说明：设置首页的图片
 * 作者：Luoyangs
 * 时间：2015-10-27
 */
@ContentView(R.layout.act_setskin_layout)
public class SetSkinAct extends Activity{

	@ViewInject(R.id.gridview)
	private GridView gridView;
	private MyBaseAdapter adapter;
	private List<Map<String, Object>> list;
	private int imgId = R.drawable.bg1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		
		loadData();
		adapter = new MyBaseAdapter(list, this);
		gridView.setAdapter(adapter);
		ToastUtil.showLong(SetSkinAct.this, "点击可以查看大图，长按可以选择~");
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(SetSkinAct.this,ShowImageAct.class);
				intent.putExtra("imgId", Integer.parseInt(list.get(arg2).get("imgId").toString()));
				startActivity(intent);
			}
		});
		gridView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				imgId = Integer.parseInt(list.get(arg2).get("imgId").toString());
				adapter.setSeclection(arg2);
				adapter.notifyDataSetChanged();
				return false;
			}
		});
		
	}
	
	@SuppressLint("Recycle")
	private void loadData(){
		list = new ArrayList<Map<String, Object>>();
		TypedArray ar = this.getResources().obtainTypedArray(R.array.bg_images);
		for (int i = 0; i < ar.length(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("imgId", ar.getResourceId(i, 0));
			int oldBg = ShareDataHelper.getInstance(this).getBgImage();
			if (oldBg == ar.getResourceId(i, 0)) {
				map.put("check", true);
			}else{
				map.put("check", false);
			}
			list.add(map);
		}
	}
	
	@OnClick(R.id.titilbar_left)
	public void onClick(View view){
		this.finishPage();
	}
	
	@OnClick(R.id.titilbar_right)
	public void onSure(View view){
		ShareDataHelper.getInstance(this).saveBgImage(imgId);
		this.finishPage();
	}
	
	private void finishPage(){
		this.finish();
		overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
	}
	
	private class MyBaseAdapter extends BaseAdapter{
		
		private List<Map<String, Object>> list;
		private LayoutInflater inflater;
		private int clickTemp = -1;
		
		public MyBaseAdapter(List<Map<String, Object>> list,Context context){
			this.list = list;
			this.inflater = LayoutInflater.from(context);
		}
		
		public void setSeclection(int position) {
			clickTemp = position;
		}
		
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = inflater.inflate(R.layout.act_setskin_layout_item, null, false);
			ImageView image = (ImageView) view.findViewById(R.id.item_image);
			final ImageView select = (ImageView) view.findViewById(R.id.item_select);
			Map<String, Object> map = list.get(position);
			final int imgId = Integer.parseInt(map.get("imgId").toString());
			image.setImageResource(imgId);
			if (clickTemp == position) {
				select.setImageResource(R.drawable.pictures_selected);
			} else if (Boolean.parseBoolean(map.get("check").toString())) {
				select.setImageResource(R.drawable.pictures_selected);
			} else {
				select.setImageResource(R.drawable.picture_unselected);
			}
			return view;
		}
	}
	
}
