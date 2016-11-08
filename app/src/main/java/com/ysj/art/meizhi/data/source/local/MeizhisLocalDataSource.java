package com.ysj.art.meizhi.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ysj.art.meizhi.data.source.MeizhisDataSource;

/**
 * 妹纸本地数据源.
 * Created by yushaojian on 10/29/16.
 */

public class MeizhisLocalDataSource implements MeizhisDataSource {

	public MeizhisLocalDataSource(@NonNull Context context) {
	}

	@Override
	public void getLatestMeizhis(@NonNull LoadMeizhisCallback callback) {
		// TODO 实现本地缓存
		callback.onDataNotAvailable();
	}
}
