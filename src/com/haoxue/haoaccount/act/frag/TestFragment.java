package com.haoxue.haoaccount.act.frag;

import com.haoxue.haoaccount.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * 说明:
 * 作者:Luoyangs
 * 时间:2015-10-1
 */
public class TestFragment extends Fragment implements OnClickListener{

	private EditText text;
	private Button btn;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_test_layout, null,false);
		text = (EditText) view.findViewById(R.id.editText1);
		btn = (Button) view.findViewById(R.id.button1);
		btn.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View view) {
		
	}
}
