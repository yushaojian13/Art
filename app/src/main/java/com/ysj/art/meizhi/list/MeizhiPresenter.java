package com.ysj.art.meizhi.list;

import java.util.List;

import com.ysj.art.meizhi.data.Meizhi;
import com.ysj.art.meizhi.data.source.MeizhisDataSource;
import com.ysj.art.meizhi.data.source.MeizhisRepository;

import android.support.annotation.NonNull;

/**
 * 作为Presenter，处理逻辑.
 * Created by yushaojian on 7/19/16.
 */
public class MeizhiPresenter implements MeizhiContract.Presenter {

    private final MeizhisRepository   mMeizhisRepository;
    private final MeizhiContract.View mMeizhiView;

    public MeizhiPresenter(@NonNull MeizhisRepository meizhisRepository, @NonNull MeizhiContract.View meizhiView) {
        mMeizhisRepository = meizhisRepository;
        mMeizhiView = meizhiView;

        mMeizhiView.setPresenter(this);
    }

    @Override
    public void start() {
        refresh(false);
    }

    @Override
    public void refresh(boolean forceUpdate) {
        // Simplification for sample: a network reload will be forced on first load.
        refresh(forceUpdate, true);
    }

    private void refresh(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mMeizhiView.setRefreshingIndicator(true);
        }

        if (forceUpdate) {
            mMeizhisRepository.refreshMeizhis();
        }

        mMeizhisRepository.getLatestMeizhis(new MeizhisDataSource.LoadMeizhisCallback() {

            @Override
            public void onMeizhisLoaded(List<Meizhi> meizhis) {
                if (!mMeizhiView.isActive()) {
                    return;
                }

                mMeizhiView.setRefreshingIndicator(false);

                if (meizhis.isEmpty()) {
                    mMeizhiView.showNoData();
                } else {
                    mMeizhiView.showMeizhi(meizhis);
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (!mMeizhiView.isActive()) {
                    return;
                }

                mMeizhiView.setRefreshingIndicator(false);
                mMeizhiView.showLoadingError();
            }
        });
    }

    @Override
    public void loadMore() {
    }

}
