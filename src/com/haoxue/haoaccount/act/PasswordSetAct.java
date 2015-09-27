package com.haoxue.haoaccount.act;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.base.ShareDataHelper;
import com.haoxue.haoaccount.util.KeyboardUtil;
import com.haoxue.haoaccount.util.MD5Util;
import com.haoxue.haoaccount.util.ToastUtil;
import com.haoxue.haoaccount.view.MaxLengthWatcher;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 说明:设置密码
 * 作者:Luoyangs
 * 时间:2015-9-26
 */
@ContentView(R.layout.act_inputpass_layout)
public class PasswordSetAct extends Activity {

	@ViewInject(R.id.editText1)
	private EditText editText1;
	@ViewInject(R.id.editText2)
	private EditText editText2;
	@ViewInject(R.id.editText3)
	private EditText editText3;
	@ViewInject(R.id.editText4)
	private EditText editText4;
	@ViewInject(R.id.EditText05)
	private EditText EditText05;
	@ViewInject(R.id.EditText06)
	private EditText EditText06;
	@ViewInject(R.id.EditText07)
	private EditText EditText07;
	@ViewInject(R.id.EditText08)
	private EditText EditText08;
	@ViewInject(R.id.passwordEditText)
	private EditText passwordEditText;
	@ViewInject(R.id.secound_pwd)
	private LinearLayout secound_pwd;
	@ViewInject(R.id.first_pwd)
	private LinearLayout first_pwd;
	@ViewInject(R.id.inuptnumber)
	private TextView inuptnumber;
	@ViewInject(R.id.textView2)
	private TextView textView2;
	
