package com.haoxue.haoaccount.act;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.act.frag.SignFavFragment;
import com.haoxue.haoaccount.act.frag.SignHisFragment;
import com.haoxue.haoaccount.act.frag.SignInfoFragment;
import com.haoxue.haoaccount.base.AssetDBManager;
import com.haoxue.haoaccount.base.Constant;
import com.haoxue.haoaccount.base.Constant.DB;
import com.haoxue.haoaccount.base.ShareDataHelper;
import com.haoxue.haoaccount.util.ToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 说明:签到页面
 * 作者:Luoyangs
 * 时间:2015-11-1
 */
@SuppressLint("SimpleDateFormat")
@ContentView(R.layout.act_sign_layout)
public class SignAct extends FragmentActivity implements OnClickListener{

	@ViewInject(R.id.tv_hint_success)
	private TextView tv_hint_success;//提示语
	@ViewInject(R.id.rl_payInfo)
	private RelativeLayout rl_payInfo;
	@ViewInject(R.id.tv_order_num2)
	private TextView tv_curfav;
	@ViewInject(R.id.tv_total_money2)
	private TextView tv_maxfav;
	@ViewInject(R.id.tv_payment2)
	private TextView tv_totalDay;
	@ViewInject(R.id.export)
	private ImageView export;
	@ViewInject(R.id.iv_export)
	private ImageView iv_export;
	@ViewInject(R.id.main_frame)
	private FrameLayout main_frame;
	private PopupWindow pw;
	private String curTime;
	private boolean hasSigned;
	
