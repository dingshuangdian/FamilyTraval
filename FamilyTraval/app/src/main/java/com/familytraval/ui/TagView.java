package com.familytraval.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ToggleButton;

import com.familytraval.R;

/**
 * Created by dings on 2016/10/25.
 */

public class TagView extends ToggleButton {

    private boolean mCheckEnable = true;
    private boolean mSelected = false;

    public TagView(Context paramContext) {
        super(paramContext);
        init();
    }

    public TagView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init();
    }

    public TagView(Context paramContext, AttributeSet paramAttributeSet,
                   int paramInt) {
        super(paramContext, paramAttributeSet, 0);
        init();
    }

    private void init() {
        setTextOn(null);
        setTextOff(null);
        setText("");
        setBackgroundResource(R.drawable.tag_button_bg_unchecked);
    }

    public void setCheckEnable(boolean paramBoolean) {
        this.mCheckEnable = paramBoolean;
        if (!this.mCheckEnable) {
            super.setChecked(false);
        }
    }

    public void setSelected(boolean isSelected) {
        if (isSelected) {
            setBackgroundResource(R.drawable.tag_button_bg_checked);
        } else {
            setBackgroundResource(R.drawable.tag_button_bg_unchecked);
        }
    }
    /*public void setChecked(boolean paramBoolean) {
		if (this.mCheckEnable) {
			super.setChecked(paramBoolean);
			setBackgroundResource(R.drawable.tag_button_bg_checked);
		}else{
			setBackgroundResource(R.drawable.tag_button_bg_unchecked);
		}

	}*/
}

