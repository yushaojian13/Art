package com.ysj.art.meizhi;

import com.ysj.art.BasePresenter;
import com.ysj.art.BaseView;

import java.util.List;

/**
 * Created by yushaojian on 7/13/16.
 */
public interface MeizhiContract {

    interface View extends BaseView<Presenter> {

        void setRefreshingIndicator(boolean active);

        void setLoadingIndicator(boolean active);

        void showMeizhi(List<Meizhi> meizhis);

        boolean isActive();

    }

    interface Presenter extends BasePresenter {

        void refresh();

        void loadMore();

    }
}
