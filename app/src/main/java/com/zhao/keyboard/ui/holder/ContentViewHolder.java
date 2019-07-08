package com.zhao.keyboard.ui.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.zhao.keyboard.R;
import com.zhao.keyboard.base.BaseRecyclerHolder;
import com.zhao.keyboard.entity.ContentEntity;
import com.zhao.keyboard.view.ContentTextWatcher;
import com.zhao.keyboard.view.KeyboardHelper;

import java.util.List;

import butterknife.BindView;

/**
 * @author zhaod
 * @date 2019/7/7
 */

public class ContentViewHolder extends BaseRecyclerHolder {
    @BindView(R.id.ed_content)
    EditText mEdContent;

    private Context mContext;
    private KeyboardHelper mKeyboardHelper;

    public void setKeyboardHelper(KeyboardHelper mKeyboardHelper) {
        this.mKeyboardHelper = mKeyboardHelper;
    }

    public ContentViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
    }

    public void setData(List<ContentEntity> data, int position) {
        ContentEntity contentEntity = data.get(position);

        mEdContent.setHint(contentEntity.isShowCustomKeyboard() ?
                String.format("请输入第%s个内容（自定义键盘）", String.valueOf(position)) :
                String.format("请输入第%s个内容", String.valueOf(position)));

        if (contentEntity.isShowCustomKeyboard()) {
            mKeyboardHelper.setEditText(mEdContent, true);
        } else {
            mKeyboardHelper.setEditText(mEdContent);
        }

        if (mEdContent.getTag() instanceof ContentEntity) {
            ContentEntity entity = (ContentEntity) mEdContent.getTag();
            mEdContent.removeTextChangedListener(entity.getContentTextWatcher());
        }

        if (TextUtils.isEmpty(contentEntity.getContent())) {
            mEdContent.setText("");
        } else {
            mEdContent.setText(contentEntity.getContent());
        }

        ContentTextWatcher contentTextWatcher = new ContentTextWatcher(contentEntity, mEdContent);
        mEdContent.addTextChangedListener(contentTextWatcher);
        contentEntity.setContentTextWatcher(contentTextWatcher);
        contentEntity.setPosition(position);
        mEdContent.setTag(contentEntity);
    }
}
