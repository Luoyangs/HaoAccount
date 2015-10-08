package com.haoxue.haoaccount.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * 说明:
 * 作者:Luoyangs
 * 时间:2015-10-8
 */
public class FileUtil {
	
	private Context context;

	public FileUtil(Context context){
		this.context = context;
	}
	
	/**依据图片名字获取图片文件*/
	public Drawable getDrawableByName(String name) {
		String packageName = context.getApplicationInfo().packageName;
		int id = context.getResources().getIdentifier(name, "drawable", packageName);
		return context.getResources().getDrawable(id);
	}

}
