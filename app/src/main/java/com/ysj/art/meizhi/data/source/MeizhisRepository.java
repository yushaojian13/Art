package com.ysj.art.meizhi.data.source;

import android.support.annotation.NonNull;

import com.ysj.art.meizhi.data.Meizhi;

import java.util.List;

/**
 * 妹纸数据源库.
 * 作为Model，接受Presenter的指令，处理数据.
 * Created by yushaojian on 10/29/16.
 */

public class MeizhisRepository implements MeizhisDataSource {

    private static MeizhisRepository INSTANCE = null;

    private MeizhisDataSource        mMeizhisLocalDataSource;

    private MeizhisDataSource        mMeizhisRemoteDataSource;

    private MeizhisRepository(@NonNull MeizhisDataSource meizhisLocalDataSource,
                              @NonNull MeizhisDataSource meizhisRemoteDataSource) {
        this.mMeizhisLocalDataSource = meizhisLocalDataSource;
        this.mMeizhisRemoteDataSource = meizhisRemoteDataSource;
    }

    public static MeizhisRepository getInstance(@NonNull MeizhisDataSource meizhisLocalDataSource,
                                                @NonNull MeizhisDataSource meizhisRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new MeizhisRepository(meizhisLocalDataSource, meizhisRemoteDataSource);
        }

        return INSTANCE;
    }

    @Override
    public void getLatestMeizhis(@NonNull final LoadMeizhisCallback callback) {
        mMeizhisLocalDataSource.getLatestMeizhis(new LoadMeizhisCallback() {

            @Override
            public void onMeizhisLoaded(List<Meizhi> meizhis) {
                callback.onMeizhisLoaded(meizhis);
            }

            @Override
            public void onDataNotAvailable() {
                mMeizhisRemoteDataSource.getLatestMeizhis(callback);
            }
        });
    }
}
