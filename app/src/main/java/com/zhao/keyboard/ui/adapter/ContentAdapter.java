package com.zhao.keyboard.ui.adapter;

import android.view.View;

import com.zhao.keyboard.R;
import com.zhao.keyboard.base.BaseRecyclerAdapter;
import com.zhao.keyboard.base.BaseRecyclerHolder;
import com.zhao.keyboard.entity.ContentEntity;
import com.zhao.keyboard.ui.holder.ContentViewHolder;
import com.zhao.keyboard.view.KeyboardHelper;

/**
 * @author zhaod
 * @date 2019/7/7
 */

public class ContentAdapter extends BaseRecyclerAdapter<ContentEntity> {

    private KeyboardHelper mKeyboardHelper;

    public ContentAdapter(KeyboardHelper mKeyboardHelper) {
        this.mKeyboardHelper = mKeyboardHelper;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_for_content;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View v, int viewType) {
        ContentViewHolder contentViewHolder = new ContentViewHolder(v);
        contentViewHolder.setKeyboardHelper(mKeyboardHelper);
        return contentViewHolder;
    }

    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
        contentViewHolder.setData(mDatas, position);
    }
}
