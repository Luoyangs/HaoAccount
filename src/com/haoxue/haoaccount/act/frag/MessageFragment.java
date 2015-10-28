package com.haoxue.haoaccount.act.frag;

import java.util.HashMap;
import java.util.List;

import com.haarman.listviewanimations.swinginadapters.prepared.SwingLeftInAnimationAdapter;
import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.util.ToastUtil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 说明：
 * 作者：Luoyangs
 * 时间：2015-10-28
 */
@SuppressLint("ValidFragment")
public class MessageFragment extends Fragment implements OnItemClickListener{

	private List<HashMap<String, String>> list;
	
	public MessageFragment(List<HashMap<String, String>> list){
		this.list = list;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_message_layout, null, false);
		ListView listView = (ListView) view.findViewById(R.id.listView);
		MyAdapter myAdapter = new MyAdapter();
		SwingLeftInAnimationAdapter swingLeftInAnimationAdapter = new SwingLeftInAnimationAdapter(myAdapter);
		swingLeftInAnimationAdapter.setListView(listView);
		listView.setAdapter(swingLeftInAnimationAdapter);
		listView.setOnItemClickListener(this);
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		ToastUtil.showLong(getActivity(), ""+position);
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
				convertView = getActivity().getLayoutInflater().inflate(R.layout.lv_item4, null);
			}
			TextView title = (TextView) convertView.findViewById(R.id.text);
			title.setText(list.get(position).get("title"));
			return convertView;
		}

	}
}
