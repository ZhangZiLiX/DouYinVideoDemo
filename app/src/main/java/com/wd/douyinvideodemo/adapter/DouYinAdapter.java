package com.wd.douyinvideodemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.wd.douyinvideodemo.R;

import java.util.List;

/**
 * Author : 张自力
 * Created on time.
 *
 * 抖音播放列表适配器
 *
 */
public class DouYinAdapter extends RecyclerView.Adapter<DouYinAdapter.ViewHolder> {

    private int index = 0;//设置循环播放标识
    private List<String> videos;
    private Context mContext;

    public DouYinAdapter(List<String> videos, Context mContext) {
        this.videos = videos;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view_pager, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.gsyVideoPlayer.setUpLazy(videos.get(index), true, null, null, "视频播放标题");
        //增加title
        holder.gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE);
        //设置返回键
        holder.gsyVideoPlayer.getBackButton().setVisibility(View.GONE);
        //设置全屏按键功能
        holder.gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.gsyVideoPlayer.startWindowFullscreen(mContext, false, true);
            }
        });
        holder.gsyVideoPlayer.setPlayPosition(i);
        //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏
        holder.gsyVideoPlayer.setAutoFullWithSize(true);
        //音频焦点冲突时是否释放
        holder.gsyVideoPlayer.setReleaseWhenLossAudio(false);
        //全屏动画
        holder.gsyVideoPlayer.setShowFullAnimation(true);
        //小屏时不触摸滑动
        holder.gsyVideoPlayer.setIsTouchWiget(false);

        /**
         * 设置循环播放标识
         * */
        index++;
        if (index >= videos.size()-1) {
            index = 0;
        }
    }

    @Override
    public int getItemCount() {//这个可以设置播放完多少次后 就停止  Integer.MAX_VALUE代表无限轮播Integer的最大值
        return 88;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_thumb;
        private StandardGSYVideoPlayer gsyVideoPlayer;
        private ImageView img_play;
        private RelativeLayout rootView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_thumb = itemView.findViewById(R.id.img_thumb);
            gsyVideoPlayer = itemView.findViewById(R.id.detail_player);
            img_play = itemView.findViewById(R.id.img_play);
            rootView = itemView.findViewById(R.id.root_view);
        }
    }



}
