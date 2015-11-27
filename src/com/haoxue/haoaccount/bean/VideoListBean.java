package com.haoxue.haoaccount.bean;

import android.graphics.Bitmap;

public class VideoListBean{

	/** 视频id */
	private String Video_Id;
	/** 视频名字 */
	private String Video_name;
	/** 视频路径 */
	private String Video_path;
	/** 视频展示配置表-封面图URL */
	private Bitmap Video_imgbg;
	/** 创建时间 */
	private String Create_time;

	
	
	public Bitmap getVideo_imgbg() {
		return Video_imgbg;
	}

	public void setVideo_imgbg(Bitmap video_imgbg) {
		Video_imgbg = video_imgbg;
	}

	public String getCreate_time() {
		return Create_time;
	}

	public void setCreate_time(String create_time) {
		Create_time = create_time;
	}

	public String getVideo_Id() {
		return Video_Id;
	}

	public void setVideo_Id(String video_Id) {
		Video_Id = video_Id;
	}

	public String getVideo_name() {
		return Video_name;
	}

	public void setVideo_name(String video_name) {
		Video_name = video_name;
	}

	public String getVideo_path() {
		return Video_path;
	}

	public void setVideo_path(String video_path) {
		Video_path = video_path;
	}

}
