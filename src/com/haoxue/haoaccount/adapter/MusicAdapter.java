package com.haoxue.haoaccount.adapter;

import java.io.IOException;
import java.util.List;

import com.haoxue.haoaccount.R;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 说明：
 * 作者：Luoyangs
 * 时间：2015-11-6
 */
public class MusicAdapter extends BaseAdapter{

	private List<String> list;
	private Context context;
	private String type;
	
	public MusicAdapter(List<String> list,Context context,String type){
		this.list = list;
		this.context = context;
		this.type = type;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_notemusic_layout, null,false);
		TextView name = (TextView) view.findViewById(R.id.favName);
		TextView time = (TextView) view.findViewById(R.id.fatime);
		ImageView Img = (ImageView) view.findViewById(R.id.img0);
		ImageView Img2 = (ImageView) view.findViewById(R.id.imgp);
		String FilePath = list.get(position);
		String musicName = FilePath.substring(FilePath.lastIndexOf("/")+1);
		name.setText(musicName);
		if (FilePath.equals("")) {
			Img.setVisibility(View.GONE);
			Img2.setVisibility(View.GONE);
			time.setVisibility(View.GONE);
		}else{
			if (type.equals("MUSIC")) {
				Img.setVisibility(View.VISIBLE);
				Img2.setVisibility(View.GONE);
			}else if (type.equals("VEDIO")) {
				Img.setVisibility(View.GONE);
				Img2.setVisibility(View.VISIBLE);
			}
			MediaPlayer player = new MediaPlayer();
			try {
				player.setDataSource(FilePath);
				player.prepare();
			}catch (IOException e) {
				e.printStackTrace();
			}
			int timespan = player.getDuration();
			time.setText(formatDuring(timespan));
		}
		return view;
	}
	
	public static String formatDuring(long mss) {  
	    long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);  
	    long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);  
	    long seconds = (mss % (1000 * 60)) / 1000; 
	    String hstr,mstr,sstr;
	    hstr = String.valueOf(hours);
	    mstr = String.valueOf(minutes);
	    sstr = String.valueOf(seconds);
	    if (hours <10) {
	    	hstr = "0"+String.valueOf(hours);
		}
	    if (minutes <10) {
	    	mstr = "0"+String.valueOf(minutes);
		}
	    if (seconds <10) {
	    	sstr = "0"+String.valueOf(seconds);
		}
	    return hstr + " : " + mstr + " : " + sstr;  
	} 
}