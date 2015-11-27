package com.haoxue.haoaccount.act.frag;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.act.DayBalanceAct;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

/**
 * 说明：
 * 作者：Luoyangs
 * 时间：2015-11-15
 */
@SuppressLint("ValidFragment")
public class MCFragment extends Fragment{

	private RelativeLayout dayInfo;
	private RelativeLayout weekInfo;
	private RelativeLayout monthInfo;
	
	public MCFragment(){
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_maincontent_layout, null,false);
		dayInfo = (RelativeLayout) view.findViewById(R.id.day_info);
		weekInfo = (RelativeLayout) view.findViewById(R.id.week_info);
		monthInfo = (RelativeLayout) view.findViewById(R.id.month_info);
		dayInfo.setOnClickListener(new MyClickListener());
		weekInfo.setOnClickListener(new MyClickListener());
		monthInfo.setOnClickListener(new MyClickListener());
		return view;
	}
	
	private class MyClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.day_info:
				startActivity(new Intent(getActivity(),DayBalanceAct.class));
				break;
			case R.id.week_info:
				startActivity(new Intent(getActivity(),DayBalanceAct.class));
				break;
			case R.id.month_info:
				startActivity(new Intent(getActivity(),DayBalanceAct.class));
				break;
			}
		}
	}
}
