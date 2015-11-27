package com.haoxue.haoaccount.act;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.base.SQLiteHelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 说明:
 * 作者:Luoyangs
 * 时间:2015-10-6
 */
public class TestAct extends Activity implements OnClickListener {

	private Button btnButton;
	private TextView textView;
	private ImageView imageView;
	
	private SQLiteHelper helper;
	private static final String[] PLANETS = new String[]{"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Uranus", "Neptune", "Pluto"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_test_layout);
		textView = (TextView)findViewById(R.id.textView1);
        btnButton = (Button) findViewById(R.id.button1);
        
        findViewById(R.id.button1).setOnClickListener(this);
	}
	
	@Override
    public void onClick(View v) {

    }
}
