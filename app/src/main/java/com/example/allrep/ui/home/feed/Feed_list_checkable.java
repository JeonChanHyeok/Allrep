package com.example.allrep.ui.home.feed;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;

import com.example.allrep.R;

import java.text.AttributedCharacterIterator;

public class Feed_list_checkable extends LinearLayout implements Checkable {
    public Feed_list_checkable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setChecked(boolean b) {
        CheckBox cb = (CheckBox) findViewById(R.id.check) ;

        if (cb.isChecked() != b) {
            cb.setChecked(b) ;
        }

        // CheckBox 가 아닌 View의 상태 변경.
    }

    @Override
    public boolean isChecked() {
        CheckBox ch = (CheckBox) findViewById(R.id.check);
        return ch.isChecked();
    }

    @Override
    public void toggle() {
        CheckBox cb = (CheckBox) findViewById(R.id.check) ;

        setChecked(cb.isChecked() ? false : true) ;
    }
}
