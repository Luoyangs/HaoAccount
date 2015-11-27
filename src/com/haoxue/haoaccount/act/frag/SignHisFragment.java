package com.haoxue.haoaccount.act.frag;

import java.util.HashMap;
import java.util.List;

import com.haarman.listviewanimations.swinginadapters.prepared.SwingLeftInAnimationAdapter;
import com.haoxue.haoaccount.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 说明：签到历史
 * 作者：Luoyangs
 * 时间：2015-11-5
 */
@SuppressLint("ValidFragment")
public class SignHisFragment extends Fragment {

	private List<HashMap<String, String>> list;
	private MyAdapter myAdapter;
	
	public SignHisFragment(List<HashMap<String, String>> list){
		this.list = list;
		myAdapter = new MyAdapter();
	}
	
	public void setDataLists(List<HashMap<String, String>> list){
		this.list = list;
		myAdapter.notifyDataSetChanged();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (list == null || list.size() == 0) {
			View emptyView = inflater.inflate(R.layout.no_data_layout, null);
			return emptyView;
		}
		View view = inflater.inflate(R.layout.fragment_message_layout, null, false);
		ListView listView = (ListView) view.findViewById(R.id.listView);
		SwingLeftInAnimationAdapter swingLeftInAnimationAdapter = new SwingLeftInAnimationAdapter(myAdapter);
		swingLeftInAnimationAdapter.setListView(listView);
		listView.setAdapter(swingLeftInAnimationAdapter);
		return view;
	}
	
	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate( R.layout.item_signhistory_layout, null);
			}
			TextView favName = (TextView) convertView.findViewById(R.id.favName);//签到积分
			TextView dlName = (TextView) convertView.findViewById(R.id.dlName);//总分
			TextView dlName2 = (TextView) convertView.findViewById(R.id.dlName2);//目标差距
			TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);//时间
			favName.setText(list.get(position).get("curfav"));
			dlName.setText(list.get(position).get("sumfav"));
			dlName2.setText(list.get(position).get("subfav"));
			tvTime.setText(list.get(position).get("time"));
			return convertView;
		}
	}
}
