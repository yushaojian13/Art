package com.ysj.art.meizhi;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ysj.art.R;

import java.util.List;

/**
 * Created by yushaojian on 7/17/16.
 */
public class MeizhiFragment extends Fragment implements MeizhiContract.View,
        SwipeRefreshLayout.OnRefreshListener {
    private MeizhiContract.Presenter mPresenter;

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mMeizhiRV;

    private MeizhiAdapter mMeizhiAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.meizhi_fragment, container, false);

        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        mMeizhiRV = (RecyclerView) view.findViewById(R.id.meizhiRV);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL);
        mMeizhiRV.setLayoutManager(layoutManager);

        mMeizhiAdapter = new MeizhiAdapter();
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
    public void setPresenter(MeizhiContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh();
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
    public void showMeizhi(List<Meizhi> meizhis) {
        mMeizhiAdapter.replaceData(meizhis);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

}
