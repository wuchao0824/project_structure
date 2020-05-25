package com.wuchao.mvvm;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * Description:<br>
 * Author:wuchao
 * Date:2020/5/20 19:07
 */
public abstract class MvvMActivity <V extends ViewDataBinding,VM extends IMvvMViewModel> extends AppCompatActivity implements IBaseView {

    public VM mViewModel;
    public V mViewDataBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewMode();
        performDataBinding();
    }

    private void performDataBinding() {
        mViewDataBinding= DataBindingUtil.setContentView(this, getLayoutId());
        mViewDataBinding.executePendingBindings();
    }

    protected abstract int getLayoutId();

    private void initViewMode() {
        mViewModel=getViewModel();
        if(mViewModel!=null){
            mViewModel.attach(this);
        }
    }

    protected abstract VM getViewModel();

    @Override
    public void showContent() {

    }

    @Override
    public void showFail() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mViewModel!=null){
            mViewModel.detach();
        }
    }
}
