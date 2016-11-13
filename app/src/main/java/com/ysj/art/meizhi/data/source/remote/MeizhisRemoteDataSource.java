package com.ysj.art.meizhi.data.source.remote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.ysj.art.meizhi.data.Meizhi;
import com.ysj.art.meizhi.data.source.MeizhisDataSource;
import com.ysj.log.L;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.NonNull;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 妹纸远程数据源.
 * Created by yushaojian on 10/29/16.
 */

public class MeizhisRemoteDataSource implements MeizhisDataSource {

    private static MeizhisRemoteDataSource INSTANCE;

    private Context mContext;

    private OkHttpClient client = new OkHttpClient();

    private Handler handler = new Handler();

    private MeizhisRemoteDataSource(@NonNull Context context) {
        mContext = context;
    }

    public static MeizhisRemoteDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new MeizhisRemoteDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getLatestMeizhis(@NonNull final LoadMeizhisCallback callback) {
        final Request request = new Request.Builder().url("http://gank.io/api/data/福利/10/1").build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        callback.onDataNotAvailable();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final List<Meizhi> meizhis = new ArrayList<>();

                if (response != null && response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.optJSONArray("results");
                        if (jsonArray != null && jsonArray.length() > 0) {
                            int length = jsonArray.length();
                            for (int i = 0; i < length; i++) {
                                JSONObject meizhiJSONObject = jsonArray.optJSONObject(i);
                                String url = meizhiJSONObject.optString("url");

                                try {
                                    Bitmap bitmap = Glide.with(mContext).load(url).asBitmap()
                                            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                                    Meizhi meizhi = new Meizhi(url, bitmap.getWidth(), bitmap.getHeight());
                                    meizhis.add(meizhi);
                                } catch (InterruptedException | ExecutionException e) {
                                    L.e(e);
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        callback.onMeizhisLoaded(meizhis);
                    }
                });
            }
        });
    }

    @Override
    public void saveMeizhi(@NonNull Meizhi meizhi) {
        // not supported.
    }

    @Override
    public void saveMeizhis(@NonNull List<Meizhi> meizhis) {
        // not supported.
    }

    @Override
    public void refreshMeizhis() {
        // Not required because the {@link MeizhisRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
    }

    @Override
    public void deleteAllMeizhis() {
        // not supported.
    }
}
