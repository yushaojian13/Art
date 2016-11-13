package com.ysj.art.meizhi.data.source;

import android.support.annotation.NonNull;

import com.ysj.art.meizhi.data.Meizhi;

import java.util.List;

/**
 * 妹纸数据源接口.
 * Created by yushaojian on 10/29/16.
 */

public interface MeizhisDataSource {

    interface LoadMeizhisCallback {

        void onMeizhisLoaded(List<Meizhi> meizhis);

        void onDataNotAvailable();
    }

    void getLatestMeizhis(@NonNull LoadMeizhisCallback callback);

    void saveMeizhi(@NonNull Meizhi meizhi);

    void saveMeizhis(@NonNull List<Meizhi> meizhis);

    void refreshMeizhis();

    void deleteAllMeizhis();

}
