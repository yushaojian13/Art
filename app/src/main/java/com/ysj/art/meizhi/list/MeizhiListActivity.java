package com.ysj.art.meizhi.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ysj.art.R;
import com.ysj.art.meizhi.data.source.MeizhisRepository;
import com.ysj.art.meizhi.data.source.local.MeizhisLocalDataSource;
import com.ysj.art.meizhi.data.source.remote.MeizhisRemoteDataSource;

/**
 * 作为控制器，构建Model、View和Presenter.
 */
public class MeizhiListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MeizhiFragment meizhiFragment = (MeizhiFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (meizhiFragment == null) {
            meizhiFragment = new MeizhiFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.contentFrame, meizhiFragment).commit();
        }

        new MeizhiPresenter(MeizhisRepository.getInstance(new MeizhisLocalDataSource(getApplicationContext()),
                                                          new MeizhisRemoteDataSource(getApplicationContext())),
                            meizhiFragment);
    }
}
