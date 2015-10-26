package com.haoxue.haoaccount.act;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.base.AssetDBManager;
import com.haoxue.haoaccount.base.Constant;
import com.haoxue.haoaccount.base.ShareDataHelper;
import com.haoxue.haoaccount.util.FileUtil;
import com.haoxue.haoaccount.util.RexUtil;
import com.haoxue.haoaccount.util.ToastUtil;
import com.haoxue.haoaccount.view.CuAlertDialog;
import com.haoxue.haoaccount.view.CuViewPager;
import com.haoxue.haoaccount.view.DeletableEditText;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * 说明：注册
 * 作者：Luoyangs
 * 时间：2015-10-25
 */
@ContentView(R.layout.act_register_layout)
public class RegisterAct extends Activity {

	@ViewInject(R.id.tv1)
	private TextView tv1;
	@ViewInject(R.id.tv2)
	private TextView tv2;
	@ViewInject(R.id.tv3)
	private TextView tv3;
	@ViewInject(R.id.v1)
	private View v1;
	@ViewInject(R.id.v2)
	private View v2;
	@ViewInject(R.id.v3)
	private View v3;
	@ViewInject(R.id.vPager)
	private CuViewPager vPager;
	
	private List<View> viewlists;//视图页面集合
	private View view1,view2,view3;
	private boolean reg_menu;//false:手机，true：邮箱
	private String phoneStr,emailStr,passStr;
	
	private long id;//插入的新数据Id
	private ImageView image;//头像
	private AlertDialog albumDialog;
	private String dateTime;
	private String iconUrl;
	
