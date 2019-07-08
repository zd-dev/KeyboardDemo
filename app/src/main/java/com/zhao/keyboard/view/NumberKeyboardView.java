package com.zhao.keyboard.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

import com.zhao.keyboard.R;

import java.util.List;

/**
 * 修改Key样式
 *
 * @author zhaod
 * @date 2019/7/7
 */

public class NumberKeyboardView extends KeyboardView {

    private final int CODE_FINISH = -100;  // 确定
    private final int CODE_EMPTY = -101;   // 占位
    private Context mContext;
    private Paint mPaintBg;
    private Paint mPaint;

    public NumberKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    public NumberKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    private void initView() {
        mPaintBg = new Paint();
        mPaintBg.setAntiAlias(true);
        mPaintBg.setDither(true);
        mPaintBg.setStyle(Paint.Style.FILL);
        mPaintBg.setColor(getResources().getColor(R.color.color_key_green));

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(60);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Keyboard mKeyboard = getKeyboard();

        List<Keyboard.Key> keys = null;
        if (mKeyboard != null) {
            keys = mKeyboard.getKeys();
        }

        if (keys != null) {
            for (Keyboard.Key key : keys) {
                if (key.codes[0] == CODE_EMPTY) {
                    drawSpecialstyle(canvas, key, R.drawable.shape_no_pre);
                } else if (key.codes[0] == CODE_FINISH) {
                    drawSpecialstyle(canvas, key);
                }
            }
        }
    }

    /**
     * 空Key样式
     *
     * @param canvas
     * @param key
     */
    private void drawSpecialstyle(Canvas canvas, Keyboard.Key key, int key_bg) {
        Drawable drawable = mContext.getResources().getDrawable(key_bg);
        drawable.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
        drawable.draw(canvas);
    }

    /**
     * 确定
     *
     * @param canvas
     * @param key
     */
    private void drawSpecialstyle(Canvas canvas, Keyboard.Key key) {
        canvas.drawRect(new Rect(key.x, key.y, key.x + key.width, key.y + key.height), mPaintBg);

        String content = "确定";
        Rect bounds = new Rect();
        mPaint.getTextBounds(content, 0, content.length(), bounds);
        float baseline = key.y + key.height / 2 + mPaint.getTextSize() / 2 - mPaint.getFontMetrics().descent;
        canvas.drawText(content, key.x + key.width / 2 - bounds.width() / 2, baseline, mPaint);
    }

}
