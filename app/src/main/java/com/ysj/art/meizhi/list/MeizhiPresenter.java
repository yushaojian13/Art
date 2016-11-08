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
        refresh();
    }

    @Override
    public void refresh() {
        mMeizhiView.setRefreshingIndicator(true);

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
