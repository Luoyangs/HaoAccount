package com.haoxue.haoaccount.adapter;

import java.util.List;
import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.act.MoreTypeAct;
import com.haoxue.haoaccount.act.PrepayAct;
import com.haoxue.haoaccount.base.Constant;
import com.haoxue.haoaccount.view.TextDrawable;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 说明：
 * 作者：Luoyangs
 * 时间：2015-11-20
 */
public class PersonTypeAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private List<String> mDatas;
	private Context context;
	
	public PersonTypeAdapter(Context context,List<String> mDatas){
		this.mInflater = LayoutInflater.from(context);
		this.mDatas = mDatas;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mDatas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		View view = mInflater.inflate(R.layout.item_per_type, null,false);
		ImageView mImg = (ImageView) view.findViewById(R.id.child_img);
		TextView mText = (TextView) view.findViewById(R.id.child_name);
		TextView mTip = (TextView) view.findViewById(R.id.child_tip);
		mText.setText(mDatas.get(arg0));
		mTip.setText(getTips(mDatas.get(arg0)));
		mImg.setImageDrawable(TextDrawable.builder().buildRound(mDatas.get(arg0), Color.parseColor(Constant.FAVTEXTCOLOR[arg0+6])));
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				String str = mDatas.get(arg0);
				if (str.equals("班费")) {
					//context.startActivity(new Intent(context,PrepayAct.class));
				}else if (str.equals("代付")) {
					//context.startActivity(new Intent(context,PrepayAct.class));
				}else if (str.equals("退款")) {
					//context.startActivity(new Intent(context,PrepayAct.class));
				}else if (str.equals("进货")) {
					//context.startActivity(new Intent(context,PrepayAct.class));
				}else if (str.equals("销售")) {
					//context.startActivity(new Intent(context,PrepayAct.class));
				}
			}
		});
		return view;
	}

	private static String getTips(String str){
		String tip = "";
		if (str.equals("班费")) {
			tip = " 收/支| 班费开销 | 记账";
		}else if (str.equals("代付")) {
			tip = " 支出| 帮人支付| 外借";
		}else if (str.equals("退款")) {
			tip = " 支出| 还贷";
		}else if (str.equals("进货")) {
			tip = " 支出| 进货支出| 支持现金";
		}else if (str.equals("销售")) {
			tip = " 收入| 销售收入| 收入统计";
		}
		return tip;
	}
}
