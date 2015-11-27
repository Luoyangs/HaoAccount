package com.haoxue.haoaccount.util;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 *
 * @author WangYuWen
 * @version 1.0
 * @date 2015年1月30日
 * @Copyright: Copyright (c) 2014 Shenzhen Utoow Technology Co., Ltd. All rights
 *             reserved.
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {
	/** 获取系统当前时间*/
	@SuppressLint("SimpleDateFormat")
	public static String getDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**获取系统当前日期*/
	public static String getDay() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	/**设置TimePicker&DatePicker并排显示*/
	public static void resizePicker(FrameLayout tp) {
        List<NumberPicker> npList = findNumberPicker(tp);
        for (NumberPicker np : npList) {
            resizeNumberPicker(np);
        }
    }
 
	private static List<NumberPicker> findNumberPicker(ViewGroup viewGroup) {
        List<NumberPicker> npList = new ArrayList<NumberPicker>();
        View child = null;
        if (null != viewGroup) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                child = viewGroup.getChildAt(i);
                if (child instanceof NumberPicker) {
                    npList.add((NumberPicker) child);
                } else if (child instanceof LinearLayout) {
                    List<NumberPicker> result = findNumberPicker((ViewGroup) child);
                    if (result.size() > 0) {
                        return result;
                    }
                }
            }
        }
        return npList;
    }
 
	private static void resizeNumberPicker(NumberPicker np) {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(156, RadioGroup.LayoutParams.WRAP_CONTENT);
		params.setMargins(10, 0, 10, 0);
		np.setLayoutParams(params);
    }
}
