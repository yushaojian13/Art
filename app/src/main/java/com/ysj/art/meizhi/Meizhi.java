package com.ysj.art.meizhi;

/**
 * Created by yushaojian on 7/17/16.
 */
public class Meizhi {
    final String url;
    final int width;
    final int height;

    public Meizhi(String url, int width, int height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }

    public float getRatio() {
        if (width == 0 || height == 0) {
            return 1;
        }

        return 1.0f * width / height;
    }
}
