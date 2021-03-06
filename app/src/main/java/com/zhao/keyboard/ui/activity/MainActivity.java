package com.zhao.keyboard.ui.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import com.zd.base.BaseActivity;
import com.zhao.keyboard.R;
import com.zhao.keyboard.entity.ContentEntity;
import com.zhao.keyboard.ui.adapter.ContentAdapter;
import com.zhao.keyboard.view.KeyboardHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity implements KeyboardHelper.OnFinishClickListner {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private KeyboardHelper mKeyboardHelper;
    private Unbinder mUnbinder;

    @Override
    public int setLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    protected void initVisible() {
        super.initVisible();
    }

    @Override
    protected void initDatas(Bundle savedInstanceState) {
        super.initDatas(savedInstanceState);
        mUnbinder = ButterKnife.bind(this);
        mKeyboardHelper = new KeyboardHelper(this);

        List<ContentEntity> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            if (i == 1 || i == 4 || i == 24 || i == 27) {
                ContentEntity contentEntity = new ContentEntity();
                contentEntity.setShowCustomKeyboard(true);
                data.add(contentEntity);
            } else {
                ContentEntity contentEntity = new ContentEntity();
                data.add(contentEntity);
            }
        }
        ContentAdapter mContentAdapter = new ContentAdapter(mKeyboardHelper);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mContentAdapter);
        mContentAdapter.setData(data);
    }

    @Override
    protected void initEvents(Bundle savedInstanceState) {
        super.initEvents(savedInstanceState);
        mKeyboardHelper.setFinishClickListner(this);
    }

    @Override
    public void onFinishClickListner(int position, String content) {
        Toast.makeText(this, "position : " + position + " content : " + content, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (mKeyboardHelper.isShowKeyboardVisible()) {
            mKeyboardHelper.hideKeyboard();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        this.mUnbinder = null;
    }
}
