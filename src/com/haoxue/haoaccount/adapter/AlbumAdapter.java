package com.haoxue.haoaccount.adapter;

import java.util.ArrayList;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.bean.ImageItem;
import com.haoxue.haoaccount.util.BitmapCache;
import com.haoxue.haoaccount.util.BitmapCache.ImageCallback;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ToggleButton;

/**
 * 说明：相册适配器
 * 作者：Luoyangs
 * 时间：2015-11-9
 */
public class AlbumAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<ImageItem> dataList;
	private ArrayList<ImageItem> selectedDataList;
	private DisplayMetrics dm;
	private BitmapCache cache;
	public AlbumAdapter(Context context, ArrayList<ImageItem> dataList, ArrayList<ImageItem> selectedDataList) {
		this.context = context;
		cache = new BitmapCache();
		this.dataList = dataList;
		this.selectedDataList = selectedDataList;
		dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
	}
	
	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_albumselect_layout, null,false);
		}
		ImageView imageView = (ImageView) convertView.findViewById(R.id.image_view);
		ToggleButton toggleButton = (ToggleButton) convertView.findViewById(R.id.toggle_button);
		Button choosetoggle = (Button) convertView.findViewById(R.id.choosedbt);
		String path;
		if (dataList != null && dataList.size() > position)
			path = dataList.get(position).imagePath;
		else
			path = "camera_default";
		if (path.contains("camera_default")) {
			imageView.setImageResource(R.drawable.plugin_camera_no_pictures);
		} else {
			final ImageItem item = dataList.get(position);
			imageView.setTag(item.imagePath);
			cache.displayBmp(imageView, item.thumbnailPath, item.imagePath, callback);
		}
		toggleButton.setTag(position);
		choosetoggle.setTag(position);
		toggleButton.setOnClickListener(new ToggleClickListener(choosetoggle));
		if (selectedDataList.contains(dataList.get(position))) {
			toggleButton.setChecked(true);
			choosetoggle.setVisibility(View.VISIBLE);
		} else {
			toggleButton.setChecked(false);
			choosetoggle.setVisibility(View.GONE);
		}
		return convertView;
	}
	
	private ImageCallback callback = new ImageCallback() {
		@Override
		public void imageLoad(ImageView imageView, Bitmap bitmap,Object... params) {
			if (imageView != null && bitmap != null) {
				String url = (String) params[0];
				if (url != null && url.equals((String) imageView.getTag())) {
					((ImageView) imageView).setImageBitmap(bitmap);
				} else {
					Log.e("AlbumAct", "callback, bmp not match");
				}
			} else {
				Log.e("AlbumAct", "callback, bmp null");
			}
		}
	};

	private class ToggleClickListener implements OnClickListener{
		private Button chooseBt;
		public ToggleClickListener(Button choosebt){
			this.chooseBt = choosebt;
		}
		
		@Override
		public void onClick(View view) {
			if (view instanceof ToggleButton) {
				ToggleButton toggleButton = (ToggleButton) view;
				int position = (Integer) toggleButton.getTag();
				if (dataList != null && mOnItemClickListener != null && position < dataList.size()) {
					mOnItemClickListener.onItemClick(toggleButton, position, toggleButton.isChecked(),chooseBt);
				}
			}
		}
	}
	
	private OnItemClickListener mOnItemClickListener;
	public void setOnItemClickListener(OnItemClickListener listener) {
		mOnItemClickListener = listener;
	}

	public interface OnItemClickListener {
		public void onItemClick(ToggleButton view, int position, boolean isChecked,Button chooseBt);
	}

}
