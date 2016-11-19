/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.widget.drawale;

import android.graphics.drawable.PaintDrawable;

public class RoundCornerDrawable extends PaintDrawable {

    public RoundCornerDrawable(int color) {
        super(color);
    }

    public void setColor(int color) {
        getPaint().setColor(color);
    }

    public void setTopCornerRadius(float radius) {
        setCornerRadii(new float[]{radius, radius, radius, radius, 0, 0, 0, 0});
    }

    public void setBottomCornerRadius(float radius) {
        setCornerRadii(new float[]{0, 0, 0, 0, radius, radius, radius, radius});
    }
}
