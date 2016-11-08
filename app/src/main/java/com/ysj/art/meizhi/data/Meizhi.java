package com.ysj.art.meizhi.data;

/**
 * 妹纸Model类.
 * Created by yushaojian on 7/17/16.
 */
public class Meizhi {

    public final String mUrl;
    public final int    mWidth;
    public final int    mHeight;

    public Meizhi(String url, int width, int height) {
        mUrl = url;
        mWidth = width;
        mHeight = height;
    }

    public float getRatio() {
        if (mWidth == 0 || mHeight == 0) {
            return 1;
        }

        return 1.0f * mWidth / mHeight;
    }

    @Override
    public String toString() {
        return "Meizhi{" + "url='" + mUrl + '\'' + ", width=" + mWidth + ", height=" + mHeight + '}';
    }
}
