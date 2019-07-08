package com.zhao.keyboard.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.zhao.keyboard.entity.ContentEntity;

/**
 * TextWatcher
 *
 * @author zhaod
 * @date 2019/7/8
 */
public class ContentTextWatcher implements TextWatcher {
    private ContentEntity contentEntity;
    private EditText mEdContent;

    public ContentTextWatcher(ContentEntity contentEntity, EditText mEdContent) {
        this.contentEntity = contentEntity;
        this.mEdContent = mEdContent;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        try {
            contentEntity.setContent(mEdContent.getText().toString().trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