	private SQLiteDatabase database;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		//初始化操作
		database = new AssetDBManager().openDatabase(this);
		tv1.setTextColor(getResources().getColor(R.color.base_green));
		tv2.setTextColor(getResources().getColor(R.color.base_black_light));
		tv3.setTextColor(getResources().getColor(R.color.base_black_light));
		v1.setBackgroundColor(getResources().getColor(R.color.base_green));
		v2.setBackgroundColor(getResources().getColor(R.color.base_light));
		v3.setBackgroundColor(getResources().getColor(R.color.base_light));
		viewlists = new ArrayList<View>();
		view1 = getLayoutInflater().inflate(R.layout.view_register1_layout, null);
		view2 = getLayoutInflater().inflate(R.layout.view_register2_layout, null);
		view3 = getLayoutInflater().inflate(R.layout.view_register3_layout, null);
		viewlists.add(view1); 
		viewlists.add(view2); 
		viewlists.add(view3); 
		vPager.setAdapter(new ViewPagerAdapter()); 
		//设置当前为第一页
		vPager.setCurrentItem(0);
	}
	
	@OnClick(R.id.titilbar_left)
	public void onClick(View view){
		this.finishPage();
	}
	
	private void finishPage(){
		this.finish();
		overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
	}
	
	private class ViewPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() { // 获得size
			return viewlists.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View view, int position, Object object) {
			// 销毁Item
			((CuViewPager) view).removeView(viewlists.get(position));
		}

		@Override
		public Object instantiateItem(View view, final int position) {
			// 实例化Item
			((CuViewPager) view).addView(viewlists.get(position), 0);
			if(position == 0){
				//判断是手机注册还是邮箱注册
				final TextView menu1 = (TextView) view1.findViewById(R.id.reg_menu1);
				final TextView menu2 = (TextView) view1.findViewById(R.id.reg_menu2);
				final DeletableEditText phone = (DeletableEditText) view1.findViewById(R.id.user_name_input);
				final DeletableEditText email = (DeletableEditText) view1.findViewById(R.id.user_email_input);
				menu1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View view) {
						reg_menu = false;//手机
						menu1.setTextColor(getResources().getColor(R.color.great_red));
						menu2.setTextColor(getResources().getColor(R.color.base_black_light));
						menu1.setBackgroundResource(R.drawable.bg_login_tab);
						menu2.setBackground(null);
						phone.setVisibility(View.VISIBLE);
						email.setVisibility(View.GONE);
						clearInput(phone);
					}
					
				});
				menu2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View view) {
						reg_menu = true;//邮箱
						menu1.setTextColor(getResources().getColor(R.color.base_black_light));
						menu2.setTextColor(getResources().getColor(R.color.great_red));
						menu1.setBackground(null);
						menu2.setBackgroundResource(R.drawable.bg_login_tab);
						phone.setVisibility(View.GONE);
						email.setVisibility(View.VISIBLE);
						clearInput(email);
					}
					
				});
				((Button) view1.findViewById(R.id.personal_commit1)).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View view) {
						if (!reg_menu) {//手机号
							phoneStr = phone.getText().toString().trim();
							if (phoneStr.equals("")) {
								ToastUtil.showShort(RegisterAct.this, "请填写手机号码~");
								return;
							}else if(!RexUtil.isPhone(phoneStr)){
								ToastUtil.showShort(RegisterAct.this, "手机号码格式不正确~");
								return;
							}else{
								//判断用户是否存在
								Cursor cursor = database.rawQuery(Constant.DB.HAS_USER_PHONE, new String[]{phoneStr});
								if (cursor.getCount() > 0) {
									ToastUtil.showShort(RegisterAct.this, "当前用户已经存在了~");
									return;
								}
							}
						}else{//邮箱
							emailStr = email.getText().toString().trim();
							if (emailStr.equals("")) {
								ToastUtil.showShort(RegisterAct.this, "请填写邮箱~");
								return;
							}else if(!RexUtil.isEmail(emailStr)){
								ToastUtil.showShort(RegisterAct.this, "邮箱格式不正确~");
								return;
							}else{
								//判断用户是否存在
								Cursor cursor = database.rawQuery(Constant.DB.HAS_USER_EMAIL, new String[]{emailStr});
								if (cursor.getCount() > 0) {
									ToastUtil.showShort(RegisterAct.this, "当前用户已经存在了~");
									return;
								}
							}
						}
						//导航变化
						tv1.setTextColor(getResources().getColor(R.color.base_green));
						tv2.setTextColor(getResources().getColor(R.color.base_green));
						tv3.setTextColor(getResources().getColor(R.color.base_black_light));
						v1.setBackgroundColor(getResources().getColor(R.color.base_green));
						v2.setBackgroundColor(getResources().getColor(R.color.base_green));
						v3.setBackgroundColor(getResources().getColor(R.color.base_light));
						vPager.setCurrentItem(position + 1,true);
					}
				});
			}else if(position == 1){
				final DeletableEditText pass = (DeletableEditText) view2.findViewById(R.id.user_password_input);
				final DeletableEditText pass2 = (DeletableEditText) view2.findViewById(R.id.user_password_input2);
				((Button) view2.findViewById(R.id.personal_commit2)).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View view) {
						passStr = pass.getText().toString().trim();
						if (passStr.length() < 6 || pass.length() > 26) {
							ToastUtil.showShort(RegisterAct.this, "请填写6~25位密码~");
							return;
						}else if(RexUtil.isNumeric(passStr)){
							ToastUtil.showShort(RegisterAct.this, "密码拒绝全部为数字~");
							clearInput(pass,pass2);
							return;
						}else if(!pass2.getText().toString().trim().equals(passStr)){
							ToastUtil.showShort(RegisterAct.this, "两次密码不一致~");
							clearInput(pass2);
							return;
						}else if (!reg_menu) {//手机号
							//手机注册
							ContentValues values = new ContentValues();
							values.put("phone", phoneStr);
							values.put("password", passStr);
							id = database.insert(Constant.DB.USER_TABLE_NAME, "id", values);
							ShareDataHelper.getInstance(RegisterAct.this).saveUser("uerId", String.valueOf(id));
							ShareDataHelper.getInstance(RegisterAct.this).saveUser("login", phoneStr);
						}else if(reg_menu){//邮箱
							//邮箱注册
							ContentValues values = new ContentValues();
							values.put("email", emailStr);
							values.put("password", passStr);
							id = database.insert(Constant.DB.USER_TABLE_NAME, "id", values);
							ShareDataHelper.getInstance(RegisterAct.this).saveUser("uerId", String.valueOf(id));
							ShareDataHelper.getInstance(RegisterAct.this).saveUser("login", emailStr);
						}
						//导航变化
						tv1.setTextColor(getResources().getColor(R.color.base_green));
						tv2.setTextColor(getResources().getColor(R.color.base_green));
						tv3.setTextColor(getResources().getColor(R.color.base_green));
						v1.setBackgroundColor(getResources().getColor(R.color.base_green));
						v2.setBackgroundColor(getResources().getColor(R.color.base_green));
						v3.setBackgroundColor(getResources().getColor(R.color.base_green));
						vPager.setCurrentItem(position + 1,true);
					}
				});
				
			}else if(position == 2){
				//完善信息
				image = (ImageView) view3.findViewById(R.id.personal_icon_content);
				final EditText name = (EditText) view3.findViewById(R.id.personal_name);
				final EditText nice = (EditText) view3.findViewById(R.id.personal_nice);
				final EditText age = (EditText) view3.findViewById(R.id.personal_age);
				final TextView star = (TextView) view3.findViewById(R.id.personal_star);
				final ToggleButton sex = (ToggleButton) view3.findViewById(R.id.personal_sex);
				final EditText like = (EditText) view3.findViewById(R.id.personal_like);
				final EditText info = (EditText) view3.findViewById(R.id.personal_info);
				//添加监听事件
				image.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View view) {
						showAlbumDialog();
					}
				});
				final LayoutInflater inflater = (LayoutInflater) RegisterAct.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				//跳过
				((Button) view3.findViewById(R.id.personal_commit31)).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View view) {
						View contentView = inflater.inflate(R.layout.view_register_sky_layout, null);
						((TextView) contentView.findViewById(R.id.tv_name)).setText(reg_menu?emailStr:phoneStr);
						((TextView) contentView.findViewById(R.id.tv_pass)).setText(passStr);
						CuAlertDialog dialog = new CuAlertDialog.Builder(RegisterAct.this)
								.setContentView(contentView)
								.setPositiveButton("确定", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										dialog.dismiss(); 
										finishPage();
										startActivity(new Intent(RegisterAct.this,UserInfoAct.class));
									}
								})
								.setNegativeButton("取消", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										dialog.dismiss();
									}
								})
								.create();
						dialog.show();
						dialog.setCanceledOnTouchOutside(false);
					}
				});
				//下一步
				((Button) view3.findViewById(R.id.personal_commit32)).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View view) {
						//验证输入的信息
						ContentValues values = new ContentValues();
						if(iconUrl != null && iconUrl.length() > 6){
							values.put("imageUri", iconUrl.substring(iconUrl.lastIndexOf("/")+1));
						}
						if (name.getText().toString().trim().length()>0) {
							values.put("name", name.getText().toString().trim());
						}
						if (nice.getText().toString().trim().length()>0) {
							values.put("nice", nice.getText().toString().trim());
						}
						if (age.getText().toString().trim().length()>0) {
							values.put("age", Integer.parseInt(age.getText().toString().trim()));
						}
						if (star.getText().toString().trim().length()>0) {
							values.put("star", star.getText().toString().trim());
						}
						values.put("sex", sex.isChecked()?"fama":"male");
						if (like.getText().toString().trim().length()>0) {
							values.put("like", like.getText().toString().trim());
						}
						if (info.getText().toString().trim().length()>0) {
							values.put("info", info.getText().toString().trim());
						}
						if (id >0 && values.size() >0) {
							database.update(Constant.DB.USER_TABLE_NAME, values, "id=?", new String[]{String.valueOf(id)});
						}
						View contentView = inflater.inflate(R.layout.view_register_ok_layout, null);
						((TextView) contentView.findViewById(R.id.tv_name)).setText(reg_menu?emailStr:phoneStr);
						((TextView) contentView.findViewById(R.id.tv_pass)).setText(passStr);
						
						CuAlertDialog dialog = new CuAlertDialog.Builder(RegisterAct.this)
								.setContentView(contentView)
								.setPositiveButton("确定", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										dialog.dismiss(); 
										finishPage();
										startActivity(new Intent(RegisterAct.this,UserInfoAct.class));
									}
								})
								.setNegativeButton("取消", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										dialog.dismiss();
									}
								})
								.create();
						dialog.show();
						dialog.setCanceledOnTouchOutside(false);
					}
				});
			}
			return viewlists.get(position);
		}
		
		//显示选择头像的弹出框
		private void showAlbumDialog(){
			albumDialog = new AlertDialog.Builder(RegisterAct.this).create();
			albumDialog.setCanceledOnTouchOutside(false);
			View v = LayoutInflater.from(RegisterAct.this).inflate(R.layout.dialog_chose_image_layout, null);
			albumDialog.show();
			albumDialog.setContentView(v);
			albumDialog.getWindow().setGravity(Gravity.CENTER);
			
			TextView albumPic = (TextView)v.findViewById(R.id.album_pic);
			TextView cameraPic = (TextView)v.findViewById(R.id.camera_pic);
			albumPic.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					albumDialog.dismiss();
					Date date1 = new Date(System.currentTimeMillis());
					dateTime = date1.getTime() + "";
					getAvataFromAlbum();
				}
			});
			cameraPic.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					albumDialog.dismiss();
					Date date = new Date(System.currentTimeMillis());
					dateTime = date.getTime() + "";
					getAvataFromCamera();
				}
			});
		}
		
		private void getAvataFromAlbum(){
			Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
			intent2.setType("image/*");
			startActivityForResult(intent2, 2);
		}
		
		private void getAvataFromCamera(){
			File f = new File(FileUtil.getCacheDirectory(RegisterAct.this, true, "icon") + dateTime);
			if (f.exists()) {
				f.delete();
			}
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Uri uri = Uri.fromFile(f);			
			Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
			startActivityForResult(camera, 1);
		}
		
		

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		String files = null ;
		if(resultCode == RegisterAct.RESULT_OK){
			switch (requestCode) {
			case 1:
				files =FileUtil.getCacheDirectory(RegisterAct.this, true, "icon") + dateTime;
				File file = new File(files);
				if(file.exists()&&file.length() > 0){
					Uri uri = Uri.fromFile(file);
					startPhotoZoom(uri);
				}else{
					
				}
				break;
			case 2:
				if (data == null) {
					return;
				}
				startPhotoZoom(data.getData());
				break;
			case 3:
				if (data != null) {
					Bundle extras = data.getExtras();
					if (extras != null) {
						Bitmap bitmap = extras.getParcelable("data");
						// 将图片存到SdCard
						iconUrl = saveToSdCard(bitmap);
						image.setImageBitmap(bitmap);
					}
				}
				break;
			default:
				break;
			}
		}
	}
	
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 190);
		intent.putExtra("outputY", 190);
		intent.putExtra("crop", "true");
		intent.putExtra("scale", true);
		intent.putExtra("scaleUpIfNeeded", true);
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);

	}
	
	public String saveToSdCard(Bitmap bitmap){
		String files =FileUtil.getCacheDirectory(RegisterAct.this, true, "icon") + dateTime+"_12";
		File file=new File(files);
        try {
            FileOutputStream out=new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)){
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
	}
	
	/**清空文本框*/
	private void clearInput(DeletableEditText... inputs){
		for (int i = 0; i < inputs.length; i++) {
			inputs[i].setText("");
		}
	}
}
