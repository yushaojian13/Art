package com.ysj.art.meizhi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ysj.art.R;
import com.ysj.art.widgets.RatioImageView;

import java.util.List;

/**
 * Created by yushaojian on 7/19/16.
 */
public class MeizhiAdapter extends RecyclerView.Adapter<MeizhiAdapter.ViewHolder> {
    private List<Meizhi> mMeizhis;

    public void replaceData(List<Meizhi> meizhis) {
        mMeizhis = meizhis;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.meizhi_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Meizhi meizhi = mMeizhis.get(position);

        holder.meizhiIV.setRatio(meizhi.getRatio());
        Glide.with(holder.meizhiIV.getContext()).load(meizhi.url).diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate().into(holder.meizhiIV);
    }

    @Override
    public int getItemCount() {
        return mMeizhis == null ? 0 : mMeizhis.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RatioImageView meizhiIV;

        public ViewHolder(View itemView) {
            super(itemView);
            meizhiIV = (RatioImageView) itemView.findViewById(R.id.meizhiIV);
        }
    }
}
