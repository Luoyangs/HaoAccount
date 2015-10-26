package com.haoxue.haoaccount.act;

import java.util.ArrayList;
import java.util.List;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.view.CuViewPager;
import com.haoxue.haoaccount.view.DeletableEditText;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 说明：注册
 * 作者：Luoyangs
 * 时间：2015-10-25
 */
@ContentView(R.layout.act_register_layout)
public class RegisterAct extends Activity {

	@ViewInject(R.id.tv1)
	private TextView tv1;
	@ViewInject(R.id.tv2)
	private TextView tv2;
	@ViewInject(R.id.tv3)
	private TextView tv3;
	@ViewInject(R.id.v1)
	private View v1;
	@ViewInject(R.id.v2)
	private View v2;
	@ViewInject(R.id.v3)
	private View v3;
	@ViewInject(R.id.vPager)
	private CuViewPager vPager;
	
	private List<View> viewlists;//视图页面集合
	private View view1,view2,view3;
	private boolean reg_menu;//false:手机，true：邮箱
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		//初始化操作
		tv1.setTextColor(getResources().getColor(R.color.base_green));
		tv2.setTextColor(getResources().getColor(R.color.base_black_light));
		tv3.setTextColor(getResources().getColor(R.color.base_black_light));
		v1.setBackgroundColor(getResources().getColor(R.color.base_green));
		v2.setBackgroundColor(getResources().getColor(R.color.base_light));
		v3.setBackgroundColor(getResources().getColor(R.color.base_light));
		viewlists = new ArrayList<View>();
		view1 = getLayoutInflater().inflate(R.layout.view_register1_layout, null);
		view2 = getLayoutInflater().inflate(R.layout.view_register2_layout, null);
		view3 = getLayoutInflater().inflate(R.layout.view_register3_layout, null);
		viewlists.add(view1); 
		viewlists.add(view2); 
		viewlists.add(view3); 
		vPager.setAdapter(new ViewPagerAdapter()); 
		//设置当前为第一页
		vPager.setCurrentItem(0);
	}
	
	@OnClick(R.id.titilbar_left)
	public void onClick(View view){
		this.finishPage();
	}
	
	private void finishPage(){
		this.finish();
		overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
	}
	
	private class ViewPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() { // 获得size
			return viewlists.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View view, int position, Object object) {
			// 销毁Item
			((CuViewPager) view).removeView(viewlists.get(position));
		}

		@Override
		public Object instantiateItem(View view, final int position) {
			// 实例化Item
			((CuViewPager) view).addView(viewlists.get(position), 0);
			if(position == 0){
				//判断是手机注册还是邮箱注册
				final TextView menu1 = (TextView) view1.findViewById(R.id.reg_menu1);
				final TextView menu2 = (TextView) view1.findViewById(R.id.reg_menu2);
				final DeletableEditText phone = (DeletableEditText) view1.findViewById(R.id.user_name_input);
				final DeletableEditText email = (DeletableEditText) view1.findViewById(R.id.user_email_input);
				final DeletableEditText pass = (DeletableEditText) view1.findViewById(R.id.user_password_input);
				final DeletableEditText pass2 = (DeletableEditText) view1.findViewById(R.id.user_password_input2);
				menu1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View view) {
						reg_menu = false;//手机
						menu1.setTextColor(getResources().getColor(R.color.great_red));
						menu2.setTextColor(getResources().getColor(R.color.base_black_light));
						menu1.setBackgroundResource(R.drawable.bg_login_tab);
						menu2.setBackground(null);
						phone.setVisibility(View.VISIBLE);
						email.setVisibility(View.GONE);
						clearInput(phone,pass,pass2);
					}
					
				});
				menu2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View view) {
						reg_menu = false;//邮箱
						menu1.setTextColor(getResources().getColor(R.color.base_black_light));
						menu2.setTextColor(getResources().getColor(R.color.great_red));
						menu1.setBackground(null);
						menu2.setBackgroundResource(R.drawable.bg_login_tab);
						phone.setVisibility(View.GONE);
						email.setVisibility(View.VISIBLE);
						clearInput(email,pass,pass2);
					}
					
				});
				((Button) view1.findViewById(R.id.personal_commit1)).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View view) {
						//验证输入
						if (!reg_menu) {
							
						}else{
							
						}
						//导航变化
						tv1.setTextColor(getResources().getColor(R.color.base_green));
						tv2.setTextColor(getResources().getColor(R.color.base_green));
						tv3.setTextColor(getResources().getColor(R.color.base_black_light));
						v1.setBackgroundColor(getResources().getColor(R.color.base_green));
						v2.setBackgroundColor(getResources().getColor(R.color.base_green));
						v3.setBackgroundColor(getResources().getColor(R.color.base_light));
						vPager.setCurrentItem(position + 1,true);
					}
				});
			}else if(position == 1){
				//跳过
				((Button) view2.findViewById(R.id.personal_commit21)).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View view) {
						tv1.setTextColor(getResources().getColor(R.color.base_green));
						tv2.setTextColor(getResources().getColor(R.color.base_green));
						tv3.setTextColor(getResources().getColor(R.color.base_green));
						v1.setBackgroundColor(getResources().getColor(R.color.base_green));
						v2.setBackgroundColor(getResources().getColor(R.color.base_green));
						v3.setBackgroundColor(getResources().getColor(R.color.base_green));
						vPager.setCurrentItem(position + 1,true);
					}
				});
				//下一步
				((Button) view2.findViewById(R.id.personal_commit22)).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View view) {
						//验证输入
						
						//导航变化
						tv1.setTextColor(getResources().getColor(R.color.base_green));
						tv2.setTextColor(getResources().getColor(R.color.base_green));
						tv3.setTextColor(getResources().getColor(R.color.base_green));
						v1.setBackgroundColor(getResources().getColor(R.color.base_green));
						v2.setBackgroundColor(getResources().getColor(R.color.base_green));
						v3.setBackgroundColor(getResources().getColor(R.color.base_green));
						vPager.setCurrentItem(position + 1,true);
					}
				});
			}else if(position == 2){
				((Button) view3.findViewById(R.id.personal_commit3)).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View view) {
						startActivity(new Intent(RegisterAct.this,UserInfoAct.class));
						finishPage();
					}
				});
			}
			return viewlists.get(position);
		}

	}
	
	/**清空文本框*/
	private void clearInput(DeletableEditText... inputs){
		for (int i = 0; i < inputs.length; i++) {
			inputs[i].setText("");
		}
	}
}
