package com.ysj.art.meizhi.list;

import com.ysj.art.BasePresenter;
import com.ysj.art.BaseView;
import com.ysj.art.meizhi.data.Meizhi;

import java.util.List;

/**
 * 契约，定义Presenter和View的接口.
 * Created by yushaojian on 7/13/16.
 */
public interface MeizhiContract {

    interface View extends BaseView<Presenter> {

        void setRefreshingIndicator(boolean active);

        // TODO 待实现
        void setLoadingIndicator(boolean active);

        void showLoadingError();

        void showNoData();

        void showMeizhi(List<Meizhi> meizhis);

        boolean isActive();

    }

    interface Presenter extends BasePresenter {

        void refresh();

        void loadMore();

    }

}
