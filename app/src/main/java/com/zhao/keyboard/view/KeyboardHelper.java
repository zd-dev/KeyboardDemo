
package com.zhao.keyboard.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.zhao.keyboard.R;
import com.zhao.keyboard.entity.ContentEntity;

/**
 * 键盘是否显示工具类
 *
 * @author zhaod
 * @date 2019/7/7
 */

public class KeyboardHelper {

    private final int CODE_DELETE = -5;    // 删除
    private final int CODE_FINISH = -100;  // 确定
    private final int CODE_EMPTY = -101;   // 占位

    private OnFinishClickListner onFinishClickListner;
    private NumberKeyboardView mNumberKeyboardView;
    private Keyboard mKeyboard;
    private Activity mActivity;
    private View mContainer;
    private int mShowKeyXml;

    public KeyboardHelper(Activity activity) {
        this(activity, R.xml.keyboard_number);
    }

    public KeyboardHelper(Activity activity, int showKeyXml) {
        mActivity = activity;
        mShowKeyXml = showKeyXml;
        initLayout();
        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void initLayout() {
        ViewGroup mView = mActivity.getWindow().findViewById(android.R.id.content);
        mContainer = LayoutInflater.from(mActivity).inflate(R.layout.layout_keyboard, null);
        mNumberKeyboardView = mContainer.findViewById(R.id.keyboardview);
        mKeyboard = new Keyboard(mActivity, mShowKeyXml);
        mNumberKeyboardView.setKeyboard(mKeyboard);
        mNumberKeyboardView.setPreviewEnabled(false);
        mView.addView(mContainer);

        mNumberKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);

        // 键盘显示
        mContainer.post(new Runnable() {
            @Override
            public void run() {
                mContainer.setTranslationY(mContainer.getHeight());
                mContainer.setVisibility(View.GONE);
            }
        });
    }

    public boolean isShowKeyboardVisible() {
        return mContainer.getVisibility() == View.VISIBLE;
    }

    /**
     * 显示自定义键盘
     */
    public void showKeyboard(View v) {
        mContainer.animate().translationY(0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mContainer.setVisibility(View.VISIBLE);
            }
        }).start();
        if (v != null) {
            ((InputMethodManager) mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    /**
     * 隐藏自定义键盘
     */
    public void hideKeyboard() {
        mContainer.animate().translationY(mContainer.getHeight()).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mContainer.setVisibility(View.GONE);
            }
        }).start();
    }

    /**
     * 显示系统键盘
     *
     * @param editText
     */
    public void setEditText(EditText editText) {
        setEditText(editText, false);
    }

    /**
     * 是否使用数字键盘
     *
     * @param editText
     * @param isShowCustom
     */
    @SuppressLint("ClickableViewAccessibility")
    public void setEditText(EditText editText, boolean isShowCustom) {
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (isShowCustom) {
                    if (hasFocus) {
                        initKeyNumber();
                        showKeyboard(v);
                    } else {
                        hideKeyboard();
                    }
                }else{
                    hideKeyboard();
                }
            }
        });
        editText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowCustom) {
                    if (!isShowKeyboardVisible()) {
                        showKeyboard(v);
                    }
                }
            }
        });
        editText.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isShowCustom) {
                    EditText edittext = (EditText) v;
                    edittext.onTouchEvent(event);
                    ((InputMethodManager) mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }

    private void initKeyNumber() {
        mKeyboard = new Keyboard(mActivity, mShowKeyXml);
        mNumberKeyboardView.setKeyboard(mKeyboard);
        mNumberKeyboardView.requestLayout();
    }

    private OnKeyboardActionListener mOnKeyboardActionListener = new OnKeyboardActionListener() {

        @Override
        public void onPress(int primaryCode) {

        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            View currentFocus = mActivity.getWindow().getCurrentFocus();
            if (currentFocus == null || !(currentFocus instanceof EditText)) {
                return;
            }
            EditText editText = (EditText) currentFocus;
            Editable editStr = editText.getText();
            int start = editText.getSelectionStart();
            if (primaryCode == CODE_DELETE) {
                if (editStr != null && start > 0) editStr.delete(start - 1, start);
            } else if (primaryCode == CODE_FINISH) {
                int position = 0;
                if (editText.getTag() instanceof ContentEntity) {
                    ContentEntity entity = (ContentEntity) editText.getTag();
                    position = entity.getPosition();
                }
                onFinishClickListner.onFinishClickListner(position, editText.getText().toString().trim());
                hideKeyboard();
            } else if (primaryCode == CODE_EMPTY) {
            } else {
                editStr.insert(start, Character.toString((char) primaryCode));
            }
        }

        @Override
        public void onText(CharSequence text) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };

    public void setFinishClickListner(OnFinishClickListner onFinishClickListner) {
        this.onFinishClickListner = onFinishClickListner;
    }

    public interface OnFinishClickListner {
        void onFinishClickListner(int position, String content);
    }
}
