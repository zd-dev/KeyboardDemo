package com.zhao.keyboard.entity;

import com.zhao.keyboard.view.ContentTextWatcher;

/**
 * @author zhaod
 * @date 2019/7/7
 */
public class ContentEntity {
    private int position;
    private String content;
    private boolean isShowCustomKeyboard;
    private ContentTextWatcher contentTextWatcher;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ContentTextWatcher getContentTextWatcher() {
        return contentTextWatcher;
    }

    public void setContentTextWatcher(ContentTextWatcher contentTextWatcher) {
        this.contentTextWatcher = contentTextWatcher;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isShowCustomKeyboard() {
        return isShowCustomKeyboard;
    }

    public void setShowCustomKeyboard(boolean showCustomKeyboard) {
        isShowCustomKeyboard = showCustomKeyboard;
    }
}
