package com.ysj.art.meizhi;

import android.os.Handler;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yushaojian on 7/19/16.
 */
public class MeizhiPresenter implements MeizhiContract.Presenter {
    private MeizhiContract.View mMeizhiView;

    private List<Meizhi> mMockMeizhis;

    public MeizhiPresenter(@NonNull MeizhiContract.View meizhiView) {
        mMeizhiView = meizhiView;
        mMeizhiView.setPresenter(this);

        mMockMeizhis = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mMockMeizhis.add(new Meizhi("http://ww4.sinaimg.cn/large/610dc034jw1f5xwnxj2vmj20dw0dwjsc.jpg", 500, 500));
        }
    }

    @Override
    public void refresh() {
        mMeizhiView.setRefreshingIndicator(true);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMockMeizhis.add(0, new Meizhi("http://ww2.sinaimg.cn/large/610dc034jw1f5s5382uokj20fk0ncmyt.jpg", 560, 840));
                mMeizhiView.showMeizhi(mMockMeizhis);
                mMeizhiView.setRefreshingIndicator(false);
            }
        }, 2000);
    }

    @Override
    public void loadMore() {

    }

    @Override
    public void start() {
        mMeizhiView.setRefreshingIndicator(true);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMeizhiView.showMeizhi(mMockMeizhis);
                mMeizhiView.setRefreshingIndicator(false);
            }
        }, 2000);
    }
}
