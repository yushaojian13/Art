package com.ysj.art;

import android.app.Application;
import android.content.Context;

import com.ysj.log.L;

/**
 * Created by yushaojian on 8/8/16.
 */
public class ArtApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		L.setTag("Meizhi");
	}

}