	private static final int ismorechoose = 56565656;
	private Boolean isfirst_inputall = false;
	private String passwordfirst, passwordsecound;
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg){ // 接收通知，刷新列表
			switch (msg.what) {
			case 10001:
				break;
			case ismorechoose:
				isfirst_inputall = !isfirst_inputall;
				input(msg);
				break;
			default:
				break;
			}
		}
	};
	
	private void input(Message msg){
		String realpass = ShareDataHelper.getInstance(PasswordSetAct.this).getPassword("PASSWORD");
		if (first_pwd.getVisibility() == View.VISIBLE && secound_pwd.getVisibility() == View.GONE) {
			if (((Integer) msg.obj) % 5 == 0) {
				editText1.setBackgroundResource(R.drawable.edittext_focus);
				editText2.setBackgroundResource(R.drawable.edittext_default);
				editText3.setBackgroundResource(R.drawable.edittext_default);
				editText4.setBackgroundResource(R.drawable.edittext_default);
			}
			if (((Integer) msg.obj) % 5 == 1) {
				editText1.setBackgroundResource(R.drawable.edittext_pass);
				editText2.setBackgroundResource(R.drawable.edittext_focus);
				editText3.setBackgroundResource(R.drawable.edittext_default);
				editText4.setBackgroundResource(R.drawable.edittext_default);
			}
			if (((Integer) msg.obj) % 5 == 2) {
				editText1.setBackgroundResource(R.drawable.edittext_pass);
				editText2.setBackgroundResource(R.drawable.edittext_pass);
				editText3.setBackgroundResource(R.drawable.edittext_focus);
				editText4.setBackgroundResource(R.drawable.edittext_default);
			}
			if (((Integer) msg.obj) % 5 == 3) {
				editText1.setBackgroundResource(R.drawable.edittext_pass);
				editText2.setBackgroundResource(R.drawable.edittext_pass);
				editText3.setBackgroundResource(R.drawable.edittext_pass);
				editText4.setBackgroundResource(R.drawable.edittext_default);
			}
			if (((Integer) msg.obj) % 5 == 4) {
				editText1.setBackgroundResource(R.drawable.edittext_focus);
				editText2.setBackgroundResource(R.drawable.edittext_default);
				editText3.setBackgroundResource(R.drawable.edittext_default);
				editText4.setBackgroundResource(R.drawable.edittext_default);
				
				passwordfirst = passwordEditText.getText().toString();
				if (realpass == null || realpass.equals("")) {
					passwordfirst = passwordEditText.getText().toString();
					passwordEditText.setText("");
					first_pwd.setVisibility(View.GONE);
					secound_pwd.setVisibility(View.VISIBLE);
				}else{
					if (MD5Util.decrypt(realpass).equals(MD5Util.encryptFirst(passwordfirst))) {
						startActivity(new Intent(PasswordSetAct.this, MainAct.class));
						finish();
						ToastUtil.showShort(PasswordSetAct.this, "欢迎再次回来！");
					} else {
						passwordfirst = passwordEditText.getText().toString();
						passwordEditText.setText("");
						first_pwd.setVisibility(View.GONE);
						secound_pwd.setVisibility(View.VISIBLE);
					}
				}
			}
		} else {
			inuptnumber.setText("请再次输入");
			textView2.setText("");
			if (((Integer) msg.obj) % 5 == 0) {
				EditText05.setBackgroundResource(R.drawable.edittext_focus);
				EditText06.setBackgroundResource(R.drawable.edittext_default);
				EditText07.setBackgroundResource(R.drawable.edittext_default);
				EditText08.setBackgroundResource(R.drawable.edittext_default);
			}
			if (((Integer) msg.obj) % 5 == 1) {
				EditText05.setBackgroundResource(R.drawable.edittext_pass);
				EditText06.setBackgroundResource(R.drawable.edittext_focus);
				EditText07.setBackgroundResource(R.drawable.edittext_default);
				EditText08.setBackgroundResource(R.drawable.edittext_default);
			}
			if (((Integer) msg.obj) % 5 == 2) {
				EditText05.setBackgroundResource(R.drawable.edittext_pass);
				EditText06.setBackgroundResource(R.drawable.edittext_pass);
				EditText07.setBackgroundResource(R.drawable.edittext_focus);
				EditText08.setBackgroundResource(R.drawable.edittext_default);
			}
			if (((Integer) msg.obj) % 5 == 3) {
				EditText05.setBackgroundResource(R.drawable.edittext_pass);
				EditText06.setBackgroundResource(R.drawable.edittext_pass);
				EditText07.setBackgroundResource(R.drawable.edittext_pass);
				EditText08.setBackgroundResource(R.drawable.edittext_default);
			}
			if (((Integer) msg.obj) % 5 == 4) {
				EditText05.setBackgroundResource(R.drawable.edittext_focus);
				EditText06.setBackgroundResource(R.drawable.edittext_default);
				EditText07.setBackgroundResource(R.drawable.edittext_default);
				EditText08.setBackgroundResource(R.drawable.edittext_default);

				if (realpass == null || realpass.equals("")) {
					if (passwordfirst.equals(passwordEditText.getText().toString())) {
						ShareDataHelper.getInstance(PasswordSetAct.this).savePassword("PASSWORD", MD5Util.encryptSecond(passwordfirst));
						startActivity(new Intent(PasswordSetAct.this, MainAct.class));
						finish();
						ToastUtil.showShort(PasswordSetAct.this, "密码设置成功！");
					} else {
						passwordEditText.setText("");
						first_pwd.setVisibility(View.VISIBLE);
						secound_pwd.setVisibility(View.GONE);
						textView2.setText("两次输入的密码不同");
						inuptnumber.setText("请输入密码");
						ToastUtil.showShort(PasswordSetAct.this, "密码设置失败，清重新输入！");
					}
				}else{
					passwordsecound = passwordEditText.getText().toString();
					if (MD5Util.decrypt(realpass).equals(MD5Util.encryptFirst(passwordsecound))) {
						startActivity(new Intent(PasswordSetAct.this, MainAct.class));
						finish();
						ToastUtil.showShort(PasswordSetAct.this, "欢迎再次回来！");
					}else{
						passwordEditText.setText("");
						first_pwd.setVisibility(View.VISIBLE);
						secound_pwd.setVisibility(View.GONE);
						textView2.setText("输入的密码不对");
						inuptnumber.setText("请输入密码");
					}
				}
			}
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		
		// 限制输入框中的字符数
		editText1.addTextChangedListener(new MaxLengthWatcher(1, editText1));
		editText2.addTextChangedListener(new MaxLengthWatcher(1, editText2));
		editText3.addTextChangedListener(new MaxLengthWatcher(1, editText3));
		editText4.addTextChangedListener(new MaxLengthWatcher(1, editText4));
		EditText05.addTextChangedListener(new MaxLengthWatcher(1, EditText05));
		EditText06.addTextChangedListener(new MaxLengthWatcher(1, EditText06));
		EditText07.addTextChangedListener(new MaxLengthWatcher(1, EditText07));
		EditText08.addTextChangedListener(new MaxLengthWatcher(1, EditText08));
		passwordEditText.addTextChangedListener(new MaxLengthWatcher(4,editText4));
		//屏蔽软键盘
		editText1.setInputType(InputType.TYPE_NULL);
		editText2.setInputType(InputType.TYPE_NULL);
		editText3.setInputType(InputType.TYPE_NULL);
		editText4.setInputType(InputType.TYPE_NULL);
		EditText05.setInputType(InputType.TYPE_NULL);
		EditText06.setInputType(InputType.TYPE_NULL);
		EditText07.setInputType(InputType.TYPE_NULL);
		EditText08.setInputType(InputType.TYPE_NULL);
		
		int inputback = passwordEditText.getInputType();
		passwordEditText.setInputType(InputType.TYPE_NULL);
		new KeyboardUtil(this, this, passwordEditText).showKeyboard();
		passwordEditText.setInputType(inputback);
		passwordEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				set_Expassword(s.length());
			}

		});
	}
	
	private void set_Expassword(int m) {
		Message message = new Message();
		message.what = ismorechoose;
		message.obj = m;
		handler.sendMessage(message); // 跟新UI
	}
}
