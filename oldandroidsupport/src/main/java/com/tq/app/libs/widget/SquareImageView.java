/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. Dotohsoft.com. All right reversed
 *  Author TrinhQuan. Create on 2016/4/29
 * ******************************************************************************
 */

package com.tq.app.libs.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.tq.app.libs.R;

public class SquareImageView extends AppCompatImageView {
    private static final int DEFAULT_RELATIVE = 0; //0 width, 1 height
    private int mRelative = DEFAULT_RELATIVE;

    public SquareImageView(Context context) {
        this(context, null);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadStateFromAttrs(attrs);
    }

    private void loadStateFromAttrs(AttributeSet attributeSet) {
        if (attributeSet == null) {
            return;
        }
        TypedArray a = null;
        try {
            a = getContext().obtainStyledAttributes(attributeSet, R.styleable.SquareView);
            mRelative = a.getInteger(R.styleable.SquareView_relative_to,
                    DEFAULT_RELATIVE);
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mRelative == 0) {
            setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
        } else {
            setMeasuredDimension(getMeasuredHeight(), getMeasuredHeight());
        }
    }
}
