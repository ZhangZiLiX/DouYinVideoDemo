package com.wd.douyinvideodemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Author : 张自力
 * Created on time.
 *
 * 抖音视频管理器
 *
 */
public class DouYinLayoutManager extends LinearLayoutManager implements RecyclerView.OnChildAttachStateChangeListener {
    private int mDrift;//位移，用来判断移动方向

    private PagerSnapHelper mPagerSnapHelper;
    private OnViewPagerListener mOnViewPagerListener;

    /**
     * 集成LinearLayoutManger
     *
     *    实现的三个方法
     *
     * */
    public DouYinLayoutManager(Context context) {
        super(context);
    }

    public DouYinLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        mPagerSnapHelper = new PagerSnapHelper();
    }

    public DouYinLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        //可写  可不写
    }


    @Override
    public void onAttachedToWindow(RecyclerView view) {
        view.addOnChildAttachStateChangeListener(this);
        mPagerSnapHelper.attachToRecyclerView(view);

        super.onAttachedToWindow(view);
    }

    /**
     * 实现RecyclerView的子条目监听
     *
     *   必须两个方法 onChildViewAttachedToWindow（）   onChildViewDetachedFromWindow(@NonNull View view)
     *
     *    当Item添加进来了调用这个方法
     * */
    @Override
    public void onChildViewAttachedToWindow(@NonNull View view) {
       //播放视频操作即将要播放的是上一个视频还是下一个视频
       int position = getPosition(view);//获取当前View的下标
       if(0 == position){
           if(mOnViewPagerListener != null){
               //调用接口方法onPageSelected（）
               //选中的监听以及判断是否滑动到底部
               mOnViewPagerListener.onPageSelected(getPosition(view), false);
           }
       }
    }

    /**
     * 管理器设置监听
     *
     * */
    public void setOnViewPagerListener(OnViewPagerListener mOnViewPagerListener) {
        this.mOnViewPagerListener = mOnViewPagerListener;
    }

    @Override
    public void onScrollStateChanged(int state) {
        switch (state){
            case RecyclerView.SCROLL_STATE_IDLE:
                View view = mPagerSnapHelper.findSnapView(this);
                int position = getPosition(view);
                if(mOnViewPagerListener != null){
                    //调用接口方法onPageSelected（）
                    //选中的监听以及判断是否滑动到底部
                    mOnViewPagerListener.onPageSelected(position, position == getItemCount() - 1);
                }
                break;
        }

        super.onScrollStateChanged(state);
    }

    @Override
    public void onChildViewDetachedFromWindow(@NonNull View view) {
        //暂停播放操作
        if(mDrift >= 0){
            if(mOnViewPagerListener !=null){
                /*释放的监听*/
                /**
                 * @param isNext 是否滑动到了下一个视频
                 * @param position 视频下标
                 *
                 * */
                mOnViewPagerListener.onPageRelease(true,getPosition(view));
            }
        }else {
            if (mOnViewPagerListener != null)
                mOnViewPagerListener.onPageRelease(false, getPosition(view));
        }
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.mDrift = dy;
        return super.scrollVerticallyBy(dy, recycler, state);
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }
}
