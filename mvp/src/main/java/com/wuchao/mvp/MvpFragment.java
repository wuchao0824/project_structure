package com.wuchao.mvp;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Description:<br>
 * Author:wuchao
 * Date:2020/5/22 16:21
 */
public abstract class MvpFragment<T extends MvpPresenter> extends MvpLazyFragment implements MvpView{

    protected T tPresenter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachPresenter();
        initialize();
    }

    private void initialize() {
        initView();
        loadData();
    }

    private void  attachPresenter(){
        if(tPresenter==null){
            tPresenter=initPresenter();
        }
        if(tPresenter!=null){
            tPresenter.attach(this);
        }

    };


    @Override
    @LayoutRes
    protected abstract int getLayoutRes() ;

    protected abstract T initPresenter();
    protected abstract void initView();
    protected abstract void loadData();

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(tPresenter!=null){
            tPresenter.detach();
        }
    }
}
