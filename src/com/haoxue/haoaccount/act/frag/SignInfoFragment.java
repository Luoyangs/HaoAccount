package com.haoxue.haoaccount.act.frag;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.act.SignAct;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * 说明：
 * 作者：Luoyangs
 * 时间：2015-11-3
 */
@SuppressLint("ValidFragment")
public class SignInfoFragment extends Fragment {
	
	private boolean hasSigned;
	private Button btn;

	public SignInfoFragment(boolean hasSigned){
		this.hasSigned = hasSigned;
	}
	
	public void setHasSigned(boolean hasSigned) {
		this.hasSigned = hasSigned;
		btn.setText(hasSigned?"已经签到":"我要签到");
		btn.setEnabled(!hasSigned);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_signinfo_layout, null,false);
		btn = (Button) view.findViewById(R.id.sign);
		btn.setText(hasSigned?"已经签到":"我要签到");
		btn.setEnabled(!hasSigned);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				((SignAct)getActivity()).sign();
			}
		});
		return view;
	}
}