	private Fragment curFragment;// 当前面板
	private Fragment siginInfo,signfav,signhis;
	private SQLiteDatabase database;
	private int curfav,sumfav;
	private int userId;
	private int deadline;
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if (msg.what == 0x01) {
				startAnimation();
				ShareDataHelper.getInstance(SignAct.this).saveUser("signTime", curTime);
				//写入数据库
				ContentValues values = new ContentValues();
				values.put("userId", userId);
				values.put("curfav", curfav);
				values.put("sumfav", sumfav+curfav);
				values.put("subfav", deadline-sumfav-curfav);
				database.insert(DB.SIGN_TABLE_NAME, "id", values);
				tv_hint_success.setText("签到成功");
				hasSigned = true;
				((SignInfoFragment)siginInfo).setHasSigned(hasSigned);
				tv_hint_success.setTextColor(Color.GREEN);
				export.setVisibility(View.GONE);
				rl_payInfo.setVisibility(View.VISIBLE);
				iv_export.setVisibility(View.VISIBLE);
				main_frame.setVisibility(View.GONE);
				hideSign();
			}
			if (msg.what == 0x02) {
				rl_payInfo.setVisibility(View.GONE);
				iv_export.setVisibility(View.GONE);
				tv_hint_success.setText("当前积分："+(sumfav+curfav));
				main_frame.setVisibility(View.VISIBLE);
				//如果当前为历史页面，则更新
				switchToTargetFragment(signhis);
				if (curFragment == signhis) {
					((SignHisFragment)signhis).setDataLists(getSignHisLists());
				}
			}
		}
	};
	
	private void hideSign(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(4400);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message msg = new Message();
				msg.what = 0x02;
				handler.sendMessage(msg);
			}
		}).start();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		database = new AssetDBManager().openDatabase(this);
		userId = ShareDataHelper.getInstance(SignAct.this).getUserId();
		//判断是否已经存在签到
		Cursor cursor1 = database.rawQuery(DB.HAS_SIGN, null);
		int temp_sumfav = 0;
		if (cursor1.moveToFirst() && cursor1.getInt(0) == 0) {
    		temp_sumfav = 0;//不存在积分
    	}else{
    		Cursor cursor2 = database.rawQuery(DB.GET_TOP_SIGN_BY_USERID, new String[]{String.valueOf(userId)});
    		while(cursor2.moveToNext()){
    			temp_sumfav = cursor2.getInt(cursor2.getColumnIndex("sumfav"));
    		}
    	}
    	Map<String, Integer> map = getCurSignAdd(temp_sumfav);
    	sumfav = temp_sumfav;
    	curfav = map.get("favcount");
    	deadline = map.get("deadline");
    	tv_curfav.setText(""+curfav);
    	tv_maxfav.setText(""+sumfav);

		curTime = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String signTime = ShareDataHelper.getInstance(SignAct.this).getUser("signTime");
		int days = 0;
		try {
			days = daysBetween(signTime, curTime)+1;
		} catch (ParseException e) {
			days = 1;
		}
		tv_totalDay.setText(""+days);
		hasSigned = curTime.equals(signTime);
		tv_hint_success.setText(hasSigned?"今天已完成签到":"点击上面完成一键签到~");
		tv_hint_success.setTextColor(hasSigned?Color.GREEN:Color.RED);
		export.setVisibility(View.GONE);
		iv_export.setVisibility(View.GONE);
		rl_payInfo.setVisibility(View.GONE);
		if (savedInstanceState == null) {
			siginInfo = new SignInfoFragment(hasSigned);
			signfav = new SignFavFragment(getSignFavLists());
			signhis = new SignHisFragment(getSignHisLists());
			if (hasSigned) {
				switchToTargetFragment(signhis);
			}else{
				switchToTargetFragment(siginInfo);
			}
		}
	}
	
	/**获取积分*/
	private List<HashMap<String, String>> getSignFavLists(){
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		Cursor cursor = database.rawQuery(Constant.DB.HAS_FAV, null); 
    	if (cursor.moveToFirst() && cursor.getInt(0) == 0) {
    		list = null;
    	}else{
    		Cursor cursor2 = database.rawQuery(Constant.DB.GET_FAV, null);
    		while(cursor2.moveToNext()){
    			HashMap<String,String> map = new HashMap<String, String>();
    			map.put("id", ""+cursor2.getInt(cursor2.getColumnIndex("id")));
    			map.put("name", cursor2.getString(cursor2.getColumnIndex("name")));
    			map.put("deadline", ""+cursor2.getInt(cursor2.getColumnIndex("deadline")));
    			list.add(map);
    		}
    	}
		return list;
	}
	
	/**获取签到 * @throws ParseException */
	private List<HashMap<String, String>> getSignHisLists(){
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		Cursor cursor = database.rawQuery(Constant.DB.HAS_SIGN, null); 
    	if (cursor.moveToFirst() && cursor.getInt(0) == 0) {
    		list = null;
    	}else{
    		Cursor cursor2 = database.rawQuery(Constant.DB.GET_SIGN, null);
    		while(cursor2.moveToNext()){
    			HashMap<String,String> map = new HashMap<String, String>();
    			map.put("curfav", ""+cursor2.getInt(cursor2.getColumnIndex("curfav")));
    			map.put("sumfav", ""+cursor2.getInt(cursor2.getColumnIndex("sumfav")));
    			map.put("subfav", ""+cursor2.getInt(cursor2.getColumnIndex("subfav")));
    			String[] timestr = cursor2.getString(cursor2.getColumnIndex("createTime")).split(" ");
    			map.put("time", timestr[0]);
    			list.add(map);
    		}
    	}
		return list;
	}
	
	public void switchToTargetFragment(Fragment target) {
		this.switchToTargetFragment(curFragment, target);
	}

	private void switchToTargetFragment(Fragment from, Fragment to) {
		if (curFragment != to) {
			if (from == null) {
				// 第一次添加页面
				getSupportFragmentManager()
						.beginTransaction()
						.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
						.add(R.id.main_frame, to)
						.commit();
			} else {
				// 判断是否添加过
				if (!to.isAdded()) {
					getSupportFragmentManager()
							.beginTransaction()
							.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
							.hide(from)
							.add(R.id.main_frame, to).commit();
				} else {
					getSupportFragmentManager()
							.beginTransaction()
							.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
							.hide(from)
							.show(to)
							.commit();
				}
			}
			curFragment = to;
		}
	}
	
	public void sign(){
		if (hasSigned) {
			ToastUtil.showShort(SignAct.this, "今天，您已完成签到,请明天再来~");
		}else{
			newThread();
		}
	}

	@OnClick(R.id.titilbar_left)
	public void onBackClick(View view){
		this.finish();
		overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
	}
	
	@SuppressWarnings("deprecation")
	@OnClick(R.id.titilbar_right)
	public void onMenuClick(View view){
		View menu = getLayoutInflater().inflate(R.layout.pop_layout3, null);
		menu.findViewById(R.id.btn_sign).setOnClickListener(this);
		menu.findViewById(R.id.btn_his).setOnClickListener(this);
		menu.findViewById(R.id.btn_fav).setOnClickListener(this);
		menu.findViewById(R.id.btn_sha).setOnClickListener(this);
		//通过view 和宽·高，构造PopopWindow
        pw = new PopupWindow(menu, 450, 752, true);
        //设置焦点为可点击
        pw.setFocusable(true);
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setOutsideTouchable(true);
        pw.showAsDropDown(view);
	}
	
	private void newThread() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message msg = new Message();
				msg.what = 0x01;
				handler.sendMessage(msg);
			}
		}).start();
	}
	
	private void startAnimation() {
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.slide_down_in);
		anim.setDuration(1200);
		anim.setFillAfter(true);
		rl_payInfo.startAnimation(anim);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_sign:
			sign();
			break;
		case R.id.btn_his:
			switchToTargetFragment(signhis);
			break;
		case R.id.btn_fav:
			switchToTargetFragment(signfav);
			break;
		case R.id.btn_sha:
	
			break;
		}
		pw.dismiss();
	}
	
	/**获取当前的签到积分*/
	private Map<String, Integer> getCurSignAdd(int cur_fav){
		Map<String, Integer> map;
		List<Map<String, Integer>> list = new ArrayList<Map<String,Integer>>();
		Cursor cursor = database.rawQuery(Constant.DB.HAS_FAV, null); 
    	if (cursor.moveToFirst() && cursor.getInt(0) == 0) {
    		return null;
    	}else{
    		Cursor cursor2 = database.rawQuery(Constant.DB.GET_FAV, null);
    		while(cursor2.moveToNext()){
    			map = new HashMap<String,Integer>();
    			map.put("deadline", cursor2.getInt(cursor2.getColumnIndex("deadline")));
    			map.put("favcount", cursor2.getInt(cursor2.getColumnIndex("favcount")));
    			list.add(map);
    		}
    	}
    	//筛选数据
    	if(list.get(0).get("deadline") >= cur_fav){
			return list.get(0);
    	}else if(list.get(list.size()-1).get("deadline") < cur_fav){
			return list.get(list.size()-1);
		}else{
	    	for (int index = 0;index<list.size();index++) {
	    		if (list.get(index).get("deadline") < cur_fav && list.get(index+1).get("deadline") >= cur_fav) {
					return list.get(index+1);
				}
			}
		}
    	return null;
	}
	
	/**计算两个日期之间相差的天数  */
	private int daysBetween(String smdate,String bdate) throws ParseException{  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(smdate));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(bdate));    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
        return Integer.parseInt(String.valueOf(between_days));     
    }  
}
