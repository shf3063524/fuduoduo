package com.fdw.fdd.kefu;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.fdw.fdd.R;
import com.fdw.fdd.base.BaseActivity;
import com.hyphenate.chat.Message;

/**
 * long click menu
 *
 */
public class ContextMenuActivity extends BaseActivity {

    public static final int RESULT_CODE_COPY = 1;
    public static final int RESULT_CODE_DELETE = 2;

    @Override
    protected int attachLayoutRes() {
        return 0;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Message message = getIntent().getParcelableExtra("message");

        int type = message.getType().ordinal();
        if (type == Message.Type.TXT.ordinal()){
            setContentView(R.layout.em_context_menu_for_text);
        }else if (type == Message.Type.IMAGE.ordinal()){
            setContentView(R.layout.em_context_menu_for_image);
        }else if (type == Message.Type.LOCATION.ordinal()){
            setContentView(R.layout.em_context_menu_for_location);
        }else if (type == Message.Type.VOICE.ordinal()){
            setContentView(R.layout.em_context_menu_for_voice);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }


    public void copy(View view){
        setResult(RESULT_CODE_COPY);
        finish();
    }

    public void delete(View view){
        setResult(RESULT_CODE_DELETE);
        finish();
    }

}
