package com.ysj.art.meizhi.data.source.local;

import android.provider.BaseColumns;

/**
 * 定义妹纸数据持久化契约.
 * Created by yushaojian on 11/13/16.
 */
public interface MeizhiPersistenceContract {

    /* 定义妹纸表名及列名 */
    interface MeizhiEntry extends BaseColumns {

        String TABLE_NAME    = "meizhi";
        String COLUMN_URL    = "url";
        String COLUMN_WIDTH  = "width";
        String COLUMN_HEIGHT = "height";
    }
}
