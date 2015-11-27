package com.haoxue.haoaccount.act;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.pedant.SweetAlert.SweetAlertDialog.OnSweetClickListener;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.adapter.MusicAdapter;
import com.haoxue.haoaccount.adapter.PicAdapter;
import com.haoxue.haoaccount.base.Constant.PSV;
import com.haoxue.haoaccount.bean.ImageItem;
import com.haoxue.haoaccount.util.Bimp;
import com.haoxue.haoaccount.util.FileUtil;
import com.haoxue.haoaccount.view.CuGridView;
import com.haoxue.haoaccount.view.CuListView;
import com.haoxue.haoaccount.view.DeletableEditText;
import com.haoxue.haoaccount.view.NoteEditText;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 说明：添加&编辑记事
 * 作者：Luoyangs
 * 时间：2015-11-6
 */
@ContentView(R.layout.act_addnote_layout)
public class AddNoteAct extends Activity implements OnClickListener{
	
	@ViewInject(R.id.txttitle)
	private DeletableEditText title;
	@ViewInject(R.id.sv_content)
	private ScrollView sv_content;
	@ViewInject(R.id.note_edit_view)
	private NoteEditText content;
	@ViewInject(R.id.pics)
	private CuGridView pics;
	@ViewInject(R.id.yinlist)
	private CuListView yinlist;
	@ViewInject(R.id.vediolist)
	private CuListView vediolist;
	@ViewInject(R.id.more_text)
	private TextView more_text;
	@ViewInject(R.id.pics_tip)
	private TextView pics_tip;
	@ViewInject(R.id.v1)
	private View v1;
	@ViewInject(R.id.piclay)
	private LinearLayout piclay;
	private PopupWindow popwind;
	private AlertDialog albumDialog;
	private PicAdapter picAdapter;
	private MusicAdapter musicAdapter,vedioAdapter;
	private MediaPlayer myPlayer;
	public static List<String> musicList = new ArrayList<String>();//音频文件
	private List<String> vedios;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		musicList.clear();
		vedios = new ArrayList<String>();
		myPlayer = new MediaPlayer();
	}
	
	@SuppressWarnings("deprecation")
	@OnClick(R.id.savemenu)
	public void onSave(View view){
		//弹出保存页面
		View popview = getLayoutInflater().inflate(R.layout.pop_savemenu_layout, null,false);
		popwind = new PopupWindow(popview, view.getWidth(), 330, true);
		popwind.setBackgroundDrawable(new BitmapDrawable());
		popwind.showAsDropDown(view);
		popwind.setOutsideTouchable(true);
		popwind.setFocusable(true);
		((TextView) popview.findViewById(R.id.pop11)).setOnClickListener(this);
		((TextView) popview.findViewById(R.id.pop12)).setOnClickListener(this);
	}
	
	@SuppressWarnings("deprecation")
	@OnClick(R.id.bgmenu)
	public void onBgColor(View view){
		//弹出保存页面
		View popview = getLayoutInflater().inflate(R.layout.pop_bgcolormenu_layout, null,false);
		popwind = new PopupWindow(popview, view.getWidth(), 740, true);
		popwind.setBackgroundDrawable(new BitmapDrawable());
		popwind.showAsDropDown(view);
		popwind.setOutsideTouchable(true);
		popwind.setFocusable(true);
		((TextView) popview.findViewById(R.id.pop1)).setOnClickListener(this);
		((TextView) popview.findViewById(R.id.pop2)).setOnClickListener(this);
		((TextView) popview.findViewById(R.id.pop3)).setOnClickListener(this);
		((TextView) popview.findViewById(R.id.pop4)).setOnClickListener(this);
		((TextView) popview.findViewById(R.id.pop5)).setOnClickListener(this);
	}
	
	@SuppressWarnings("deprecation")
	@OnClick(R.id.moremenu)
	public void onMore(View view){
		//弹出附件页面
		View popview = getLayoutInflater().inflate(R.layout.pop_attachmenu_layout, null,false);
		popwind = new PopupWindow(popview, view.getWidth(), 460, true);
		popwind.setBackgroundDrawable(new BitmapDrawable());
		popwind.showAsDropDown(view);
		popwind.setOutsideTouchable(true);
		popwind.setFocusable(true);
		((TextView) popview.findViewById(R.id.pop21)).setOnClickListener(this);
		((TextView) popview.findViewById(R.id.pop22)).setOnClickListener(this);
		((TextView) popview.findViewById(R.id.pop23)).setOnClickListener(this);
	}

	@OnClick(R.id.titilbar_left)
	public void onBack(View view){
		this.finishPage();
	}
	
	private void finishPage(){
		this.finish();
		overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.pop1:
			sv_content.setBackgroundResource(R.drawable.edit_blue);
			break;
		case R.id.pop2:
			sv_content.setBackgroundResource(R.drawable.edit_green);
			break;
		case R.id.pop3:
			sv_content.setBackgroundResource(R.drawable.edit_yellow);
			break;
		case R.id.pop4:
			sv_content.setBackgroundResource(R.drawable.edit_red);
			break;
		case R.id.pop5:
			sv_content.setBackgroundResource(R.drawable.edit_white);
			break;
		case R.id.pop11:
			
			break;
		case R.id.pop12:
					
			break;
		case R.id.pop21:
			more_text.setVisibility(View.VISIBLE);
			piclay.setVisibility(View.VISIBLE);
			picAdapter = new PicAdapter(AddNoteAct.this);
			pics.setAdapter(picAdapter);
			pics.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3) {
					if (position == Bimp.tempSelectBitmap.size()) {
						showAlbumDialog();
					}else{//大图显示
						Intent intent = new Intent(AddNoteAct.this,ShowImageAct.class);
						intent.putExtra("type", "BITMAP");
						intent.putExtra("imgurl", Bimp.tempSelectBitmap.get(position).getBitmap());
						startActivity(intent);
					}
				}
			});
			//长按删除图片
			pics.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
					if (position == Bimp.tempSelectBitmap.size()) {
						return false;
					}
					final SweetAlertDialog dialog = new SweetAlertDialog(AddNoteAct.this, SweetAlertDialog.WARNING_TYPE).setTitleText("确定删除么?");
					dialog.setTitleText("要删除图片么?").setContentText("删除之后还可以选择新的图片!");
					dialog.setConfirmText("是，删除!").setCanceledOnTouchOutside(true);
					dialog.setConfirmClickListener(new OnSweetClickListener() {
						
						@Override
						public void onClick(SweetAlertDialog sweetAlertDialog) {
							sweetAlertDialog.setTitleText("已删除!")
			                	.setContentText("你又有位置选择新的图片~")
			                	.setConfirmText("确定")
			                	.setConfirmClickListener(null)
			                	.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
							Bimp.tempSelectBitmap.remove(position);
							picAdapter.notifyDataSetChanged();
						}
					}).show();
					return true;
				}
			});
			break;
		case R.id.pop22://录制音频
			startActivity(new Intent(AddNoteAct.this,AddRecordAct.class));
			musicAdapter = new MusicAdapter(musicList,AddNoteAct.this,"MUSIC");
			yinlist.setAdapter(musicAdapter);
			yinlist.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,int postion, long arg3) {
					if (!myPlayer.isPlaying()) {
						try {
							myPlayer.reset();
							myPlayer.setDataSource(musicList.get(postion));
							myPlayer.prepare();
							myPlayer.start();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}else{
						myPlayer.stop();
					}
				}
			});
			yinlist.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
					final SweetAlertDialog dialog = new SweetAlertDialog(AddNoteAct.this, SweetAlertDialog.WARNING_TYPE).setTitleText("确定删除么?");
					dialog.setTitleText("要删除录音么?").setContentText("删除之后还可以添加新的录音!");
					dialog.setConfirmText("是，删除!").setCanceledOnTouchOutside(true);
					dialog.setConfirmClickListener(new OnSweetClickListener() {
						
						@Override
						public void onClick(SweetAlertDialog sweetAlertDialog) {
							sweetAlertDialog.setTitleText("已删除!")
			                	.setContentText("你又有位置选择新的录音~")
			                	.setConfirmText("确定")
			                	.setConfirmClickListener(null)
			                	.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
							String FilePath = musicList.get(position);
							if (FilePath != null) {
								File oldFile = new File(FilePath);
								oldFile.delete();
							}
							musicList.remove(position);
							if (musicList.size() == 1) {// 方便显示
								musicList.add("");
							} else {
								musicList.remove("");
							}
							musicAdapter.notifyDataSetChanged();
						}
					}).show();
					return true;
				}
			});
			break;
		case R.id.pop23://录制视频
			Intent intent24 = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
			intent24.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
			startActivityForResult(intent24, PSV.REQUEST_CODE_VIO);
			break;
		}
		popwind.dismiss();
	}
	
	//显示选择头像的弹出框
	private void showAlbumDialog() {
		albumDialog = new AlertDialog.Builder(AddNoteAct.this).create();
		albumDialog.setCanceledOnTouchOutside(false);
		View v = LayoutInflater.from(AddNoteAct.this).inflate(R.layout.cu_dialog_choseimage_layout, null);
		albumDialog.show();
		albumDialog.setContentView(v);
		albumDialog.getWindow().setGravity(Gravity.CENTER);

		TextView albumPic = (TextView) v.findViewById(R.id.album_pic);
		TextView cameraPic = (TextView) v.findViewById(R.id.camera_pic);
		albumPic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				albumDialog.dismiss();
				startActivity(new Intent(AddNoteAct.this,AlbumAct.class));
			}
		});
		cameraPic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				albumDialog.dismiss();
				Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(camera, PSV.REQUEST_CODE_PIC);
			}
		});
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PSV.REQUEST_CODE_PIC && Bimp.tempSelectBitmap.size() < 8 && resultCode == RESULT_OK) {
			String fileName = String.valueOf(System.currentTimeMillis());
			Bitmap bm = (Bitmap) data.getExtras().get("data");
			FileUtil.saveBitmap(bm, fileName);
			
			ImageItem takePhoto = new ImageItem();
			takePhoto.setBitmap(bm);
			Bimp.tempSelectBitmap.add(takePhoto);
		}else if(requestCode == PSV.REQUEST_CODE_VIO && resultCode == RESULT_OK){
			Uri uriVideo = data.getData();
			Cursor cursor = this.getContentResolver().query(uriVideo, null, null, null, null);
			if (cursor.moveToNext()) {
				String strVideoPath = cursor.getString(cursor .getColumnIndex("_data"));
				vedios.add(strVideoPath);
				//显示列表
				more_text.setVisibility(View.VISIBLE);
				vediolist.setVisibility(View.VISIBLE);
				vedioAdapter = new MusicAdapter(vedios,AddNoteAct.this,"VEDIO");
				vediolist.setAdapter(vedioAdapter);
				//点击查看视频
				vediolist.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
						Intent intent = new Intent(AddNoteAct.this,PlayVedioAct.class);
						startActivity(intent);
					}
				});
				vediolist.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

						return false;
					}
				});
			}
		}
	}
	
	@Override
	protected void onResume() {
		//更新Adapter
		if(picAdapter != null && piclay.VISIBLE == View.VISIBLE){
			picAdapter.notifyDataSetChanged();
		}
		if(musicAdapter != null && musicList.size()>0){
			//显示已经存在的音频文件
			if (musicList.size() == 1) {//方便显示
				musicList.add("");
			}else{
				musicList.remove("");
			}
			more_text.setVisibility(View.VISIBLE);
			yinlist.setVisibility(View.VISIBLE);
			musicAdapter.notifyDataSetChanged();
		}
		if(vedioAdapter != null && vedios.size()>0){
			//显示已经存在的音频文件
			if (vedios.size() == 1) {//方便显示
				vedios.add("");
			}else{
				vedios.remove("");
			}
			if (yinlist.VISIBLE == View.VISIBLE) {
				v1.setVisibility(View.VISIBLE);
			}
			vedioAdapter.notifyDataSetChanged();
		}
		super.onResume();
	}
}
