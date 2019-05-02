package com.wd.douyinvideodemo.adapter;

/**
 * Author : 张自力
 * Created on time.
 *
 * 播放视频 全局监听接口回调
 *
 */
public interface OnViewPagerListener {

    /*初始化完成*/
    void onInitComplete();

    /*释放的监听*/
    /**
     * @param isNext 是否滑动到了下一个视频
     * @param position 视频下标
     *
     * */
    void onPageRelease(boolean isNext,int position);

    /*选中的监听以及判断是否滑动到底部*/
    void onPageSelected(int position, boolean isBottom);


}
