package com.haoxue.haoaccount.act;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.util.ToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 说明：开始录音 
 * 作者：Luoyangs 
 * 时间：2015-11-10
 */
@SuppressLint({ "SdCardPath", "SimpleDateFormat" })
@ContentView(R.layout.act_addrecord_layout)
public class AddRecordAct extends Activity {

	@ViewInject(R.id.bt_back)
	private Button btn_back;
	@ViewInject(R.id.bt_save)
	private TextView btn_save;
	@ViewInject(R.id.btn_record)
	private Button btn_record;
	@ViewInject(R.id.tv_recordTime)
	private TextView tv_recordTime;
	@ViewInject(R.id.iv_microphone)
	private ImageView iv_microphone;
	@ViewInject(R.id.iv_record_wave_left)
	private ImageView iv_record_wave_left;
	@ViewInject(R.id.iv_record_wave_right)
	private ImageView iv_record_wave_right;
	private AnimationDrawable ad_left, ad_right;
	private int isRecording = 0;
	private int isPlaying = 0;

	private Timer mTimer;
	// 语音操作对象
	private MediaPlayer mPlayer = null;
	private MediaRecorder mRecorder = null;
	// 语音保存路径
	private String FilePath = "";
	
	@SuppressLint("HandlerLeak")
	private final Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch(msg.what){
			    case 1 :
			    	String time[] = tv_recordTime.getText().toString().split(":");
					int hour = Integer.parseInt(time[0]);
					int minute = Integer.parseInt(time[1]);
					int second = Integer.parseInt(time[2]);
					if(second < 59){
						second++;
					}
					else if(second == 59 && minute < 59){
						minute++;
						second = 0;
					}
					if(second == 59 && minute == 59 && hour < 98){
						hour++;
						minute = 0;
						second = 0;
					}
					time[0] = hour + "";
					time[1] = minute + "";
					time[2] = second + "";
					//调整格式显示到屏幕上
					if(second < 10)
						time[2] = "0" + second;
					if(minute < 10)
						time[1] = "0" + minute;
					if(hour < 10)
						time[0] = "0" + hour;
					//显示在TextView中
					tv_recordTime.setText(time[0]+":"+time[1]+":"+time[2]);
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ViewUtils.inject(this);
		ad_left = (AnimationDrawable) iv_record_wave_left.getBackground();
		ad_right = (AnimationDrawable) iv_record_wave_right.getBackground();
		
		btn_save.setOnClickListener(new ClickEvent());
		btn_back.setOnClickListener(new ClickEvent());
		btn_record.setOnClickListener(new ClickEvent());
		iv_microphone.setOnClickListener(new ClickEvent());
	}

	private class ClickEvent implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			// 点击的是开始录音按钮
			case R.id.btn_record:
				// 开始录音
				if (isRecording == 0) {
					// 每一次调用录音，可以录音多次，至多满意为至，最后只将最后一次的录音文件保存，其他的删除
					if (FilePath != null) {
						File oldFile = new File(FilePath);
						oldFile.delete();
					}
					// 获得系统当前时间，并以该时间作为文件名
					SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
					Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
					String str = formatter.format(curDate);
					str = str + "record.amr";
					File dir = new File("/sdcard/notes/");
					File file = new File("/sdcard/notes/", str);
					if (!dir.exists()) {
						dir.mkdir();
					} else {
						if (file.exists()) {
							file.delete();
						}
					}

					FilePath = dir.getPath() + "/" + str;
					// 计时器
					mTimer = new Timer();
					// 将麦克图标设置成不可点击，
					iv_microphone.setClickable(false);
					// 将显示的时间设置为00:00:00
					tv_recordTime.setText("00:00:00");
					// 将按钮换成停止录音
					isRecording = 1;
					btn_record.setBackgroundResource(R.drawable.tabbar_record_stop);

					mRecorder = new MediaRecorder();
					mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
					mRecorder.setOutputFile(FilePath);
					mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

					try {
						mRecorder.prepare();
					} catch (Exception e) {
						e.printStackTrace();
					}
					mRecorder.start();
					mTimer.schedule(new TimerTask() {

						@Override
						public void run() {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					}, 1000, 1000);
					// 播放动画
					ad_left.start();
					ad_right.start();
				}
				// 停止录音
				else {
					// 将按钮换成开始录音
					isRecording = 0;
					btn_record
							.setBackgroundResource(R.drawable.tabbar_record_start);
					mRecorder.stop();
					mTimer.cancel();
					mTimer = null;

					mRecorder.release();
					mRecorder = null;

					// 将麦克图标设置成可点击，
					iv_microphone.setClickable(true);
					// 停止动画
					ad_left.stop();
					ad_right.stop();
					Toast.makeText(AddRecordAct.this, "单击麦克图标试听，再次点击结束试听", Toast.LENGTH_LONG).show();
				}
				break;
			// 如果单击的是麦克图标，则可以是进入试听模式，再次点击，停止播放
			case R.id.iv_microphone:
				if (FilePath == null)
					Toast.makeText(AddRecordAct.this, "没有录音广播可以播放，请先录音", Toast.LENGTH_LONG).show();
				else {
					// 试听
					if (isPlaying == 0) {
						isPlaying = 1;
						mPlayer = new MediaPlayer();
						tv_recordTime.setText("00:00:00");
						mTimer = new Timer();
						mPlayer.setOnCompletionListener(new MediaCompletion());
						try {
							mPlayer.setDataSource(FilePath);
							mPlayer.prepare();
							mPlayer.start();
						}catch (Exception e) {
							e.printStackTrace();
						}
						mTimer.schedule(new TimerTask() {

							@Override
							public void run() {
								Message message = new Message();
								message.what = 1;
								handler.sendMessage(message);

							}
						}, 1000, 1000);

						// 播放动画
						ad_left.start();
						ad_right.start();
					}
					// 结束试听
					else {
						isPlaying = 0;
						mPlayer.stop();
						mPlayer.release();
						mPlayer = null;
						mTimer.cancel();
						mTimer = null;
						// 停止动画
						ad_left.stop();
						ad_right.stop();
					}
				}
				break;

			// 点击确定按钮
			case R.id.bt_save:
				// 将最终的录音文件的路径返回
				if (FilePath == null || FilePath.length() == 0) {
					AddRecordAct.this.finish();
					break;
				}
				AddNoteAct.musicList.add(FilePath);
				String musicName = FilePath.substring(FilePath.lastIndexOf("/")+1);
				ToastUtil.showLong(AddRecordAct.this, "JJ:"+FilePath);
				AddRecordAct.this.finish();
				break;
			case R.id.bt_back:
				// 返回前将录音的文件删除
				if (FilePath != null) {
					File oldFile = new File(FilePath);
					oldFile.delete();
				}
				AddRecordAct.this.finish();
				ToastUtil.showLong(AddRecordAct.this, "录音文件已删除~");
				break;
			}
		}

	}

	private class MediaCompletion implements OnCompletionListener {

		@Override
		public void onCompletion(MediaPlayer mp) {
			mTimer.cancel();
			mTimer = null;
			isPlaying = 0;
			// 停止动画
			ad_left.stop();
			ad_right.stop();
			Toast.makeText(AddRecordAct.this, "播放完毕", Toast.LENGTH_LONG).show();
			tv_recordTime.setText("00:00:00");
		}

	}
}
