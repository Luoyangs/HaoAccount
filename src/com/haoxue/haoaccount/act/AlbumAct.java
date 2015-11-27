package com.haoxue.haoaccount.act;

import java.util.ArrayList;
import java.util.List;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.adapter.AlbumAdapter;
import com.haoxue.haoaccount.bean.ImageBucket;
import com.haoxue.haoaccount.bean.ImageItem;
import com.haoxue.haoaccount.util.AlbumHelper;
import com.haoxue.haoaccount.util.Bimp;
import com.haoxue.haoaccount.util.ToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * 说明：相集选择
 * 作者：Luoyangs
 * 时间：2015-11-8
 */
@ContentView(R.layout.act_album_layout)
public class AlbumAct extends Activity {

	@ViewInject(R.id.myText)
	private TextView myText;//没有相册时的提示
	@ViewInject(R.id.myGrid)
	private GridView myGrid;
	@ViewInject(R.id.btnok)
	private TextView btnok;
	private AlbumAdapter albumAdapter;
	private AlbumHelper helper;
	public static List<ImageBucket> contentList;
	private ArrayList<ImageItem> dataList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		
		helper = AlbumHelper.getHelper();
		helper.init(AlbumAct.this);
		contentList = helper.getImagesBucketList(false);
		dataList = new ArrayList<ImageItem>();
		for (int i = 0; i < contentList.size(); i++) {
			dataList.addAll(contentList.get(i).imageList);
		}
		albumAdapter = new AlbumAdapter(AlbumAct.this, dataList, Bimp.tempSelectBitmap);
		myGrid.setAdapter(albumAdapter);
		myGrid.setEmptyView(myText);
		btnok.setText("[确定" + Bimp.tempSelectBitmap.size()+ "/8]");
		albumAdapter.setOnItemClickListener(new AlbumAdapter.OnItemClickListener() {
			
			@Override
			public void onItemClick(ToggleButton toggleButton, int position, boolean isChecked,Button chooseBt){
				if (Bimp.tempSelectBitmap.size() >= 8) {
					toggleButton.setChecked(false);
					chooseBt.setVisibility(View.GONE);
					if (!removeOneData(dataList.get(position))) {
						ToastUtil.showShort(AlbumAct.this, "只能选择8张图片~");
					}
					return;
				}
				if (isChecked) {
					chooseBt.setVisibility(View.VISIBLE);
					Bimp.tempSelectBitmap.add(dataList.get(position));
					btnok.setText("[确定" + Bimp.tempSelectBitmap.size()+ "/8]");
				} else {
					Bimp.tempSelectBitmap.remove(dataList.get(position));
					chooseBt.setVisibility(View.GONE);
					btnok.setText("[确定" + Bimp.tempSelectBitmap.size()+ "/8]");
				}
			}
		});
	}
	
	private boolean removeOneData(ImageItem imageItem) {
		if (Bimp.tempSelectBitmap.contains(imageItem)) {
			Bimp.tempSelectBitmap.remove(imageItem);
			btnok.setText("[确定" + Bimp.tempSelectBitmap.size() + "/8]");
			return true;
		}
		return false;
	}
	
	@OnClick(R.id.titilbar_left)
	public void onBack(View view){
		this.finish();
		overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
	}

}
