package com.ysj.art.meizhi.data.source;

import android.support.annotation.NonNull;

import com.ysj.art.meizhi.data.Meizhi;

import java.util.ArrayList;
import java.util.List;

/**
 * 妹纸数据源库.
 * 作为Model，接受Presenter的指令，处理数据.
 * Created by yushaojian on 10/29/16.
 */

public class MeizhisRepository implements MeizhisDataSource {

    private static MeizhisRepository INSTANCE = null;

    private MeizhisDataSource mMeizhisLocalDataSource;

    private MeizhisDataSource mMeizhisRemoteDataSource;

    private List<Meizhi> mCacheMeizhis;

    private boolean mCacheIsDirty = false;

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
        // Respond immediately with cache if available and not dirty
        if (mCacheMeizhis != null && !mCacheIsDirty) {
            callback.onMeizhisLoaded(new ArrayList<>(mCacheMeizhis));
            return;
        }

        if (mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getMeizhisFromRemoteDataSource(callback);
        } else {
            // Query the local storage if available. If not, query the network.
            mMeizhisLocalDataSource.getLatestMeizhis(new LoadMeizhisCallback() {

                @Override
                public void onMeizhisLoaded(List<Meizhi> meizhis) {
                    refreshCache(meizhis);
                    callback.onMeizhisLoaded(new ArrayList<>(mCacheMeizhis));
                }

                @Override
                public void onDataNotAvailable() {
                    getMeizhisFromRemoteDataSource(callback);
                }
            });
        }
    }

    @Override
    public void refreshMeizhis() {
        mCacheIsDirty = true;
    }

    @Override
    public void saveMeizhi(@NonNull Meizhi meizhi) {
        mMeizhisRemoteDataSource.saveMeizhi(meizhi);
        mMeizhisLocalDataSource.saveMeizhi(meizhi);

        if (mCacheMeizhis == null) {
            mCacheMeizhis = new ArrayList<>();
        }

        mCacheMeizhis.add(meizhi);
    }

    @Override
    public void saveMeizhis(@NonNull List<Meizhi> meizhis) {
        mMeizhisRemoteDataSource.saveMeizhis(meizhis);
        mMeizhisLocalDataSource.saveMeizhis(meizhis);

        if (mCacheMeizhis == null) {
            mCacheMeizhis = new ArrayList<>();
        }

        mCacheMeizhis.addAll(meizhis);
    }

    @Override
    public void deleteAllMeizhis() {
        mMeizhisLocalDataSource.deleteAllMeizhis();
        mMeizhisRemoteDataSource.deleteAllMeizhis();

        if (mCacheMeizhis == null) {
            mCacheMeizhis = new ArrayList<>();
        }

        mCacheMeizhis.clear();
    }

    private void getMeizhisFromRemoteDataSource(@NonNull final LoadMeizhisCallback callback) {
        mMeizhisRemoteDataSource.getLatestMeizhis(new LoadMeizhisCallback() {

            @Override
            public void onMeizhisLoaded(List<Meizhi> meizhis) {
                refreshCache(meizhis);
                refreshLocalDataSource(meizhis);
                callback.onMeizhisLoaded(new ArrayList<>(mCacheMeizhis));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshCache(List<Meizhi> meizhis) {
        if (mCacheMeizhis == null) {
            mCacheMeizhis = new ArrayList<>();
        }

        mCacheMeizhis.clear();
        mCacheMeizhis.addAll(meizhis);

        mCacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<Meizhi> meizhis) {
        mMeizhisLocalDataSource.deleteAllMeizhis();
        mMeizhisLocalDataSource.saveMeizhis(meizhis);
    }
}
