package com.ysj.art;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ysj.art.meizhi.MeizhiFragment;
import com.ysj.art.meizhi.MeizhiPresenter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MeizhiFragment meizhiFragment = new MeizhiFragment();
        getFragmentManager().beginTransaction().add(R.id.contentFrame, meizhiFragment).commit();

        new MeizhiPresenter(meizhiFragment);
    }
}
