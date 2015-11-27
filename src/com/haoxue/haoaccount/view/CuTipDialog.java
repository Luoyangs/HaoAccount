package com.haoxue.haoaccount.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.haoxue.haoaccount.R;

/**
 * 说明：提示弹窗
 * 作者：Luoyangs
 * 时间：2015-11-14
 */
public class CuTipDialog extends Dialog {
	
	public CuTipDialog(Context context) {  
        super(context);  
    }  
  
    public CuTipDialog(Context context, int theme) {  
        super(context, theme);  
    }  
  
    public static class Builder {  
        private Context context;   
        private String message;
        private DialogInterface.OnClickListener positiveButtonClickListener;  
        private DialogInterface.OnClickListener negativeButtonClickListener;  
  
        public Builder(Context context) {  
            this.context = context;
        }   
  
        public Builder setMessage(String message) {  
            this.message = message;  
            return this;  
        }  
 
        public Builder setPositiveButton(DialogInterface.OnClickListener listener) {  
            this.positiveButtonClickListener = listener;  
            return this;  
        }   
  
        public Builder setNegativeButton(DialogInterface.OnClickListener listener) {  
            this.negativeButtonClickListener = listener;  
            return this;  
        }  
        
		@SuppressWarnings("deprecation")
		public CuTipDialog create() {  
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
            final CuTipDialog dialog = new CuTipDialog(context,R.style.Dialog);  
            View layout = inflater.inflate(R.layout.cu_dialog_layout, null,false);
            dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT)); 
            TextView content = (TextView) layout.findViewById(R.id.tvContent);
            content.setText(message);
            if (positiveButtonClickListener != null) {  
                ((TextView) layout.findViewById(R.id.btnOk)).setOnClickListener(new View.OnClickListener() {  
                     public void onClick(View v) {  
                         positiveButtonClickListener.onClick(dialog,DialogInterface.BUTTON_POSITIVE);  
                     }  
                });  
            }  
            if (negativeButtonClickListener != null) {  
                ((TextView) layout.findViewById(R.id.btnCal)).setOnClickListener(new View.OnClickListener() {  
                     public void onClick(View v) {  
                         negativeButtonClickListener.onClick(dialog,DialogInterface.BUTTON_NEGATIVE);  
                     }  
                });  
            }  
            dialog.show();
    		dialog.setCanceledOnTouchOutside(false);
            return dialog;  
        }  
    }  
}
