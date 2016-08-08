package com.ysj.art.meizhi;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.ysj.art.ArtApplication;
import com.ysj.log.L;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yushaojian on 7/19/16.
 */
public class MeizhiPresenter implements MeizhiContract.Presenter {
    private MeizhiContract.View mMeizhiView;

    private List<Meizhi> mMockMeizhis;

    private OkHttpClient client = new OkHttpClient();

    private Handler handler = new Handler();

    public MeizhiPresenter(@NonNull MeizhiContract.View meizhiView) {
        mMeizhiView = meizhiView;
        mMeizhiView.setPresenter(this);

        mMockMeizhis = new ArrayList<>();
    }

    @Override
    public void refresh() {
        mMeizhiView.setRefreshingIndicator(true);

        final Request request = new Request.Builder().url("http://gank.io/api/data/福利/10/1").build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mMeizhiView.isActive()) {
                            mMeizhiView.setRefreshingIndicator(false);
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                boolean success = false;

                if (response != null && response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.optJSONArray("results");
                        if (jsonArray != null && jsonArray.length() > 0) {
                            mMockMeizhis.clear();

                            int length = jsonArray.length();
                            for (int i = 0; i < length; i++) {
                                JSONObject meizhiJSONObject = jsonArray.optJSONObject(i);
                                String url = meizhiJSONObject.optString("url");

                                try {
                                    Bitmap bitmap = Glide.with(ArtApplication.getContext())
                                            .load(url).asBitmap()
                                            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                            .get();
                                    Meizhi meizhi = new Meizhi(url, bitmap.getWidth(), bitmap.getHeight());
                                    mMockMeizhis.add(meizhi);
                                } catch (InterruptedException | ExecutionException e) {
                                    e.printStackTrace();
                                }
                            }

                            success = true;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (success) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mMeizhiView.isActive()) {
                                mMeizhiView.showMeizhi(mMockMeizhis);
                                mMeizhiView.setRefreshingIndicator(false);
                            }
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mMeizhiView.isActive()) {
                                mMeizhiView.setRefreshingIndicator(false);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void loadMore() {
    }

    @Override
    public void start() {
        refresh();
    }
}
