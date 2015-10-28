package com.haoxue.haoaccount.act;

import com.haoxue.haoaccount.act.frag.MainFragment;
import com.haoxue.haoaccount.base.ActivityManager;
import com.haoxue.haoaccount.base.ShareDataHelper;
import com.haoxue.haoaccount.view.CuAlertDialog;
import com.haoxue.haoaccount.view.materialmenu.MaterialMenuDrawable;
import com.haoxue.haoaccount.view.materialmenu.MaterialMenuDrawable.Stroke;
import com.haoxue.haoaccount.view.materialmenu.MaterialMenuIcon;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import com.haoxue.haoaccount.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 *  说明：主页
 *	作者： Luoyangs
 *	时间： 2015年9月25日
 */
@ContentView(R.layout.act_main_layout)
public class MainAct extends FragmentActivity {

	/** DrawerLayout */
	@ViewInject(R.id.drawer_layout)
	private DrawerLayout mDrawerLayout;
	/** 左边栏菜单 */
	@ViewInject(R.id.left_drawer)
	private RelativeLayout left_drawer;
	@ViewInject(R.id.userImg)
	private ImageView userImg;
	@ViewInject(R.id.userName)
	private TextView userName;
	/** 右边栏 */
	@ViewInject(R.id.right_drawer)
	private RelativeLayout right_drawer;
	/** Material Design风格 */
	private MaterialMenuIcon mMaterialMenuIcon;
	/** 菜单打开/关闭状态 */
	private boolean isDirection_left = false;
	/** 右边栏打开/关闭状态 */
	private boolean isDirection_right = false;
	private View showView;
	private Fragment curFragment;// 当前面板

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		ActivityManager.getInstance().addActivity(this);
		initView(savedInstanceState);
	}
	
	private void initView(Bundle savedInstanceState){
		this.showView = left_drawer;
		//设置ActionBar背景色
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.top_bar_background));
		// 设置抽屉打开时，主要内容区被自定义阴影覆盖
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		// 设置ActionBar可见，并且切换菜单和内容视图
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mMaterialMenuIcon = new MaterialMenuIcon(this, Color.WHITE, Stroke.THIN);
		mDrawerLayout.setDrawerListener(new DrawerLayoutStateListener());
		
		if (savedInstanceState == null) {
			Fragment fragment = new MainFragment();
			switchToTargetFragment(fragment);
		}
	}
	
	@OnClick(R.id.set)
	public void onSetOnclick(View view){
		closeLeftMenu();
		startActivity(new Intent(this,SetAct.class));
	}
	
	@OnClick(R.id.pifu)
	public void setSkin(View view) {
		closeLeftMenu();
		startActivity(new Intent(MainAct.this, SetSkinAct.class));
	}
	
	@OnClick(R.id.tongzhi)
	public void setMessage(View view) {
		closeLeftMenu();
		startActivity(new Intent(MainAct.this, MessageAct.class));
	}
	
	@OnClick(R.id.exit)
	public void setExit(View view) {
		closeLeftMenu();
		View contentView = getLayoutInflater().inflate(R.layout.cu_dialog_exitapp_layout, null);
		CuAlertDialog dialog = new CuAlertDialog.Builder(MainAct.this)
		.setContentView(contentView)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss(); 
				startActivity(new Intent(MainAct.this,ExitAppAct.class));
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
	
	@OnClick(R.id.userImg)
	public void login(View view){
		if (!ShareDataHelper.getInstance(MainAct.this).hasLogin()) {
			closeLeftMenu();
			startActivity(new Intent(MainAct.this,LoginAct.class));
		}else{
			closeLeftMenu();
			startActivity(new Intent(MainAct.this,UserInfoAct.class));
		}
	}
	
	private void closeLeftMenu(){
		if (showView == left_drawer) {
			mDrawerLayout.closeDrawer(left_drawer);
		}
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
						.add(R.id.content_frame, to)
						.commit();
			} else {
				// 判断是否添加过
				if (!to.isAdded()) {
					getSupportFragmentManager()
							.beginTransaction()
							.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
							.hide(from)
							.add(R.id.content_frame, to).commit();
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
	
	/**
	 * DrawerLayout状态变化监听
	 */
	private class DrawerLayoutStateListener extends DrawerLayout.SimpleDrawerListener {
		/**
		 * 当导航菜单滑动的时候被执行
		 */
		@Override
		public void onDrawerSlide(View drawerView, float slideOffset) {
			showView = drawerView;
			if (drawerView == left_drawer) {// 根据isDirection_left决定执行动画
				mMaterialMenuIcon.setTransformationOffset(
						MaterialMenuDrawable.AnimationState.BURGER_ARROW,
						isDirection_left ? 2 - slideOffset : slideOffset);
			} /*else if (drawerView == right_drawer) {// 根据isDirection_right决定执行动画
				mMaterialMenuIcon.setTransformationOffset(
						MaterialMenuDrawable.AnimationState.BURGER_ARROW,
						isDirection_right ? 2 - slideOffset : slideOffset);
			}*/
		}

		/**
		 * 当导航菜单打开时执行
		 */
		@Override
		public void onDrawerOpened(android.view.View drawerView) {
			if (drawerView == left_drawer) {
				isDirection_left = true;
			} /*else if (drawerView == right_drawer) {
				isDirection_right = true;
			}*/
		}

		/**
		 * 当导航菜单关闭时执行
		 */
		@Override
		public void onDrawerClosed(android.view.View drawerView) {
			if (drawerView == left_drawer) {
				isDirection_left = false;
			} /*else if (drawerView == right_drawer) {
				isDirection_right = false;
				showView = left_drawer;
			}*/
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			if (showView == left_drawer) {
				if (!isDirection_left) { // 左边栏菜单关闭时，打开
					mDrawerLayout.openDrawer(left_drawer);
				} else {// 左边栏菜单打开时，关闭
					mDrawerLayout.closeDrawer(left_drawer);
				}
			} else if (showView == right_drawer) {
				if (!isDirection_right) {// 右边栏关闭时，打开
					mDrawerLayout.openDrawer(right_drawer);
				} else {// 右边栏打开时，关闭
					mDrawerLayout.closeDrawer(right_drawer);
				}
			}
			break;
		case R.id.action_personal:
			if (!isDirection_right) {// 右边栏关闭时，打开
				if (showView == left_drawer) {
					mDrawerLayout.closeDrawer(left_drawer);
				}
				mDrawerLayout.openDrawer(right_drawer);
			} else {// 右边栏打开时，关闭
				mDrawerLayout.closeDrawer(right_drawer);
			}
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 根据onPostCreate回调的状态，还原对应的icon state
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mMaterialMenuIcon.syncState(savedInstanceState);
	}

	/**
	 * 根据onSaveInstanceState回调的状态，保存当前icon state
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		mMaterialMenuIcon.onSaveInstanceState(outState);
		super.onSaveInstanceState(outState);
	}
	
	/**
	 * 加载菜单
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
