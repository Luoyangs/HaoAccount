package com.haoxue.haoaccount.act.frag;

import java.util.HashMap;
import java.util.List;

import com.haarman.listviewanimations.swinginadapters.prepared.SwingLeftInAnimationAdapter;
import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.act.MessageDetailAct;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
	private MyAdapter myAdapter;
	
	public MessageFragment(List<HashMap<String, String>> list){
		this.list = list;
		myAdapter = new MyAdapter();
	}
	
	public void setList(List<HashMap<String, String>> list){
		this.list = list;
		myAdapter.notifyDataSetChanged();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (list == null || list.size() == 0 ) {
			View emptyView = inflater.inflate(R.layout.no_data_layout, null);
			return emptyView;
		}
		View view = inflater.inflate(R.layout.fragment_message_layout, null, false);
		ListView listView = (ListView) view.findViewById(R.id.listView);
		SwingLeftInAnimationAdapter swingLeftInAnimationAdapter = new SwingLeftInAnimationAdapter(myAdapter);
		swingLeftInAnimationAdapter.setListView(listView);
		listView.setAdapter(swingLeftInAnimationAdapter);
		listView.setOnItemClickListener(this);
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
		Intent intent = new Intent(getActivity(),MessageDetailAct.class);
		intent.putExtra("msgId", list.get(position).get("id"));
		getActivity().startActivity(intent);
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
			ImageView img = (ImageView) convertView.findViewById(R.id.img);
			img.setVisibility(Integer.parseInt(list.get(position).get("state"))==0?View.VISIBLE:View.INVISIBLE);
			title.setText(list.get(position).get("title"));
			return convertView;
		}

	}
}
