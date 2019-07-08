package com.zhao.keyboard.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * RecyclerViewHolder 基类
 *
 * @author zhaod
 * @date 2019/7/7
 */

public class BaseRecyclerHolder extends RecyclerView.ViewHolder {

    public BaseRecyclerHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    /**
     * 点击事件
     *
     * @param listener
     * @return
     */

    public BaseRecyclerHolder setOnClickListener(View view, View.OnClickListener listener) {
        view.setOnClickListener(listener);
        return this;
    }
}
