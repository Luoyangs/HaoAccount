package com.haoxue.haoaccount.view;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.act.AddIncomeAct;
import com.haoxue.haoaccount.act.AddOutcomeAct;
import com.haoxue.haoaccount.act.MoreTypeAct;
import com.haoxue.haoaccount.act.PrepayAct;
import com.haoxue.haoaccount.act.TestAct;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 说明:圆形菜单
 * 作者:Luoyangs
 * 时间:2015-10-5
 */

public class RadialButtonLayout extends FrameLayout {

    private final static long DURATION_SHORT = 400;

    @ViewInject(R.id.btn_main)
    private View btnMain;
    @ViewInject(R.id.btn_orange)
    private View btnOrange;
    @ViewInject(R.id.btn_yellow)
    private View btnYellow;
    @ViewInject(R.id.btn_green)
    private View btnGreen;
    @ViewInject(R.id.btn_blue)
    private View btnBlue;
    @ViewInject(R.id.btn_indigo)
    private View btnIndigo;

    private Context context;
    private boolean isOpen = false;
    
    /**
     * Default constructor
     * @param context
     */
    public RadialButtonLayout(final Context context) {
        this(context, null);
    }

    public RadialButtonLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadialButtonLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate( R.layout.layout_radial_buttons, this);
        this.context = context;
        if (isInEditMode()) {
            //
        } else{
            ViewUtils.inject(this);
            /*show(btnOrange, 1, 300);
            show(btnYellow, 2, 300);
            show(btnGreen, 3, 300);
            show(btnBlue, 4, 300);
            show(btnIndigo, 5, 300);
            isOpen = true;*/
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isInEditMode()) {
            //
        } else {

        }
    }

    @OnClick( R.id.btn_main )
    public void onMainButtonClicked( final View btn ) {
        if ( isOpen ) {
            // close
        	close();
        } else {
            show(btnOrange, 1, 300);
            show(btnYellow, 2, 300);
            show(btnGreen, 3, 300);
            show(btnBlue, 4, 300);
            show(btnIndigo, 5, 300);
            isOpen = true;
        }
        btn.playSoundEffect( SoundEffectConstants.CLICK);
    }

    private void close(){
    	hide(btnOrange);
        hide(btnYellow);
        hide(btnGreen);
        hide(btnBlue);
        hide(btnIndigo);
        isOpen = false;
    }
    
    @OnClick({ R.id.btn_orange, R.id.btn_yellow, R.id.btn_green, R.id.btn_blue, R.id.btn_indigo})
    public void onButtonClicked( final View btn ) {
        switch ( btn.getId() ) {
            case R.id.btn_orange:
            	Intent intent = new Intent(getContext(),AddIncomeAct.class);
            	intent.putExtra("isEdit", 0);
                context.startActivity(intent);
                break;
            case R.id.btn_yellow:
            	context.startActivity(new Intent(getContext(),AddOutcomeAct.class));
                break;
            case R.id.btn_green:
            	context.startActivity(new Intent(getContext(),PrepayAct.class));
                break;
            case R.id.btn_blue:
            	context.startActivity(new Intent(getContext(),TestAct.class));
                break;
            case R.id.btn_indigo:
            	context.startActivity(new Intent(getContext(),MoreTypeAct.class));
                break;
            default:
            	break;
        }
        close();
        btn.playSoundEffect( SoundEffectConstants.CLICK);
    }

    private final void hide( final View child) {
        child.animate()
                .setDuration(DURATION_SHORT)
                .translationX(0)
                .translationY(0)
                .start();
    }

    private final void show(final View child, final int position, final int radius) {
        float angleDeg = 180.f;
        int dist = radius;
        switch (position) {
            case 1:
                angleDeg += 0.f;
                break;
            case 2:
                angleDeg += 45.f;
                break;
            case 3:
                angleDeg += 90.f;
                break;
            case 4:
                angleDeg += 135.f;
                break;
            case 5:
                angleDeg += 180.f;
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                break;
        }

        final float angleRad = (float) (angleDeg * Math.PI / 180.f);

        final Float x = dist * (float) Math.cos(angleRad);
        final Float y = dist * (float) Math.sin(angleRad);
        child.animate()
                .setDuration(DURATION_SHORT)
                .translationX(x)
                .translationY(y)
                .start();
    }
}

