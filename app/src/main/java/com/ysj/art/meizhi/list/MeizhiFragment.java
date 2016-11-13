package com.ysj.art.meizhi.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ysj.art.R;
import com.ysj.art.meizhi.data.Meizhi;

import java.util.List;

/**
 * 显示妹纸瀑布流.
 * 作为View，几乎不做逻辑处理，接收Presenter指令.
 * Created by yushaojian on 7/17/16.
 */
public class MeizhiFragment extends Fragment implements MeizhiContract.View, SwipeRefreshLayout.OnRefreshListener {

    private MeizhiContract.Presenter mPresenter;

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView       mMeizhiRV;

    private MeizhiAdapter mMeizhiAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMeizhiAdapter = new MeizhiAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.meizhi_fragment, container, false);

        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        mMeizhiRV = (RecyclerView) view.findViewById(R.id.meizhiRV);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL);
        mMeizhiRV.setLayoutManager(layoutManager);

        mMeizhiRV.setAdapter(mMeizhiAdapter);

        mRefreshLayout.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull MeizhiContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh(false);
    }

    @Override
    public void setRefreshingIndicator(final boolean active) {
        mRefreshLayout.post(new Runnable() {

            @Override
            public void run() {
                mRefreshLayout.setRefreshing(active);
            }
        });
    }

    @Override
    public void setLoadingIndicator(boolean active) {
    }

    @Override
    public void showLoadingError() {
        showMessage(getString(R.string.loading_error));
    }

    @Override
    public void showNoData() {
        showMessage(getString(R.string.no_data));
    }

    @Override
    public void showMeizhi(List<Meizhi> meizhis) {
        mMeizhiAdapter.replaceData(meizhis);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }
}
