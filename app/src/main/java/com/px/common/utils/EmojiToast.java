package com.px.common.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.px.common.R;


/**
 * emoji toast
 */

public class EmojiToast {

    private static TextView textView;
    public static final int EMOJI_SAD = 1;
    public static final int EMOJI_SMILE = 2;

    public static void show(String message ,int emot){
        Context context = CommonApplication.context;
        View toastView = LayoutInflater.from(context).inflate(R.layout.c_toast, null);
        textView = (TextView) toastView.findViewById(R.id.tvToast);
        textView.setText(message);
        Drawable drawable = null;
        if(emot == EMOJI_SAD){
            drawable = context.getResources().getDrawable(R.drawable.c_ic_sad_face);
        }else{
            drawable = context.getResources().getDrawable(R.drawable.c_ic_smile_face);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable,null, null, null);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastView);
        toast.show();
    }
}
