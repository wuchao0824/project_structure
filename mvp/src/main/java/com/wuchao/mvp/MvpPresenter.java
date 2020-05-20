package com.wuchao.mvp;

import android.content.Context;

/**
 * Description:<br>
 * Author:wuchao
 * Date:2020/5/20 9:06
 */
public class MvpPresenter <V extends MvpView> {
    private Context mContext;
    private V mView;
    public void attach(V baseView){
        this.mView=baseView;
        this.mContext=baseView.getContext();
    }
    public void detach(){
        this.mContext=null;
        this.mView=null;
    }
    /**
     *  des: 显示加载框
     */
    public void showLoading(){
        if(mView!=null){
            mView.showLoading();
        }
    }

    public void hideLoading(){
        if(mView==null){
            mView.hideLoading();
        }
    }
}
