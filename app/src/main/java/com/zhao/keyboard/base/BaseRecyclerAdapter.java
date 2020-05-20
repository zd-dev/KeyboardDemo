package com.zhao.keyboard.base;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * RecyclerViewAdapter 基类
 *
 * @author zhaod
 * @date 2019/7/7
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerHolder> {
    protected List<T> mDatas;
    private OnRecyclerItemClickListner onRecyclerItemClickListner = null;//RecyclerView点击事件
    private OnRecyclerItemLongClickListner onRecyclerItemLongClickListner = null;//RecyclerView长点击事件

    private OnTabSelectListener onTabSelectListener;
    private int mCurrentTab = 0;
    private int mPreSelectedTabIndex = -1;

    public BaseRecyclerAdapter() {
    }

    @NonNull
    @Override
    public BaseRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
        BaseRecyclerHolder mHolder = new BaseRecyclerHolder(view);
        mHolder = getCreateViewHolder(view, viewType);
        BaseRecyclerHolder finalMHolder = mHolder;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = finalMHolder.getLayoutPosition();
                if (onRecyclerItemClickListner != null) {
                    onRecyclerItemClickListner.onItemClickListner(v, position);
                }
                if (onTabSelectListener != null) {
                    mPreSelectedTabIndex = position;
                    if (mCurrentTab == mPreSelectedTabIndex) {
                        onTabSelectListener.onTabReselect(position);
                    } else {
                        onTabSelectListener.onTabSelect(position);
                    }
                    mCurrentTab = mPreSelectedTabIndex;
                }
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = finalMHolder.getLayoutPosition();
                if (onRecyclerItemLongClickListner != null) {
                    onRecyclerItemLongClickListner.onItemLongClickListner(v, position);
                }
                return false;
            }
        });

        return mHolder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, int position) {
        getHolder(holder, position);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            getHolder(holder, position);
        } else {
            getHolder(holder, position, payloads);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public List<T> getDatas() {
        return mDatas;
    }

    /**
     * 刷新数据
     *
     * @param datas
     */
    public void refresh(List<T> datas) {
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param mDatas
     */
    public void setData(List<T> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param datas
     */
    public void addData(List<T> datas) {
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 添加数据(局部刷新，局部刷新时必须重写getItemId方法，同时setHasStableIds(true))
     *
     * @param datas
     */
    public void addDataWithoutAnim(List<T> datas) {
        if (datas == null)
            return;
        int size = mDatas.size();
        this.mDatas.addAll(datas);
        notifyItemRangeChanged(size, datas.size());
    }

    /**
     * 删除列表
     *
     * @param position
     */
    public void remove(int position) {
        mDatas.remove(position);
        int internalPosition = position;
        notifyItemRemoved(internalPosition);
        notifyItemRangeChanged(internalPosition, mDatas.size() - internalPosition);
    }

    /**
     * 需要重写的方法
     *
     * @param holder
     * @param position
     */
    public abstract void getHolder(BaseRecyclerHolder holder, int position);

    /**
     * 需要重写的方法，局部刷新
     *
     * @param holder
     * @param position
     */
    protected void getHolder(BaseRecyclerHolder holder, int position, List<Object> payloads) {
    }

    protected abstract int getLayoutId(int viewType);

    public abstract BaseRecyclerHolder getCreateViewHolder(View view, int viewType);

    /**
     * RecyclerView点击事件
     *
     * @param onItemClickListner
     */
    public void setRecyclerItemClickListner(OnRecyclerItemClickListner onItemClickListner) {
        this.onRecyclerItemClickListner = onItemClickListner;
    }

    public interface OnRecyclerItemClickListner {
        void onItemClickListner(View v, int position);
    }

    /**
     * RecyclerView长点击事件
     *
     * @param onItemClickListner
     */
    public void setRecyclerItemLongClickListner(OnRecyclerItemLongClickListner onItemClickListner) {
        this.onRecyclerItemLongClickListner = onItemClickListner;
    }

    public interface OnRecyclerItemLongClickListner {
        void onItemLongClickListner(View v, int position);
    }

    public void setOnTabSelectListener(OnTabSelectListener onTabSelectListener) {
        this.onTabSelectListener = onTabSelectListener;
    }

    /**
     * item点击监听
     */
    public interface OnTabSelectListener {
        /**
         * @param position 首次选中索引
         */
        void onTabSelect(int position);

        /**
         * @param position 第二次之后选中索引
         */
        void onTabReselect(int position);
    }

}
