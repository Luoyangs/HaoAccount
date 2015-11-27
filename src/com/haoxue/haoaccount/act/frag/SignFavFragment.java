package com.haoxue.haoaccount.act.frag;

import java.util.HashMap;
import java.util.List;

import com.haarman.listviewanimations.swinginadapters.prepared.SwingLeftInAnimationAdapter;
import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.base.Constant;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 说明：签到规则 
 * 作者：Luoyangs 
 * 时间：2015-11-4
 */
@SuppressLint("ValidFragment")
public class SignFavFragment extends Fragment implements OnItemClickListener {

	private List<HashMap<String, String>> list;
	private MyAdapter myAdapter;

	public SignFavFragment(List<HashMap<String, String>> list) {
		this.list = list;
		myAdapter = new MyAdapter();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (list == null || list.size() == 0) {
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {

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
				convertView = getActivity().getLayoutInflater().inflate( R.layout.item_signlevel_layout, null);
			}
			ImageView img = (ImageView) convertView.findViewById(R.id.img0);
			TextView favName = (TextView) convertView.findViewById(R.id.favName);
			TextView dlName = (TextView) convertView.findViewById(R.id.dlName);
			img.setImageResource(FAVIMGS[Integer.parseInt(list.get(position).get("name")) -1]);
			favName.setText(list.get(position).get("name"));
			favName.setTextColor(Color.parseColor(Constant.FAVTEXTCOLOR[Integer.parseInt(list.get(position).get("name")) -1]));
			dlName.setText(list.get(position).get("deadline"));
			return convertView;
		}

		private final int[] FAVIMGS = { R.drawable.aw0, R.drawable.aw0,
				R.drawable.aw1, R.drawable.aw1, R.drawable.aw2, R.drawable.aw2,
				R.drawable.aw3, R.drawable.aw3, R.drawable.aw4, R.drawable.aw4,
				R.drawable.aw5, R.drawable.aw5, R.drawable.aw6, R.drawable.aw6,
				R.drawable.aw7, R.drawable.aw7, R.drawable.aw8, R.drawable.aw8,
				R.drawable.aw9, R.drawable.aw9, R.drawable.aw10, R.drawable.aw10, 
				R.drawable.aw11,R.drawable.aw11,R.drawable.aw11, R.drawable.aw11, 
				R.drawable.aw12,R.drawable.aw12,R.drawable.aw12,R.drawable.aw12 };
	}

}
