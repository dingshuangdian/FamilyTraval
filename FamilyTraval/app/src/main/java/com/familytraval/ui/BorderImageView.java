package com.familytraval.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.familytraval.R;

/**
 * Created by dings on 2016/10/26.
 */

public class BorderImageView extends ImageView {

    private int color;

    public BorderImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        /** 获取自定义属性 titleText */
        TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BorderImageView, 0, 0);
        color = typeArray.getColor(R.styleable.BorderImageView_borderColor, context.getResources().getColor(R.color.separator_line));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画边框
        Rect rec = canvas.getClipBounds();
        rec.bottom--;
        rec.right--;
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rec, paint);
    }
}

