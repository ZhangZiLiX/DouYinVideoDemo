package com.wd.douyinvideodemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anenn.flowlikeviewlib.FlowLikeView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.wd.douyinvideodemo.adapter.DouYinAdapter;
import com.wd.douyinvideodemo.adapter.DouYinLayoutManager;
import com.wd.douyinvideodemo.adapter.OnViewPagerListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private List<String> urlList;
    private DouYinLayoutManager douYinLayoutManager;
    private DouYinAdapter douYinAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1 初始化数据
        initData();
        //2 初始化控件
        initView();
        //3 设置监听事件
        initListener();
    }

    /**
     * 3 设置监听事件
     *
     * */
    private void initListener() {
         //管理器 设置
         douYinLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
             @Override
             public void onInitComplete() {

             }

             @Override
             public void onPageRelease(boolean isNext, int position) {
                 Log.e("管理器监听：", "释放位置:" + position + " 下一页:" + isNext);

                 int index = 0;
                 if(isNext){
                     index = 0;
                 }else{
                     index = 1;
                 }
                 //调用方法 释放视频
                 releaseVideo(index);
             }

             @Override
             public void onPageSelected(int position, boolean isBottom) {
                 Log.e("管理器监听：", "释放位置:" + position + " 下一页:" + isBottom);
                 //调用方法  播放视频
                 playVideo(0);
             }
         });
    }

    /**
     * 播放视频
     * @param i
     */
    private void playVideo(int i) {
        View itemView = recycler.getChildAt(i);
        final StandardGSYVideoPlayer standardGSYVideoPlayer = itemView.findViewById(R.id.detail_player);
        ImageView imgPlay = itemView.findViewById(R.id.img_play);//初始化播放按钮
        ImageView imgThumb = itemView.findViewById(R.id.img_thumb);//初始化背景
        TextView txtXin = itemView.findViewById(R.id.txt_xin);//点赞飘心

        FlowLikeView flowLikeView = itemView.findViewById(R.id.flowLikeView);//点赞飘心的容器
        TextView txtLike = itemView.findViewById(R.id.txt_like);//点赞飘心

        //设置视频播放器的监听
        standardGSYVideoPlayer.setVideoAllCallBack(new GSYSampleCallBack(){
            @Override
            public void onStartPrepared(String url, Object... objects) {
                //开始加载，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
                super.onStartPrepared(url, objects);
                imgThumb.setVisibility(View.VISIBLE);
                Log.i("视频播放器监听：", "onStartPrepared: "+"视频标题："+objects[0]+"当前所处播放器："+objects[1]);
            }

            @Override
            public void onPrepared(String url, Object... objects) {
                //加载成功，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
                super.onPrepared(url, objects);
                imgThumb.setVisibility(View.GONE);
                Log.i("视频播放器监听：", "onPrepared: "+"视频标题："+objects[0]+"当前所处播放器："+objects[1]);
            }
        });


        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启视频播放
                standardGSYVideoPlayer.startPlayLogic();
            }
        });

        //点赞飘心
        txtXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flowLikeView.addLikeView();
            }
        });
    }

    /**
     *释放视频
     * @param index
     */
    private void releaseVideo(int index) {
        //拿到列表的子条目
        View childView = recycler.getChildAt(index);
        //通过子条目 初始化当前视频播放器
        StandardGSYVideoPlayer standardGSYVideoPlayer = childView.findViewById(R.id.detail_player);
        //开启视频播放器  可以设置一个按钮 点击后进行播放
        standardGSYVideoPlayer.release();

    }


    /**
     * 2 初始化控件
     *
     * */
    private void initView() {
        //视频列表
        recycler = (RecyclerView) findViewById(R.id.recycler);
        //初始化自定义个视频管理器
        douYinLayoutManager = new DouYinLayoutManager(this, OrientationHelper.VERTICAL, false);
        //初始化适配器
        douYinAdapter = new DouYinAdapter(urlList, this);
        //为recycler添加适配器 和 管理器
        recycler.setLayoutManager(douYinLayoutManager);
        recycler.setAdapter(douYinAdapter);

    }

    /**
     * 1 初始化数据
     *
     *   播放的视频地址
     *
     * */
    private void initData() {
        urlList = new ArrayList<>();
        urlList.add("http://chuangfen.oss-cn-hangzhou.aliyuncs.com/public/attachment/201805/100651/201805181532123423.mp4");
        urlList.add("http://chuangfen.oss-cn-hangzhou.aliyuncs.com/public/attachment/201803/100651/201803151735198462.mp4");
        urlList.add("http://chuangfen.oss-cn-hangzhou.aliyuncs.com/public/attachment/201803/100651/201803150923220770.mp4");
        urlList.add("http://chuangfen.oss-cn-hangzhou.aliyuncs.com/public/attachment/201803/100651/201803150922255785.mp4");
        urlList.add("http://chuangfen.oss-cn-hangzhou.aliyuncs.com/public/attachment/201803/100651/201803150920130302.mp4");
        urlList.add("http://chuangfen.oss-cn-hangzhou.aliyuncs.com/public/attachment/201803/100651/201803141625005241.mp4");
        urlList.add("http://chuangfen.oss-cn-hangzhou.aliyuncs.com/public/attachment/201803/100651/201803141624378522.mp4");
        urlList.add("http://chuangfen.oss-cn-hangzhou.aliyuncs.com/public/attachment/201803/100651/201803131546119319.mp4");

    }



}
