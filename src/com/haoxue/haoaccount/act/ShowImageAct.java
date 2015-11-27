package com.haoxue.haoaccount.act;
import com.haoxue.haoaccount.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * 说明:显示大图
 * 作者:Luoyangs
 * 时间:2015-9-18
 */
@ContentView(R.layout.act_showimage_layout)
public class ShowImageAct extends Activity {
	
	@ViewInject(R.id.image)
	private ImageView imageView;
	@ViewInject(R.id.loading)
	private ProgressBar spinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);

		String type = getIntent().getStringExtra("type");
		if(type.equals("INT")){
			imageView.setImageResource(getIntent().getIntExtra("imgId",R.drawable.bg1));
		}else if(type.equals("BITMAP")){
			imageView.setImageBitmap((Bitmap)getIntent().getParcelableExtra("imgurl"));
		}
		//添加监听
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				finish();
			}
		});
	}
}
